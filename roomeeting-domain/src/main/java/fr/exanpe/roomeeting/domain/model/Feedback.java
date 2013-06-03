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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries(
{ @NamedQuery(name = Feedback.LIST_BY_SITE, query = "From Feedback where room.site = :site order by date desc"),
        @NamedQuery(name = Feedback.COUNT_BY_SITE, query = "Select count(f) From Feedback f where f.room.site = :site order by f.date desc") })
public class Feedback implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = -243347878060345357L;

    public static final String LIST_BY_SITE = "Feedback.listBySite";
    public static final String COUNT_BY_SITE = "Feedback.countBySite";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Room room;

    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne
    private User user;

    private String feedback;

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
     * @return the feedback
     */
    public String getFeedback()
    {
        return feedback;
    }

    /**
     * @param feedback the feedback to set
     */
    public void setFeedback(String feedback)
    {
        this.feedback = feedback;
    }

    // public Integer getMinutesLength()
    // {
    // return minutesLength;
    // }

}
