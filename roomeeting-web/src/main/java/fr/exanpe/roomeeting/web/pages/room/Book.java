package fr.exanpe.roomeeting.web.pages.room;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionAttribute;
import org.apache.tapestry5.ioc.annotations.Inject;

import fr.exanpe.roomeeting.domain.business.BookingManager;
import fr.exanpe.roomeeting.domain.business.dto.SearchAvailabilityDTO;
import fr.exanpe.roomeeting.domain.business.dto.TimeSlot;
import fr.exanpe.roomeeting.domain.model.Gap;
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
    private TimeSlot from;

    @Property
    private TimeSlot to;

    Object onActivate()
    {
        if (bookGap == null) { return Search.class; }

        hoursModel = selectHoursService.create(DateUtils.isSameDay(new Date(), bookGap.getDate()));

        from = search.getRequest().getRestrictFrom();
        to = search.getRequest().getRestrictTo();

        return null;
    }

    @OnEvent(value = "book")
    void book()
    {
        bookingManager.processBooking(bookGap, from, to);
    }
}
