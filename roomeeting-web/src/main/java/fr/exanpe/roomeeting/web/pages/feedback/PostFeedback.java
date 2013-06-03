package fr.exanpe.roomeeting.web.pages.feedback;

import java.util.Date;

import org.apache.commons.lang.time.FastDateFormat;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import fr.exanpe.roomeeting.domain.business.FeedbackManager;
import fr.exanpe.roomeeting.domain.model.Feedback;
import fr.exanpe.roomeeting.domain.security.RooMeetingSecurityContext;

public class PostFeedback
{
    @Persist
    private Feedback feedback;

    @Inject
    private FeedbackManager feedbackManager;

    @Inject
    private RooMeetingSecurityContext securityContext;

    @Inject
    private Messages messages;

    @SetupRender
    void init()
    {
        feedback.setUser(securityContext.getUser());
    }

    @OnEvent(value = EventConstants.ACTION, component = "post")
    public void post()
    {
        // security check
        feedbackManager.create(feedback);
    }

    public String toStringDate(Date d)
    {
        return FastDateFormat.getInstance(messages.get("date-format")).format(d);
    }

    /**
     * @return the feedback
     */
    public Feedback getFeedback()
    {
        return feedback;
    }

    /**
     * @param feedback the feedback to set
     */
    public void setFeedback(Feedback feedback)
    {
        this.feedback = feedback;
    }
}
