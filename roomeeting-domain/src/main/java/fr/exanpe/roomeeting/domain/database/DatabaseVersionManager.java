/**
 * 
 */
package fr.exanpe.roomeeting.domain.database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import fr.exanpe.roomeeting.common.enums.ParameterEnum;
import fr.exanpe.roomeeting.common.enums.RoleEnum;
import fr.exanpe.roomeeting.common.exception.BusinessException;
import fr.exanpe.roomeeting.domain.business.ParameterManager;
import fr.exanpe.roomeeting.domain.business.RoomFeatureManager;
import fr.exanpe.roomeeting.domain.business.UserManager;
import fr.exanpe.roomeeting.domain.database.versions.OnlyDatabaseVersionDescriptor;
import fr.exanpe.roomeeting.domain.model.Role;
import fr.exanpe.roomeeting.domain.model.User;
import fr.exanpe.roomeeting.domain.model.ref.Parameter;

@Component
public class DatabaseVersionManager
{
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseVersionManager.class);

    @Autowired
    private List<DatabaseVersion> versions;

    @Autowired
    private ParameterManager parameterManager;

    @Autowired
    private RoomFeatureManager roomFeatureManager;

    @Autowired
    private UserManager userManager;

    @Autowired
    private DataSource dataSource;

    private ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();

    public void boot() throws SQLException
    {
        sortVersions();

        String dbVersion = parameterManager.findString(ParameterEnum.DB_VERSION);

        List<DatabaseVersion> applyVersions = null;

        if (dbVersion == null)
        {
            LOGGER.info("Initializing database from scratch");
            applyVersions = versions;
        }
        else
        {
            LOGGER.info("Database version detected [{}]", dbVersion);
            DatabaseVersion currentVersion = new OnlyDatabaseVersionDescriptor(dbVersion);
            applyVersions = filterVersions(currentVersion);
            if (CollectionUtils.isEmpty(applyVersions))
            {
                LOGGER.info("Database already uptodate");
                return;
            }
        }

        for (DatabaseVersion v : applyVersions)
        {
            LOGGER.info("Applying patch for version [{}]", v.getVersion());
            for (Resource r : v.getResources())
            {
                databasePopulator.addScript(r);
            }
        }
        databasePopulator.populate(dataSource.getConnection());

        if (dbVersion == null)
        {
            init_post_V1_0_0_features();
        }

        Parameter p = new Parameter(ParameterEnum.DB_VERSION.getCode());
        p.setName("Version");
        p.setStringValue(applyVersions.get(applyVersions.size() - 1).getVersion());

        parameterManager.update(p);

        LOGGER.info("Database up-to-date on version [{}]", p.getStringValue());
    }

    private void init_post_V1_0_0_features()
    {
        roomFeatureManager.create("roomfeatures-key-videoc", "/features/icons/videoconf.png");
        roomFeatureManager.create("roomfeatures-key-projector", "/features/icons/projector.png");
        roomFeatureManager.create("roomfeatures-key-secured", "/features/icons/secured.png");
        roomFeatureManager.create("roomfeatures-key-drinks", "/features/icons/drinks.png");
        roomFeatureManager.create("roomfeatures-key-phonec", "/features/icons/phoneconf.png");
        roomFeatureManager.create("roomfeatures-key-wifi", "/features/icons/wifi.png");

        Role roleAdmin = userManager.findRole(RoleEnum.ADMIN.getCode());

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setEmail("admin@domain.com");
        admin.setName("Admin");
        admin.setFirstname("Admin");
        try
        {
            userManager.createUser(admin, Collections.singletonList(roleAdmin));
        }
        catch (BusinessException e)
        {
            throw new RuntimeException(e);
        }
    }

    private void sortVersions()
    {
        Collections.sort(versions, COMPARATOR);
    }

    private List<DatabaseVersion> filterVersions(DatabaseVersion baseVersion)
    {

        for (int i = 0; i < versions.size(); i++)
        {
            if (COMPARATOR.compare(baseVersion, versions.get(i)) < 0) { return versions.subList(i, versions.size()); }
        }
        return new ArrayList<DatabaseVersion>();
    }

    private static final Comparator<DatabaseVersion> COMPARATOR = new DatabaseVersionComparator();
}
