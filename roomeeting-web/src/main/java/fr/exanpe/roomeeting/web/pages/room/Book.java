package fr.exanpe.roomeeting.web.pages.room;

import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionAttribute;
import org.apache.tapestry5.ioc.annotations.Inject;

import fr.exanpe.roomeeting.common.exception.BusinessException;
import fr.exanpe.roomeeting.domain.business.BookingManager;
import fr.exanpe.roomeeting.domain.business.dto.SearchAvailabilityDTO;
import fr.exanpe.roomeeting.domain.business.dto.TimeSlot;
import fr.exanpe.roomeeting.domain.model.Booking;
import fr.exanpe.roomeeting.domain.model.Gap;
import fr.exanpe.roomeeting.domain.security.RooMeetingSecurityContext;
import fr.exanpe.roomeeting.web.services.SelectTimeSlotService;

public class Book
{

    @SessionAttribute
    @Property
    private Gap bookGap;

    @SessionAttribute
    @Property
    private SearchAvailabilityDTO search;

    @Inject
    private BookingManager bookingManager;

    @Inject
    private SelectTimeSlotService selectHoursService;

    @Property
    private SelectModel hoursModel;

    @Property
    @Persist(PersistenceConstants.FLASH)
    private TimeSlot from;

    @Property
    @Persist(PersistenceConstants.FLASH)
    private TimeSlot to;

    @Inject
    private RooMeetingSecurityContext securityContext;

    Object onActivate()
    {
        if (bookGap == null) { return Search.class; }

        if (search.getRequest().getRestrictFrom().getHours() > bookGap.getStartHour()
                || (search.getRequest().getRestrictFrom().getHours() == bookGap.getStartHour() && search.getRequest().getRestrictTo().getMinutes() > bookGap
                        .getEndMinute()))
        {
            from = search.getRequest().getRestrictFrom();
        }
        else
        {
            from = new TimeSlot(bookGap.getStartHour(), bookGap.getStartMinute());
        }

        if (search.getRequest().getRestrictTo().getHours() < bookGap.getEndHour()
                || (search.getRequest().getRestrictTo().getHours() == bookGap.getEndHour() && search.getRequest().getRestrictTo().getMinutes() <= bookGap
                        .getEndMinute()))
        {
            System.out.println("to requests:" + search.getRequest().getRestrictTo().getHours() + "/" + search.getRequest().getRestrictTo().getMinutes());
            to = search.getRequest().getRestrictTo();
        }
        else
        {
            System.out.println("to requests:" + bookGap.getEndHour() + "/" + bookGap.getEndMinute());
            to = new TimeSlot(bookGap.getEndHour(), bookGap.getEndMinute());
        }

        hoursModel = selectHoursService.create(from, to, 30);

        return null;
    }

    @SessionAttribute
    private Booking booking;

    @OnEvent(value = "book")
    Object book() throws BusinessException
    {
        booking = bookingManager.processBooking(securityContext.getUser(), bookGap, from, to);

        bookGap = null;
        search = null;

        return ConfirmBooking.class;
    }
}
