package fr.exanpe.roomeeting.web.services;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.ioc.services.Coercion;

import fr.exanpe.roomeeting.domain.business.SiteManager;
import fr.exanpe.roomeeting.domain.model.Site;

public class SiteCoercer implements Coercion<String, Site>
{

    private SiteManager siteManager;

    private Class<Site> site;

    @Override
    public Site coerce(String input)
    {
        if (StringUtils.isEmpty(input)) { return null; }

        return siteManager.find(Long.parseLong(input));
    }
}
