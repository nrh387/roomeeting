package fr.exanpe.roomeeting.web.pages.feedback;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import fr.exanpe.roomeeting.common.annotations.RoomeetingSecured;
import fr.exanpe.roomeeting.common.exception.HackException;
import fr.exanpe.roomeeting.domain.business.FeedbackManager;
import fr.exanpe.roomeeting.domain.model.Feedback;
import fr.exanpe.roomeeting.domain.security.RooMeetingSecurityContext;
import fr.exanpe.t5.lib.annotation.Authorize;

@Authorize(all = "AUTH_READ_FEEDBACK")
public class ListFeedbacks
{

    @Persist
    @Property
    private List<Feedback> list;

    @Property
    private Feedback current;

    @Inject
    private FeedbackManager feedbackManager;

    @Inject
    private RooMeetingSecurityContext securityContext;

    @RoomeetingSecured
    void onActivate()
    {
        list = feedbackManager.list(securityContext.getUser());
    }

    @RoomeetingSecured
    @OnEvent(value = EventConstants.ACTION, component = "delete")
    public void delete(Long id)
    {
        for (Feedback f : list)
        {
            if (id.equals(f.getId()))
            {
                // security check
                feedbackManager.delete(id);
                return;
            }
        }

        throw new HackException();
    }

    public boolean hasElements()
    {
        return CollectionUtils.isNotEmpty(list);
    }

}
