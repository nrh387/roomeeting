package fr.exanpe.roomeeting.domain.business.dto;

import java.util.List;

import fr.exanpe.roomeeting.domain.business.filters.RoomFilter;

public class SearchAvailabilityDTO
{
    private List<DateAvailabilityDTO> dates;

    private RoomFilter request;

    public SearchAvailabilityDTO(List<DateAvailabilityDTO> dates, RoomFilter request)
    {
        super();
        this.dates = dates;
        this.request = request;
    }

    /**
     * @return the dates
     */
    public List<DateAvailabilityDTO> getDates()
    {
        return dates;
    }

    /**
     * @param dates the dates to set
     */
    public void setDates(List<DateAvailabilityDTO> dates)
    {
        this.dates = dates;
    }

    /**
     * @return the request
     */
    public RoomFilter getRequest()
    {
        return request;
    }

    /**
     * @param request the request to set
     */
    public void setRequest(RoomFilter request)
    {
        this.request = request;
    }

}
