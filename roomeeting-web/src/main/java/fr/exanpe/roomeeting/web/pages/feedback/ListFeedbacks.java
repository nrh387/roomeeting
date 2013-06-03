package fr.exanpe.roomeeting.web.pages.feedback;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import fr.exanpe.roomeeting.domain.business.FeedbackManager;
import fr.exanpe.roomeeting.domain.model.Feedback;

public class ListFeedbacks
{

    @Persist
    @Property
    private List<Feedback> list;

    @Property
    private Feedback current;

    @Inject
    private FeedbackManager feedbackManager;

    void onActivate()
    {
        list = feedbackManager.list();
    }

    @OnEvent(value = EventConstants.ACTION, component = "delete")
    public void delete(Long id)
    {
        // security check
        feedbackManager.delete(id);

    }

    public boolean hasElements()
    {
        return CollectionUtils.isNotEmpty(list);
    }

}
