package fr.exanpe.roomeeting.web.spring;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import fr.exanpe.roomeeting.domain.business.UserManager;
import fr.exanpe.roomeeting.domain.model.User;

@Component("authenticationSuccessHandler")
public class RooMeetingAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler
{
    @Autowired
    private UserManager userManager;

    public RooMeetingAuthenticationSuccessHandler()
    {
        setAlwaysUseDefaultTargetUrl(false);
        setDefaultTargetUrl("/Home");
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException,
            ServletException
    {
        userManager.onConnected((User) authentication.getPrincipal());
        super.onAuthenticationSuccess(request, response, authentication);
    }

}
