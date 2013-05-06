package fr.exanpe.roomeeting.domain.business.filters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.exanpe.roomeeting.domain.business.dto.TimeSlot;
import fr.exanpe.roomeeting.domain.model.Site;
import fr.exanpe.roomeeting.domain.model.ref.RoomFeature;

public class RoomFilter
{
    private Site site;

    private int capacity = 0;

    private String name;

    private Date date = new Date();

    private List<RoomFeature> features = new ArrayList<RoomFeature>();

    private int extendDays = 0;

    private boolean extendWorkingOnly = true;

    private TimeSlot restrictFrom;

    private TimeSlot restrictTo;

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
     * @return the extendDays
     */
    public int getExtendDays()
    {
        return extendDays;
    }

    /**
     * @param extendDays the extendDays to set
     */
    public void setExtendDays(int extendDays)
    {
        this.extendDays = extendDays;
    }

    /**
     * @return the extendWorking
     */
    public boolean isExtendWorkingOnly()
    {
        return extendWorkingOnly;
    }

    /**
     * @param extendWorking the extendWorking to set
     */
    public void setExtendWorkingOnly(boolean extendWorking)
    {
        this.extendWorkingOnly = extendWorking;
    }

    /**
     * @return the restrictFrom
     */
    public TimeSlot getRestrictFrom()
    {
        return restrictFrom;
    }

    /**
     * @param restrictFrom the restrictFrom to set
     */
    public void setRestrictFrom(TimeSlot restrictFrom)
    {
        this.restrictFrom = restrictFrom;
    }

    /**
     * @return the restrictTo
     */
    public TimeSlot getRestrictTo()
    {
        return restrictTo;
    }

    /**
     * @param restrictTo the restrictTo to set
     */
    public void setRestrictTo(TimeSlot restrictTo)
    {
        this.restrictTo = restrictTo;
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
