package fr.exanpe.roomeeting.web.pages.admin;

import java.io.IOException;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.io.IOUtils;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.springframework.security.access.AccessDeniedException;

import fr.exanpe.roomeeting.domain.business.RoomFeatureManager;
import fr.exanpe.roomeeting.domain.business.SiteManager;
import fr.exanpe.roomeeting.domain.model.Room;
import fr.exanpe.roomeeting.domain.model.Site;
import fr.exanpe.roomeeting.domain.model.ref.RoomFeature;
import fr.exanpe.t5.lib.annotation.ContextPageReset;

/**
 * Welcome page
 */
public class ManageSite
{
    @Property
    @Persist
    private Site site;

    @Inject
    private SiteManager siteManager;

    @Inject
    private RoomFeatureManager roomFeatureManager;

    @Property
    private Room currentRoom;

    @Property
    @Persist
    private Room editRoom;

    @Property
    private UploadedFile roomMap;

    @Property
    @Persist(PersistenceConstants.FLASH)
    private String errorUpload;

    @InjectComponent("roomZone")
    private Zone roomZone;

    @InjectComponent(value = "gridRooms")
    private Grid gridRooms;

    @Inject
    private Messages messages;

    void onActivate()
    {
        if (site == null)
        {
            site = new Site();
            editRoom = null;
        }
    }

    boolean onActivate(Long siteId)
    {
        // TODO security
        site = siteManager.findWithRooms(siteId);
        editRoom = null;
        return true;
    }

    @ContextPageReset
    private void reset()
    {
        site = new Site();
        editRoom = null;
    }

    Object onUploadException(FileUploadException ex)
    {
        errorUpload = messages.get("file-error");

        return this;
    }

    @SetupRender
    void init()
    {
        // TODO bug
        if (CollectionUtils.isNotEmpty(site.getRooms()))
        {
            gridRooms.getDataModel().get("phoneNumber").sortable(false);
        }
    }

    @OnEvent(value = "validateSite")
    void validateSite()
    {
        site = siteManager.update(site);

        editRoom = null;
    }

    @OnEvent(value = EventConstants.ACTION, component = "setupEditRoom")
    Object setupEditRoom(Long id)
    {
        editRoom = null;
        for (Room r : site.getRooms())
        {
            if (id.equals(r.getId()))
            {
                editRoom = r;
                break;
            }
        }
        if (editRoom == null) { throw new AccessDeniedException("Not authorized"); }
        return roomZone.getBody();
    }

    @OnEvent(value = EventConstants.ACTION, component = "setupAddRoom")
    Object setupAddRoom()
    {
        editRoom = new Room();
        return roomZone.getBody();
    }

    @OnEvent(value = EventConstants.ACTION, component = "cancelRoom")
    void cancelRoom()
    {
        editRoom = null;
    }

    @OnEvent(value = "validateRoom")
    void validateRoom()
    {
        // Map
        if (roomMap != null)
        {
            if (!(roomMap.getFileName().endsWith(".png") || roomMap.getFileName().endsWith(".jpg") || roomMap.getFileName().endsWith(".jpeg")))
            {
                errorUpload = messages.get("file-format");
                return;
            }
            byte[] b;

            try
            {
                b = IOUtils.toByteArray(roomMap.getStream());
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }

            editRoom.setMap(b);
        }

        if (editRoom.getId() == null)
        {
            siteManager.addRoom(site, editRoom);
        }
        else
        {
            siteManager.updateRoom(editRoom);
        }

        site = siteManager.findWithRooms(site.getId());

        editRoom = null;
    }

    @OnEvent(value = EventConstants.ACTION, component = "deleteRoom")
    void deleteRoom(Long id)
    {
        // security check
        siteManager.removeRoom(site, id);

    }

    public boolean hasRooms()
    {
        return CollectionUtils.isNotEmpty(site.getRooms());
    }

    public boolean isSiteExists()
    {
        return site.getId() != null;
    }

    public boolean hasRoomMap()
    {
        return currentRoom.getMap() != null && currentRoom.getMap().length != 0;
    }

    public List<RoomFeature> listFeatures()
    {
        return roomFeatureManager.list();
    }

}
