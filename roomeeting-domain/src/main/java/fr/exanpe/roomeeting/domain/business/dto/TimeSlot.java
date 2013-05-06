package fr.exanpe.roomeeting.domain.business.dto;

import java.io.Serializable;

/**
 * @author jmaupoux
 */
// TODO factory immutable
public class TimeSlot implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 5103024977794099345L;

    private int hours;

    private int minutes;

    public TimeSlot()
    {
        // TODO Auto-generated constructor stub
    }

    public TimeSlot(int hours, int minutes)
    {
        super();
        this.hours = hours;
        this.minutes = minutes;
    }

    /**
     * @return the hours
     */
    public int getHours()
    {
        return hours;
    }

    /**
     * @param hours the hours to set
     */
    public void setHours(int hours)
    {
        this.hours = hours;
    }

    /**
     * @return the minutes
     */
    public int getMinutes()
    {
        return minutes;
    }

    /**
     * @param minutes the minutes to set
     */
    public void setMinutes(int minutes)
    {
        this.minutes = minutes;
    }

}
