/**
 * 
 */
package fr.exanpe.roomeeting.domain.business.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import fr.exanpe.roomeeting.common.exception.BusinessException;
import fr.exanpe.roomeeting.domain.business.UserManager;
import fr.exanpe.roomeeting.domain.business.filters.UserFilter;
import fr.exanpe.roomeeting.domain.core.business.impl.DefaultManagerImpl;
import fr.exanpe.roomeeting.domain.core.dao.CrudDAO;
import fr.exanpe.roomeeting.domain.core.dao.QueryParameters;
import fr.exanpe.roomeeting.domain.model.Role;
import fr.exanpe.roomeeting.domain.model.Site;
import fr.exanpe.roomeeting.domain.model.User;
import fr.exanpe.roomeeting.domain.security.RooMeetingSecurityContext;

/**
 * @author lguerin
 */
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
@Service("userManager")
public class UserManagerImpl extends DefaultManagerImpl<User, Long> implements UserManager
{
    private static final Logger LOGGER = LoggerFactory.getLogger(UserManagerImpl.class);

    @Autowired
    private CrudDAO crudDAO;

    @Autowired
    private RooMeetingSecurityContext securityContext;

    @Autowired
    private SaltSource saltSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PersistenceContext
    protected EntityManager entityManager;

    /*
     * (non-Javadoc)
     * @see fr.exanpe.roomeeting.domain.business.UserManager#findByUsername(java.lang.String)
     */
    @Override
    public User findByUsername(String username)
    {
        Assert.hasText(username, String.format("Le parametre username ne peut pas etre null ou vide"));
        User result;
        try
        {

            result = crudDAO.findUniqueWithNamedQuery(User.FIND_BY_USERNAME, QueryParameters.with("username", username).parameters());
        }
        catch (NoResultException e)
        {
            LOGGER.info("L'utilisateur identifi� par: " + username + " n'existe pas.");
            result = null;
        }
        catch (EmptyResultDataAccessException e)
        {
            LOGGER.info("User identified by username: " + username + " n'existe pas.");
            result = null;
        }
        return result;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = BusinessException.class)
    public void createUser(User user, List<Role> roles) throws BusinessException
    {
        if (!isAvailableUsername(user.getUsername())) { throw new BusinessException("L'utilisateur: " + user.getUsername() + " existe d�j�."); }

        String pass = user.getPassword();
        user.setPassword(this.passwordEncoder.encodePassword(pass, this.saltSource.getSalt(user)));
        for (Role role : roles)
        {
            Role realRole = crudDAO.findUniqueWithNamedQuery(Role.FIND_BY_ROLE_NAME, QueryParameters.with("name", role.getName()).parameters());
            if (realRole == null) { throw new BusinessException("Le role: " + role.getName() + " n'existe pas !"); }
            user.addRole(realRole);
        }
        crudDAO.create(user);
    }

    /*
     * (non-Javadoc)
     * @see fr.exanpe.roomeeting.domain.business.UserManager#isAvailableName(java.lang.String)
     */
    public boolean isAvailableUsername(String username)
    {
        return findByUsername(username) == null;
    }

    @Override
    public void cleanRoles(User user)
    {
        user.getRoles().clear();
        crudDAO.update(user);
    }

    public RooMeetingSecurityContext getSecurityContext()
    {
        return securityContext;
    }

    public void setSecurityContext(RooMeetingSecurityContext securityContext)
    {
        this.securityContext = securityContext;
    }

    @Override
    public void createRole(Role r)
    {
        crudDAO.create(r);
    }

    @Override
    public List<Role> listRoles()
    {
        return crudDAO.findWithNamedQuery(Role.FIND_ALL);
    }

    @Override
    public List<User> searchUsers(UserFilter filter)
    {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> q = builder.createQuery(User.class);
        Root<User> objectUser = q.from(User.class);

        q.select(objectUser);

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (StringUtils.isNotEmpty(filter.getName()))
        {
            predicates.add(builder.like(builder.upper(objectUser.<String> get("name")), StringUtils.upperCase(filter.getName()) + "%"));
        }
        if (StringUtils.isNotEmpty(filter.getUsername()))
        {
            predicates.add(builder.like(builder.upper(objectUser.<String> get("username")), StringUtils.upperCase(filter.getUsername()) + "%"));
        }
        if (StringUtils.isNotEmpty(filter.getFirstname()))
        {
            predicates.add(builder.like(builder.upper(objectUser.<String> get("firstname")), StringUtils.upperCase(filter.getFirstname()) + "%"));
        }

        q.where(predicates.toArray(new Predicate[predicates.size()]));

        return crudDAO.findCriteriaQuery(q, 100);
    }

    @Override
    public Site findDefaultSite(User user)
    {

        return find(user.getId()).getDefaultSite();
    }

    @Override
    public void onConnected(User user)
    {
        user.setLastConnection(new Date());
    }

}
