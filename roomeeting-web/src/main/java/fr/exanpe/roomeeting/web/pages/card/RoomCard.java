package fr.exanpe.roomeeting.web.pages.card;

import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import fr.exanpe.roomeeting.domain.business.SiteManager;
import fr.exanpe.roomeeting.domain.model.Room;

public class RoomCard
{
    @Inject
    private SiteManager siteManager;

    @Property
    @Persist(PersistenceConstants.FLASH)
    private Room room;

    void onActivate(long id)
    {
        room = siteManager.findRoom(id);
    }

    public boolean hasMap()
    {
        return room.getMap() != null && room.getMap().length > 0;
    }
}
