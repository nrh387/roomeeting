package fr.exanpe.roomeeting.web.pages.admin;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Grid;

import fr.exanpe.roomeeting.common.exception.BusinessException;
import fr.exanpe.roomeeting.domain.business.UserManager;
import fr.exanpe.roomeeting.domain.business.filters.UserFilter;
import fr.exanpe.roomeeting.domain.model.User;
import fr.exanpe.roomeeting.domain.security.RooMeetingSecurityContext;

/**
 * Welcome page
 */
public class ManageUsers
{
    private static final int MAX_RESULTS = 100;

    @Persist
    @Property
    private List<User> users;

    @Property
    private User currentUser;

    @InjectComponent
    private Grid gridUsers;

    @Inject
    private UserManager userManager;

    @Property
    @Persist
    private UserFilter userFilter;

    @Property
    private boolean maxFound;

    @Inject
    private RooMeetingSecurityContext securityContext;

    void onActivate()
    {
        if (userFilter == null)
        {
            userFilter = new UserFilter();
        }
    }

    public boolean isMaxFound()
    {
        return users != null && users.size() == MAX_RESULTS;
    }

    @OnEvent(value = "search")
    void search() throws BusinessException
    {
        if (StringUtils.isEmpty(userFilter.getName()) && StringUtils.isEmpty(userFilter.getFirstname()) && StringUtils.isEmpty(userFilter.getUsername())) { throw new BusinessException(
                "Au moins un élément du filtre est requis"); }

        userFilter.setMaxResults(MAX_RESULTS);

        // always need a fresh copy for a crud page
        users = userManager.searchUsers(userFilter);

        if (CollectionUtils.isNotEmpty(users))
        {
            if (gridUsers.getSortModel().getSortConstraints().isEmpty())
            {
                gridUsers.getSortModel().updateSort("name");
            }
        }
    }

    @OnEvent(value = "reset")
    void reset() throws BusinessException
    {
        userFilter = new UserFilter();
        users = null;
    }
}
