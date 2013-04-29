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

    @Temporal(TemporalType.TIME)
    private Date startTime;

    @Temporal(TemporalType.TIME)
    private Date endTime;

    @Column(nullable = false)
    private Integer minutesLength;

    @PrePersist
    @PreUpdate
    protected void compute()
    {
        minutesLength = (int) ((endTime.getTime() - startTime.getTime()) / (1000 * 60));
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

    /**
     * @return the startDate
     */
    public Date getStartTime()
    {
        return startTime;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }

    public Integer getMinutesLength()
    {
        return minutesLength;
    }

    /**
     * @return the endDate
     */
    public Date getEndTime()
    {
        return endTime;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

}
