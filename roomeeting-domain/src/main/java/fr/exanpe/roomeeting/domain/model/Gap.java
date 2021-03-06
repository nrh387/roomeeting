/**
 * 
 */
package fr.exanpe.roomeeting.domain.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Index;

import fr.exanpe.roomeeting.common.utils.RoomDateUtils;

@Entity
@NamedQueries(
{
        @NamedQuery(name = Gap.FIND_GAP_AROUND_TIMESLOT, query = "From Gap gap where gap.date = :date and gap.room = :room and gap.startTime <= :startTime and gap.endTime >= :endTime"),
        @NamedQuery(name = Gap.FIND_GAP_FOR_TIME, query = "From Gap gap where gap.date = :date and gap.room = :room and (gap.startTime = :time or gap.endTime = :time)") })
@NamedNativeQuery(name = Gap.PURGE, query = "DELETE FROM Gap where date < CURRENT_DATE", resultClass = Gap.class)
public class Gap implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = -243347798060345357L;

    public static final String FIND_GAP_AROUND_TIMESLOT = "Gap.findGapAroundTimeslot";
    public static final String FIND_GAP_FOR_TIME = "Gap.findGapForTime";
    public static final String PURGE = "Gap.purge";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @Index(name = "idx_gap_date_room", columnNames =
    { "date", "room" })
    private Room room;

    @Temporal(TemporalType.DATE)
    private Date date;

    private Integer startHour;

    private Integer startMinute;

    private Integer endHour;

    private Integer endMinute;

    @Temporal(TemporalType.TIME)
    private Date startTime;

    @Temporal(TemporalType.TIME)
    private Date endTime;

    @PreUpdate
    @PrePersist
    void compute()
    {
        startTime = RoomDateUtils.setHourMinutes(date, startHour, startMinute);
        endTime = RoomDateUtils.setHourMinutes(date, endHour, endMinute);
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
    public Integer getStartMinute()
    {
        return startMinute;
    }

    /**
     * @param startMinutes the startMinutes to set
     */
    public void setStartMinute(Integer startMinutes)
    {
        this.startMinute = startMinutes;
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
    public Integer getEndMinute()
    {
        return endMinute;
    }

    /**
     * @param endMinutes the endMinutes to set
     */
    public void setEndMinute(Integer endMinutes)
    {
        this.endMinute = endMinutes;
    }

    /**
     * @return the startTime
     */
    public Date getStartTime()
    {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    /**
     * @return the endTime
     */
    public Date getEndTime()
    {
        return endTime;
    }

}
