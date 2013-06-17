package fr.exanpe.roomeeting.web.services.coercers;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.ioc.services.Coercion;

import fr.exanpe.roomeeting.domain.business.UserManager;
import fr.exanpe.roomeeting.domain.model.Role;

public class StringRoleCoercer implements Coercion<String, Role>
{

    private UserManager userManager;

    public StringRoleCoercer(UserManager userManager)
    {
        this.userManager = userManager;
    }

    @Override
    public Role coerce(String input)
    {
        if (StringUtils.isEmpty(input)) { return null; }

        return userManager.findRole(Long.parseLong(input));
    }
}
