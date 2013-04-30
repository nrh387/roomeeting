package fr.exanpe.roomeeting.web.pages.room;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionAttribute;

import fr.exanpe.roomeeting.domain.business.dto.RoomAvailabilityDTO;
import fr.exanpe.roomeeting.domain.model.Site;

public class ShowAvailability
{
    @SessionAttribute
    @Property
    private List<RoomAvailabilityDTO> roomAvailabilityDTO;

    @Persist
    @Property
    private List<Site> sites;

    @Property
    private Site currentSite;

    private RoomAvailabilityDTO currentRA;

    @Property
    private boolean siteChanged;

    Object onActivate()
    {
        if (roomAvailabilityDTO == null) { return Search.class; }

        sites = new ArrayList<Site>();
        for (RoomAvailabilityDTO r : roomAvailabilityDTO)
        {
            if (!sites.contains(r.getRoom().getSite()))
            {
                sites.add(r.getRoom().getSite());
            }
        }

        return null;
    }

    /**
     * @return the currentRA
     */
    public RoomAvailabilityDTO getCurrentRA()
    {
        return currentRA;
    }

    /**
     * @param currentRA the currentRA to set
     */
    public void setCurrentRA(RoomAvailabilityDTO currentRA)
    {
        this.siteChanged = true;

        if (this.currentRA != null)
        {
            if (this.currentRA.getRoom().getSite().equals(currentRA.getRoom().getSite()))
            {
                this.siteChanged = false;
            }
        }
        this.currentRA = currentRA;
    }

}
