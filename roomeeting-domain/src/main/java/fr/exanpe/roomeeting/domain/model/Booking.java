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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Booking implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = -243387776160345357L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Room room;

    @ManyToOne
    private User user;

    @Temporal(TemporalType.DATE)
    private Date date;

    private Integer startHour;

    private Integer startMinutes;

    private Integer endHour;

    private Integer endMinutes;

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
     * @return the user
     */
    public User getUser()
    {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user)
    {
        this.user = user;
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
