/**
 * 
 */
package fr.exanpe.roomeeting.domain.business.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.exanpe.roomeeting.domain.business.SiteManager;
import fr.exanpe.roomeeting.domain.core.business.impl.DefaultManagerImpl;
import fr.exanpe.roomeeting.domain.core.dao.CrudDAO;
import fr.exanpe.roomeeting.domain.core.dao.QueryParameters;
import fr.exanpe.roomeeting.domain.model.Room;
import fr.exanpe.roomeeting.domain.model.Site;

@Service("siteManager")
public class SiteManagerImpl extends DefaultManagerImpl<Site, Long> implements SiteManager
{
    private static final Logger LOGGER = LoggerFactory.getLogger(SiteManagerImpl.class);

    @Autowired
    private CrudDAO crudDAO;

    @Override
    @Transactional(readOnly = true)
    public List<Site> list()
    {
        return crudDAO.findWithNamedQuery(Site.FIND_ALL);
    }

    @Override
    public void addRoom(Site s, Room r)
    {
        s = find(s.getId());

        s.getRooms().add(r);
        r.setSite(s);
        s.setRoomCount(s.getRoomCount() + 1);

        create(s);
    }

    @Override
    @Transactional(readOnly = true)
    public Site findWithRooms(Long siteId)
    {
        return crudDAO.findUniqueWithNamedQuery(Site.FIND_WITH_ROOMS, QueryParameters.with("id", siteId).parameters());
    }

    @Override
    public void updateRoom(Room editRoom)
    {
        crudDAO.update(editRoom);
    }

    @Override
    public void removeRoom(Site site, Long roomId)
    {
        Room r = crudDAO.find(Room.class, roomId);

        if (site.getRooms().remove(r))
        {
            site.setRoomCount(site.getRoomCount() - 1);
            update(site);
        }

    }

}
