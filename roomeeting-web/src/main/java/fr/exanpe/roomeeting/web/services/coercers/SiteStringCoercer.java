package fr.exanpe.roomeeting.web.services.coercers;

import org.apache.tapestry5.ioc.services.Coercion;

import fr.exanpe.roomeeting.domain.model.Site;

public class SiteStringCoercer implements Coercion<Site, String>
{

    @Override
    public String coerce(Site input)
    {
        if (input == null) { return null; }

        return input.getId() + "";
    }
}
