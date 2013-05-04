package fr.exanpe.roomeeting.domain.business.dto;

import java.util.ArrayList;
import java.util.List;

import fr.exanpe.roomeeting.domain.model.Site;

public class SiteAvailabilityDTO
{
    private Site site;

    private List<RoomAvailabilityDTO> roomAvailabilityDTOs = new ArrayList<RoomAvailabilityDTO>();

    public SiteAvailabilityDTO(Site site)
    {
        super();
        this.site = site;
    }

    /**
     * @return the site
     */
    public Site getSite()
    {
        return site;
    }

    /**
     * @return the roomAvailabilityDTOs
     */
    public List<RoomAvailabilityDTO> getRoomAvailabilityDTOs()
    {
        return roomAvailabilityDTOs;
    }

}
