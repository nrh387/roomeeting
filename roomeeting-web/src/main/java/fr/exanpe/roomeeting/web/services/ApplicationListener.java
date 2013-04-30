/**
 * 
 */
package fr.exanpe.roomeeting.web.services;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.io.IOUtils;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.annotations.EagerLoad;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import fr.exanpe.roomeeting.common.enums.ParameterEnum;
import fr.exanpe.roomeeting.common.exception.BusinessException;
import fr.exanpe.roomeeting.domain.business.ParameterManager;
import fr.exanpe.roomeeting.domain.business.RoomFeatureManager;
import fr.exanpe.roomeeting.domain.business.SiteManager;
import fr.exanpe.roomeeting.domain.business.UserManager;
import fr.exanpe.roomeeting.domain.model.Role;
import fr.exanpe.roomeeting.domain.model.Room;
import fr.exanpe.roomeeting.domain.model.Site;
import fr.exanpe.roomeeting.domain.model.User;
import fr.exanpe.roomeeting.domain.model.ref.Parameter;
import fr.exanpe.roomeeting.domain.model.ref.RoomFeature;

/**
 * Classe chargée automatiquement au démarrage de l'application.
 * 
 * @author lguerin
 */
@EagerLoad
public class ApplicationListener
{
    /**
     * Logger de la classe
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationListener.class);

    /**
     * Le formatteur de date / heure
     */
    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    /**
     * Constructeur.
     * 
     * @param context Contexte Spring
     * @param productionMode Mode Tapestry (PRODUCTION ou DEVELOPPEMENT)
     * @throws SQLException
     */
    public ApplicationListener(ApplicationContext context, @Inject
    @Symbol(SymbolConstants.PRODUCTION_MODE)
    boolean productionMode) throws SQLException
    {
        if (productionMode)
        {
            LOGGER.info("Demarrage de l'application en mode PRODUCTION.");
            return;
        }

        /**
         * En mode DEVELOPPEMENT, permet d'initialiser un jeu de données par défaut.
         */
        LOGGER.info("Demarrage de l'application en mode DEVELOPPEMENT.");

        // Chargement du jeu de données de test
        try
        {
            loadParameters(context);
            loadRoomFeatures(context);
            loadRole(context);
            loadUser(context);
            loadSites(context);
        }
        catch (IOException e)
        {
            LOGGER.error(">> Erreur fatale : impossible de charger le jeu de données de test: " + e);
            System.exit(1);
        }
        catch (BusinessException e)
        {
            LOGGER.error(">> Erreur fatale : impossible de charger le jeu de données de test: " + e);
            System.exit(1);
        }
    }

    private void loadParameters(ApplicationContext context)
    {
        LOGGER.info(">>> Parameter : Chargement du jeu de donnees par defaut...");

        ParameterManager pm = context.getBean(ParameterManager.class);

        Parameter p1 = new Parameter(ParameterEnum.HOUR_DAY_START.getCode(), "Heure d'ouverture des salles",
                "Paramètre de l'heure d'ouverture des salles. Aucune salle ne pourra être réservée avant cet horaire.");
        p1.setIntegerValue(8);

        Parameter p2 = new Parameter(ParameterEnum.HOUR_DAY_END.getCode(), "Heure de fermeture des salles",
                "Paramètre de l'heure de fermeture des salles. Aucune salle ne pourra être réservée après cet horaire.");
        p2.setIntegerValue(20);

        pm.create(p1);
        pm.create(p2);

        LOGGER.info("<<< Parameter : Chargement termine.");
    }

    private void loadRoomFeatures(ApplicationContext context) throws IOException
    {
        LOGGER.info(">>> RoomFeatures : Chargement du jeu de donnees par defaut...");

        RoomFeatureManager rfm = context.getBean(RoomFeatureManager.class);

        RoomFeature rf = new RoomFeature();
        rf.setName("Video-conference");
        rf.setIcon(IOUtils.toByteArray(Thread.currentThread().getContextClassLoader().getResourceAsStream("/features/icons/videoconf.png")));

        rfm.create(rf);

        RoomFeature rf2 = new RoomFeature();
        rf2.setName("Projector");
        rf2.setIcon(IOUtils.toByteArray(Thread.currentThread().getContextClassLoader().getResourceAsStream("/features/icons/projector.png")));

        rfm.create(rf2);

        RoomFeature rf3 = new RoomFeature();
        rf3.setName("Secured");
        rf3.setIcon(IOUtils.toByteArray(Thread.currentThread().getContextClassLoader().getResourceAsStream("/features/icons/secured.png")));

        rfm.create(rf3);

        RoomFeature rf4 = new RoomFeature();
        rf4.setName("Drinks");
        rf4.setIcon(IOUtils.toByteArray(Thread.currentThread().getContextClassLoader().getResourceAsStream("/features/icons/drinks.png")));

        rfm.create(rf4);

        RoomFeature rf5 = new RoomFeature();
        rf5.setName("Phone-conference");
        rf5.setIcon(IOUtils.toByteArray(Thread.currentThread().getContextClassLoader().getResourceAsStream("/features/icons/phoneconf.png")));

        rfm.create(rf5);

        RoomFeature rf6 = new RoomFeature();
        rf6.setName("Wi-Fi");
        rf6.setIcon(IOUtils.toByteArray(Thread.currentThread().getContextClassLoader().getResourceAsStream("/features/icons/wifi.png")));

        rfm.create(rf6);

        LOGGER.info("<<< RoomFeatures : Chargement termine.");
    }

    private void loadSites(ApplicationContext context) throws SQLException
    {
        LOGGER.info(">>> Sites : Chargement du jeu de donnees par defaut...");

        SiteManager sm = context.getBean(SiteManager.class);

        Site site = new Site();
        site.setName("Mattei");
        site.setAddress("rue de Bercy, Paris");

        sm.create(site);

        Site site2 = new Site();
        site2.setName("Manhattan");
        site2.setAddress("Esplanade de la Defense");

        sm.create(site2);

        RoomFeature rf = context.getBean(RoomFeatureManager.class).find(1L);
        RoomFeature rf2 = context.getBean(RoomFeatureManager.class).find(2L);

        Room room = new Room();
        room.setName("Oeillet");

        room.getFeatures().add(rf);
        room.getFeatures().add(rf2);

        sm.addRoom(site, room);

        LOGGER.info("<<< Sites : Chargement termine.");
    }

    private void loadRole(ApplicationContext context) throws SQLException
    {
        LOGGER.info(">>> Roles : Chargement du jeu de donnees par defaut...");

        UserManager rm = context.getBean(UserManager.class);

        Role superAdmin = new Role();
        superAdmin.setPriority(1);
        superAdmin.setName("Super Admin");

        rm.createRole(superAdmin);

        Role admin = new Role();
        superAdmin.setPriority(2);
        admin.setName("Admin");

        rm.createRole(admin);

        Role user = new Role();
        superAdmin.setPriority(3);
        user.setName("User");

        rm.createRole(user);

        LOGGER.info("<<< Roles : Chargement termine.");
    }

    private void loadUser(ApplicationContext context) throws SQLException, BusinessException
    {
        LOGGER.info(">>> Users : Chargement du jeu de donnees par defaut...");

        UserManager userManager = context.getBean(UserManager.class);

        List<Role> roles = context.getBean(UserManager.class).listRoles();

        User superadmin = new User();
        superadmin.setUsername("superadmin");
        superadmin.setPassword("superadmin");
        superadmin.setEmail("jul@gmail.com");
        superadmin.setName("Name");
        superadmin.setFirstname("Firstname");
        userManager.createUser(superadmin, Collections.singletonList(roles.get(0)));

        User useradmin = new User();
        useradmin.setUsername("admin");
        useradmin.setPassword("admin");
        useradmin.setEmail("jul@gmail.com");
        useradmin.setName("Name");
        useradmin.setFirstname("Firstname");
        userManager.createUser(useradmin, Collections.singletonList(roles.get(1)));

        User user = new User();
        user.setUsername("user");
        user.setPassword("user");
        user.setEmail("jul@gmail.com");
        user.setName("Name");
        user.setFirstname("Firstname");
        userManager.createUser(user, Collections.singletonList(roles.get(2)));

        LOGGER.info("<<< Users : Chargement termine.");

        // TODO delete, cache test purpose
        for (int i = 0; i < 10; i++)
        {
            userManager.findByUsername("admin");
        }
    }

    private Connection pullConnection(ApplicationContext context) throws SQLException
    {
        return pullDataSource(context).getConnection();
    }

    private DataSource pullDataSource(ApplicationContext context)
    {
        return (DataSource) context.getBean("dataSource");
    }
}
