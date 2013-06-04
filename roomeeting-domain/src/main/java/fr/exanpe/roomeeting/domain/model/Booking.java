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
{
        @NamedQuery(name = Booking.FIND_BOOKING_FOR_DATE, query = "From Booking booking where booking.date = :date and booking.room = :room"),
        @NamedQuery(name = Booking.LIST_USER_FUTURES_BOOKINGS, query = "From Booking booking left join fetch booking.room where booking.user = :user and (booking.date > CURRENT_DATE or (booking.date = CURRENT_DATE and booking.endHour > HOUR(CURRENT_TIME))) order by booking.date, booking.startHour, booking.startMinute"),
        @NamedQuery(name = Booking.LIST_USER_PASTS_BOOKINGS, query = "From Booking booking left join fetch booking.room where booking.user = :user and (booking.date < CURRENT_DATE or (booking.date = CURRENT_DATE and booking.endHour <= HOUR(CURRENT_TIME))) order by booking.date, booking.endHour, booking.endMinute") })
public class Booking implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = -243387776160345357L;

    public static final String FIND_BOOKING_FOR_DATE = "Booking.listUserBooking";
    public static final String LIST_USER_FUTURES_BOOKINGS = "Booking.listUserFuturesBookings";
    public static final String LIST_USER_PASTS_BOOKINGS = "Booking.listUserPastsBookings";

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

    private Integer startMinute;

    private Integer endHour;

    private Integer endMinute;

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

}
