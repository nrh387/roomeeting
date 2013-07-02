package fr.exanpe.roomeeting.web.pages;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

import fr.exanpe.roomeeting.common.exception.BusinessException;
import fr.exanpe.roomeeting.domain.business.UserManager;

public class ResetPassword
{
    @Property
    @Persist(PersistenceConstants.FLASH)
    private String username;

    @Inject
    private UserManager userManager;

    @Property
    @Persist(PersistenceConstants.FLASH)
    private boolean success;

    @Inject
    private Request request;

    @OnEvent(value = EventConstants.SUCCESS)
    public void resetPassword() throws BusinessException
    {

        userManager.resetPassword(username, request.getLocale());

        success = true;
    }
}
