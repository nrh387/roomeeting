/**
 * 
 */
package fr.exanpe.roomeeting.domain.security.impl;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import fr.exanpe.roomeeting.domain.model.User;
import fr.exanpe.roomeeting.domain.security.RooMeetingSecurityContext;

/**
 * Implementation du SecurityContext propre à roomeeting
 * 
 * @author lguerin
 */
@Component("roomeetingSecurityContext")
public class RooMeetingSecurityContextImpl implements RooMeetingSecurityContext
{
    /*
     * (non-Javadoc)
     * @see
     * fr.exanpe.roomeeting.domain.security.RooMeetingSecurityContext#login(fr.exanpe.roomeeting.domain.model
     * .User)
     */
    @Override
    public void login(User user)
    {
        Assert.notNull(user, "user");
        UsernamePasswordAuthenticationToken logged = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(logged);
    }

    /*
     * (non-Javadoc)
     * @see fr.exanpe.roomeeting.domain.security.RooMeetingSecurityContext#isLoggedIn()
     */
    @Override
    public boolean isLoggedIn()
    {
        if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null)
        {
            if ("anonymousUser".equals(SecurityContextHolder.getContext().getAuthentication().getName())) { return false; }
            return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * @see fr.exanpe.roomeeting.domain.security.RooMeetingSecurityContext#getUser()
     */
    @Override
    public User getUser()
    {
        User user = null;
        if (isLoggedIn())
        {
            if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User)
            {
                user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            }
        }
        return user;
    }

    /*
     * (non-Javadoc)
     * @see fr.exanpe.roomeeting.domain.security.RooMeetingSecurityContext#logout()
     */
    @Override
    public void logout()
    {
        SecurityContextHolder.getContext().setAuthentication(null);
        SecurityContextHolder.clearContext();
    }

}
