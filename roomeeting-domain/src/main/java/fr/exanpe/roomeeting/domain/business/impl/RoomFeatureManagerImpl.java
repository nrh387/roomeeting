/**
 * 
 */
package fr.exanpe.roomeeting.domain.business.impl;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.exanpe.roomeeting.domain.business.RoomFeatureManager;
import fr.exanpe.roomeeting.domain.core.business.impl.DefaultManagerImpl;
import fr.exanpe.roomeeting.domain.core.dao.CrudDAO;
import fr.exanpe.roomeeting.domain.model.ref.RoomFeature;

@Service
public class RoomFeatureManagerImpl extends DefaultManagerImpl<RoomFeature, Long> implements RoomFeatureManager
{
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomFeatureManagerImpl.class);

    private static final String DEFAULT_ICON = "/features/icons/unknown.png";

    @Autowired
    private CrudDAO crudDAO;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(String name, String classpathIcon)
    {
        RoomFeature rf = new RoomFeature();
        rf.setName(name);
        try
        {
            rf.setIcon(IOUtils.toByteArray(Thread.currentThread().getContextClassLoader().getResourceAsStream(classpathIcon)));
        }
        catch (IOException e)
        {
            LOGGER.error("Icon loading error", e);
            try
            {
                rf.setIcon(IOUtils.toByteArray(Thread.currentThread().getContextClassLoader().getResourceAsStream(DEFAULT_ICON)));
            }
            catch (IOException e1)
            {
                LOGGER.error("Default icon loading error", e);
            }
        }

        create(rf);
    }

    @Override
    public void delete(Long id)
    {
        Query q = entityManager.createNativeQuery("DELETE FROM Room_RoomFeature WHERE features_id=:id");

        q.setParameter("id", id);
        q.executeUpdate();

        super.delete(id);
    }

}
