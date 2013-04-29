package fr.exanpe.roomeeting.domain.business.filters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.exanpe.roomeeting.domain.model.Site;
import fr.exanpe.roomeeting.domain.model.ref.RoomFeature;

public class RoomFilter
{
    private Site site;

    private int capacity = 0;

    private String name;

    private Date date = new Date();

    private Integer minutesLength;

    private List<RoomFeature> features = new ArrayList<RoomFeature>();

    /**
     * @return the site
     */
    public Site getSite()
    {
        return site;
    }

    /**
     * @param site the site to set
     */
    public void setSite(Site site)
    {
        this.site = site;
    }

    /**
     * @return the capacity
     */
    public int getCapacity()
    {
        return capacity;
    }

    /**
     * @param capacity the capacity to set
     */
    public void setCapacity(int capacity)
    {
        this.capacity = capacity;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the date
     */
    public Date getDate()
    {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date)
    {
        this.date = date;
    }

    /**
     * @return the minutesLength
     */
    public Integer getMinutesLength()
    {
        return minutesLength;
    }

    /**
     * @param minutesLength the minutesLength to set
     */
    public void setMinutesLength(Integer minutesLength)
    {
        this.minutesLength = minutesLength;
    }

    /**
     * @return the features
     */
    public List<RoomFeature> getFeatures()
    {
        return features;
    }

    /**
     * @param features the features to set
     */
    public void setFeatures(List<RoomFeature> features)
    {
        this.features = features;
    }

}
