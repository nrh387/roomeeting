/**
 * 
 */
package fr.exanpe.roomeeting.domain.business.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.net.SocketException;
import java.util.UUID;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.UidGenerator;

import org.apache.commons.lang.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import fr.exanpe.roomeeting.common.utils.RoomDateUtils;
import fr.exanpe.roomeeting.domain.business.ICalManager;
import fr.exanpe.roomeeting.domain.model.Booking;

/**
 * Gestion des calendrier iCal
 * 
 * @author jmaupoux
 */
@Transactional(propagation = Propagation.SUPPORTS)
@Service
public class ICalManagerImpl implements ICalManager
{

    private static final Logger LOGGER = LoggerFactory.getLogger(ICalManagerImpl.class);

    private static final String ICAL_EXT = ".ics";

    private CalendarOutputter calendarOutputter;

    public ICalManagerImpl()
    {
        this.calendarOutputter = new CalendarOutputter(true);
    }

    public String generateICal(Booking booking)
    {
        Date start = new DateTime(RoomDateUtils.setHourMinutes(booking.getDate(), booking.getStartHour(), booking.getStartMinute()));
        Date end = new DateTime(RoomDateUtils.setHourMinutes(booking.getDate(), booking.getEndHour(), booking.getEndMinute()));
        VEvent vbooking = new VEvent(start, end, "");

        Location location = new Location("[" + booking.getRoom().getSite().getName() + "] - " + booking.getRoom().getName());

        vbooking.getProperties().add(location);

        Uid uid = null;

        try
        {
            UidGenerator ug = new UidGenerator("1");
            uid = ug.generateUid();
        }
        catch (SocketException e)
        {
            uid = new Uid(UUID.randomUUID().toString());
        }
        vbooking.getProperties().add(uid);

        Calendar ical = new Calendar();
        ical.getProperties().add(Version.VERSION_2_0);
        ical.getProperties().add(new ProdId("-//exanpe//RooMeeting//EN"));
        ical.getComponents().add(vbooking);

        StringWriter writer = new StringWriter(64);
        try
        {
            this.calendarOutputter.output(ical, writer);
        }
        catch (IOException e)
        {
            LOGGER.error("Error during generation", e);
            return null;
        }
        catch (ValidationException e)
        {
            LOGGER.error("Error during validation", e);
            return null;
        }

        return writer.toString();
    }

    public String generateICalName(Booking booking)
    {
        return "Booking-" + FastDateFormat.getInstance("yyyyMMdd").format(booking.getDate()) + "-" + booking.getRoom().getName() + ICAL_EXT;
    }
}
