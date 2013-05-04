/**
 * 
 */
package fr.exanpe.roomeeting.domain.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Gap implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = -243347798060345357L;

    public static final String SEARCH_ROOM_AVAILABLE = "Gap.searchRoomAvailable";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Room room;

    @Temporal(TemporalType.DATE)
    private Date date;

    private Integer startHour;

    private Integer startMinutes;

    private Integer endHour;

    private Integer endMinutes;

    @Column(nullable = false)
    private Integer minutesLength;

    @PrePersist
    @PreUpdate
    protected void compute()
    {
        minutesLength = (endHour - startHour) * 60 + endMinutes - startMinutes;
    }

    /**
     * @return the id
     */
    public Long getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id)
    {
        this.id = id;
    }

    /**
     * @return the room
     */
    public Room getRoom()
    {
        return room;
    }

    /**
     * @param room the room to set
     */
    public void setRoom(Room room)
    {
        this.room = room;
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

    public Integer getMinutesLength()
    {
        return minutesLength;
    }

    /**
     * @return the startHour
     */
    public Integer getStartHour()
    {
        return startHour;
    }

    /**
     * @param startHour the startHour to set
     */
    public void setStartHour(Integer startHour)
    {
        this.startHour = startHour;
    }

    /**
     * @return the startMinutes
     */
    public Integer getStartMinutes()
    {
        return startMinutes;
    }

    /**
     * @param startMinutes the startMinutes to set
     */
    public void setStartMinutes(Integer startMinutes)
    {
        this.startMinutes = startMinutes;
    }

    /**
     * @return the endHour
     */
    public Integer getEndHour()
    {
        return endHour;
    }

    /**
     * @param endHour the endHour to set
     */
    public void setEndHour(Integer endHour)
    {
        this.endHour = endHour;
    }

    /**
     * @return the endMinutes
     */
    public Integer getEndMinutes()
    {
        return endMinutes;
    }

    /**
     * @param endMinutes the endMinutes to set
     */
    public void setEndMinutes(Integer endMinutes)
    {
        this.endMinutes = endMinutes;
    }

}
