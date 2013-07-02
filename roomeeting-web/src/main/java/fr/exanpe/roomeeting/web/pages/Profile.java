package fr.exanpe.roomeeting.web.pages;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import fr.exanpe.roomeeting.domain.business.UserManager;
import fr.exanpe.roomeeting.domain.model.User;
import fr.exanpe.roomeeting.domain.security.RooMeetingSecurityContext;

/**
 * Profile page
 */
public class Profile
{
    @Inject
    private UserManager userManager;

    @Inject
    private RooMeetingSecurityContext rooMeetingSecurityContext;

    @Property
    @Persist
    private User user;

    @Property
    private String pass1;

    @Property
    private String pass2;

    @Property
    private boolean external;

    void onActivate()
    {
        user = rooMeetingSecurityContext.getUser();
    }

    public boolean editDisabled()
    {
        return user.isExternal();
    }

    @Inject
    private Messages messages;

    @InjectComponent
    private Form userForm;

    @OnEvent(value = EventConstants.VALIDATE, component = "userForm")
    void validateUpdate()
    {
        if (StringUtils.isNotEmpty(pass1) || StringUtils.isNotEmpty(pass2))
        {
            if (StringUtils.isEmpty(pass1) || StringUtils.isEmpty(pass2))
            {
                userForm.recordError(messages.get("error-passes"));
            }
            else
            {
                if (!pass1.equals(pass2))
                {
                    userForm.recordError(messages.get("error-passes"));
                }
            }
        }
    }

    @Property
    @Persist(PersistenceConstants.FLASH)
    private boolean success;

    @OnEvent(value = EventConstants.SUBMIT)
    void update()
    {
        if (StringUtils.isNotEmpty(pass1))
        {
            user.setPassword(userManager.encodePassword(pass1));
        }
        userManager.update(user);
        success = true;
    }
}
