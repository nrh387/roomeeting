/**
 * 
 */
package fr.exanpe.roomeeting.domain.business.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import fr.exanpe.roomeeting.domain.business.FeedbackManager;
import fr.exanpe.roomeeting.domain.business.UserManager;
import fr.exanpe.roomeeting.domain.core.business.impl.DefaultManagerImpl;
import fr.exanpe.roomeeting.domain.core.dao.CrudDAO;
import fr.exanpe.roomeeting.domain.core.dao.QueryParameters;
import fr.exanpe.roomeeting.domain.model.Feedback;
import fr.exanpe.roomeeting.domain.model.Site;
import fr.exanpe.roomeeting.domain.model.User;

@Service("feedbackManager")
public class FeedbackManagerImpl extends DefaultManagerImpl<Feedback, Long> implements FeedbackManager
{
    private static final Logger LOGGER = LoggerFactory.getLogger(FeedbackManagerImpl.class);

    // TODO parameter
    private static final int MAX_RESULTS = 100;

    @Autowired
    private UserManager userManager;

    @Autowired
    private CrudDAO crudDAO;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Feedback> list(User user)
    {
        Site site = userManager.findDefaultSite(user);

        if (site == null) { return list(MAX_RESULTS); }

        return crudDAO.findMaxResultsWithNamedQuery(Feedback.LIST_BY_SITE, QueryParameters.with("site", site).parameters(), MAX_RESULTS);
    }

    @Override
    public int count(User user)
    {
        Site site = userManager.findDefaultSite(user);

        if (site == null) { return (int) count(); }

        return (int) crudDAO.count(Feedback.COUNT_BY_SITE, QueryParameters.with("site", site).parameters());
    }
}
