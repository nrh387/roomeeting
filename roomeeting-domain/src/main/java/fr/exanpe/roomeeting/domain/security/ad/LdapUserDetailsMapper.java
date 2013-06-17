package fr.exanpe.roomeeting.domain.security.ad;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.InetOrgPerson;
import org.springframework.security.ldap.userdetails.InetOrgPersonContextMapper;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.stereotype.Component;

import fr.exanpe.roomeeting.common.enums.RoleEnum;
import fr.exanpe.roomeeting.domain.business.UserManager;
import fr.exanpe.roomeeting.domain.model.User;

@Component
public class LdapUserDetailsMapper implements UserDetailsContextMapper
{

    private InetOrgPersonContextMapper ldapUserDetailsMapper = new InetOrgPersonContextMapper();

    @Autowired
    private UserManager userManager;

    @Override
    public UserDetails mapUserFromContext(DirContextOperations arg0, String arg1, Collection<? extends GrantedAuthority> arg2)
    {
        InetOrgPerson userLdap = (InetOrgPerson) ldapUserDetailsMapper.mapUserFromContext(arg0, arg1, arg2);

        User u = userManager.findByUsername(userLdap.getUsername());
        if (u == null)
        {
            u = new User();
            u.addRole(userManager.findRole(RoleEnum.ADMIN.getCode()));
        }
        u.setExternal(true);
        u.setEmail(userLdap.getMail());
        u.setName(userLdap.getSn());
        u.setUsername(userLdap.getUsername());

        userManager.update(u);

        return u;
    }

    @Override
    public void mapUserToContext(UserDetails arg0, DirContextAdapter arg1)
    {
        ldapUserDetailsMapper.mapUserToContext(arg0, arg1);
    }

}
