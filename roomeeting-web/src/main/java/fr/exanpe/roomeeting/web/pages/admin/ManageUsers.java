package fr.exanpe.roomeeting.web.pages.admin;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.internal.SelectModelImpl;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import fr.exanpe.roomeeting.common.exception.BusinessException;
import fr.exanpe.roomeeting.domain.business.UserManager;
import fr.exanpe.roomeeting.domain.business.filters.UserFilter;
import fr.exanpe.roomeeting.domain.model.Role;
import fr.exanpe.roomeeting.domain.model.User;
import fr.exanpe.roomeeting.domain.security.RooMeetingSecurityContext;
import fr.exanpe.roomeeting.web.services.OptionalMessageService;

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

    @InjectComponent
    private Form form;

    @InjectComponent("userZone")
    private Zone userZone;

    @Property
    @Persist
    private User editUser;

    @Inject
    private Messages messages;

    @Property
    private boolean maxFound;

    @Inject
    private RooMeetingSecurityContext securityContext;

    @Inject
    private OptionalMessageService optionalMessageService;

    @Property
    private SelectModel selectRoles;

    @Property
    private Role selectedRole;

    void onActivate()
    {
        if (userFilter == null)
        {
            userFilter = new UserFilter();
        }

        List<Role> list = userManager.listRoles();

        OptionModel[] options = new OptionModel[list.size()];

        int count = 0;
        for (Role r : list)
        {
            options[count++] = new OptionModelImpl(optionalMessageService.getOptionalMessage(messages, r.getName()), r.getId());
        }

        selectRoles = new SelectModelImpl(options);
    }

    public boolean isMaxFound()
    {
        return users != null && users.size() == MAX_RESULTS;
    }

    @OnEvent(value = "search")
    void search() throws BusinessException
    {
        if (StringUtils.isEmpty(userFilter.getName()) && StringUtils.isEmpty(userFilter.getFirstname()) && StringUtils.isEmpty(userFilter.getUsername())
                && userFilter.getRole() == null)
        {
            form.recordError(messages.get("error-filter-empty"));
            return;
        }

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

    @OnEvent(value = EventConstants.ACTION, component = "setupEditUser")
    Object setupEditUser(Long id)
    {
        editUser = userManager.find(id);

        if (CollectionUtils.isNotEmpty(editUser.getRoles()))
        {
            selectedRole = editUser.getRoles().get(0);
        }

        return userZone.getBody();
    }

    @OnEvent(value = EventConstants.SUCCESS, component = "formUser")
    void editUser()
    {
        editUser.getRoles().clear();
        editUser.getRoles().add(selectedRole);

        userManager.update(editUser);

        editUser = null;
    }

    @OnEvent(value = EventConstants.ACTION, component = "cancelUser")
    void cancelUser()
    {
        editUser = null;
    }

    @OnEvent(value = EventConstants.ACTION, component = "deleteUser")
    void deleteUser(Long id) throws BusinessException
    {
        currentUser = userManager.find(id);
        if (canDelete())
        {
            userManager.delete(id);
            search();
        }
        editUser = null;
    }

    public boolean canDelete()
    {
        return currentUser.getId() != securityContext.getUser().getId() && !"admin".equals(currentUser.getUsername());
    }

    public boolean canEdit()
    {
        return canDelete();
    }

    public String getRole()
    {
        if (currentUser != null && CollectionUtils.isNotEmpty(currentUser.getRoles())) { return currentUser.getRoles().get(0).getName(); }
        return "";
    }
}
