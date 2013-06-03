/**
 * 
 */
package fr.exanpe.roomeeting.domain.business;

import java.util.List;

import fr.exanpe.roomeeting.domain.core.business.DefaultManager;
import fr.exanpe.roomeeting.domain.model.Feedback;
import fr.exanpe.roomeeting.domain.model.User;

public interface FeedbackManager extends DefaultManager<Feedback, Long>
{
    List<Feedback> list(User user);

    int count(User user);
}
