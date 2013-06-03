package fr.exanpe.roomeeting.web.components;

import org.apache.tapestry5.ioc.annotations.Inject;

import fr.exanpe.roomeeting.domain.business.FeedbackManager;
import fr.exanpe.roomeeting.domain.security.RooMeetingSecurityContext;

public class Menu
{
    @Inject
    private FeedbackManager feedbackManager;

    @Inject
    private RooMeetingSecurityContext securityContext;

    public int getFeedbackCount()
    {
        return feedbackManager.count(securityContext.getUser());
    }
}
