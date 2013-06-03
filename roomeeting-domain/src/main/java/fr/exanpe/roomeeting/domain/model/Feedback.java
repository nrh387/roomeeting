/**
 * 
 */
package fr.exanpe.roomeeting.domain.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries(
{ @NamedQuery(name = Feedback.LIST_BY_SITE, query = "From Feedback where booking.room.site = :site order by date desc"),
        @NamedQuery(name = Feedback.COUNT_BY_SITE, query = "Select count(f) From Feedback f where f.booking.room.site = :site order by f.booking.date desc") })
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
    private Booking booking;

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
     * @return the booking
     */
    public Booking getBooking()
    {
        return booking;
    }

    /**
     * @param booking the booking to set
     */
    public void setBooking(Booking booking)
    {
        this.booking = booking;
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

}
