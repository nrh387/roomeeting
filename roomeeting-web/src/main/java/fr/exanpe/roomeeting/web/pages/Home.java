package fr.exanpe.roomeeting.web.pages;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.FastDateFormat;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import fr.exanpe.roomeeting.common.utils.RoomDateUtils;
import fr.exanpe.roomeeting.domain.business.BookingManager;
import fr.exanpe.roomeeting.domain.model.Booking;
import fr.exanpe.roomeeting.domain.security.RooMeetingSecurityContext;

/**
 * Welcome page
 */
public class Home
{
    @Inject
    private BookingManager bookingManager;

    @Property
    @Persist(PersistenceConstants.FLASH)
    private List<Booking> bookings;

    @Property
    private Booking currentBooking;

    @Inject
    private RooMeetingSecurityContext securityContext;

    @SetupRender
    void setup()
    {
        bookings = bookingManager.listUserFuturesBookings(securityContext.getUser());
    }

    @OnEvent(value = EventConstants.ACTION, component = "cancel")
    void cancelBooking(Long id)
    {
        bookingManager.deleteBooking(id);
    }

    @Inject
    private Messages messages;

    public String formatCurrentStartTime()
    {
        return FastDateFormat.getInstance("HH" + messages.get("hour-separator") + "mm").format(
                RoomDateUtils.setHourMinutes(new Date(), currentBooking.getStartHour(), currentBooking.getStartMinute()));
    }

    public String formatCurrentEndTime()
    {
        return FastDateFormat.getInstance("HH" + messages.get("hour-separator") + "mm").format(
                RoomDateUtils.setHourMinutes(new Date(), currentBooking.getEndHour(), currentBooking.getEndMinute()));
    }
}
