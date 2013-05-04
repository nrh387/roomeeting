package fr.exanpe.roomeeting.domain.business.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateAvailabilityDTO
{
    private Date date;

    private List<SiteAvailabilityDTO> siteAvailabilityDTOs = new ArrayList<SiteAvailabilityDTO>();

    public DateAvailabilityDTO(Date d)
    {
        super();
        this.date = d;
    }

    /**
     * @return the d
     */
    public Date getDate()
    {
        return date;
    }

    /**
     * @return the siteAvailabilityDTOs
     */
    public List<SiteAvailabilityDTO> getSiteAvailabilityDTOs()
    {
        return siteAvailabilityDTOs;
    }

}
