package fr.exanpe.roomeeting.web.pages.feedback;

import java.util.Date;

import org.apache.commons.lang.time.FastDateFormat;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import fr.exanpe.roomeeting.common.annotations.RoomeetingSecured;
import fr.exanpe.roomeeting.domain.business.BookingManager;
import fr.exanpe.roomeeting.domain.business.FeedbackManager;
import fr.exanpe.roomeeting.domain.model.Booking;
import fr.exanpe.roomeeting.domain.model.Feedback;
import fr.exanpe.roomeeting.domain.security.RooMeetingSecurityContext;
import fr.exanpe.roomeeting.web.pages.Home;
import fr.exanpe.t5.lib.annotation.Authorize;

@Authorize(all = "AUTH_POST_FEEDBACK")
public class PostFeedback
{
    @Persist
    @Property
    private Feedback feedback;

    @Inject
    private FeedbackManager feedbackManager;

    @Inject
    private BookingManager bookingManager;

    @Inject
    private RooMeetingSecurityContext securityContext;

    @Inject
    private Messages messages;

    @RoomeetingSecured
    Object onActivate(Long id)
    {
        if (id == null) { return Home.class; }
        Booking booking = bookingManager.findWithRoomUser(id, securityContext.getUser());
        if (booking == null) { return Home.class; }

        feedback = new Feedback();
        feedback.setBooking(booking);

        return null;
    }

    @OnEvent(value = "post")
    public Object post()
    {
        // security check
        feedbackManager.create(feedback);

        return Home.class;
    }

    public String toStringDate(Date d)
    {
        return FastDateFormat.getInstance(messages.get("date-format")).format(d);
    }

}
