/**
 * 
 */
package fr.exanpe.roomeeting.domain.security.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import fr.exanpe.roomeeting.domain.business.UserManager;
import fr.exanpe.roomeeting.domain.model.User;

/**
 * Implémentation de l'interface {@link UserDetailsService} fournie par Spring Security.
 * Permet de faire le pont entre notre implémentation spécifique et le gestionnaire
 * d'authentification de Spring Security.
 * 
 * @author lguerin
 */
@Component("roomeetingUserDetailService")
public class UserDetailsServiceImpl implements UserDetailsService
{

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    /**
     * RooMeeting {@link UserManager}
     */
    @Autowired
    private UserManager userManager;

    /**
     * Constructor
     * 
     * @param userManager
     */
    public UserDetailsServiceImpl() {}


    /**
     * Constructor
     * 
     * @param userManager
     */
    public UserDetailsServiceImpl(UserManager userManager)
    {
        this.userManager = userManager;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.
     * lang.String)
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException
    {
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug(">>> loadUserByUsername username: " + username);
        }
        User user = userManager.findByUsername(username);

        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("<<< User: " + user);
        }
        return user;
    }

}
