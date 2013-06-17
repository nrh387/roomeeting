package fr.exanpe.roomeeting.web.services.coercers;

import org.apache.tapestry5.ioc.services.Coercion;

import fr.exanpe.roomeeting.domain.model.Role;

public class RoleStringCoercer implements Coercion<Role, String>
{

    @Override
    public String coerce(Role input)
    {
        if (input == null) { return null; }

        return input.getId() + "";
    }
}
