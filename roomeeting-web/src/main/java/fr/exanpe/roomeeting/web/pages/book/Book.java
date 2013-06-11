package fr.exanpe.roomeeting.web.pages.book;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionAttribute;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
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
            to = search.getRequest().getRestrictTo();
        }
        else
        {
            to = new TimeSlot(bookGap.getEndHour(), bookGap.getEndMinute());
        }

        hoursModel = selectHoursService.create(from, to, 30);

        return null;
    }

    @SessionAttribute
    private Booking booking;

    @Inject
    private Messages messages;

    @InjectComponent
    private Form bookForm;

    @OnEvent(value = EventConstants.VALIDATE, component = "bookForm")
    void validateSearch() throws ValidationException
    {
        if (!from.before(to))
        {
            bookForm.recordError(messages.get("search-error-before-after"));
        }
    }

    @OnEvent(value = EventConstants.SUCCESS)
    Object book() throws BusinessException
    {
        booking = bookingManager.processBooking(securityContext.getUser(), bookGap, from, to);

        bookGap = null;
        search = null;

        return ConfirmBooking.class;
    }
}
