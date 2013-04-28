/**
 * 
 */
package fr.exanpe.roomeeting.domain.business;

import java.util.List;

import fr.exanpe.roomeeting.domain.core.business.DefaultManager;
import fr.exanpe.roomeeting.domain.model.Room;
import fr.exanpe.roomeeting.domain.model.Site;

public interface SiteManager extends DefaultManager<Site, Long>
{
    public void addRoom(Site s, Room r);

    public List<Site> list();

    public Site findWithRooms(Long siteId);

    public void updateRoom(Room editRoom);

    public void removeRoom(Site site, Long roomId);
}
