package fr.exanpe.roomeeting.web.services.coercers;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.ioc.services.Coercion;

import fr.exanpe.roomeeting.domain.business.SiteManager;
import fr.exanpe.roomeeting.domain.model.Site;

public class StringSiteCoercer implements Coercion<String, Site>
{

    private SiteManager siteManager;

    public StringSiteCoercer(SiteManager siteManager)
    {
        this.siteManager = siteManager;
    }

    @Override
    public Site coerce(String input)
    {
        if (StringUtils.isEmpty(input)) { return null; }

        return siteManager.find(Long.parseLong(input));
    }
}
