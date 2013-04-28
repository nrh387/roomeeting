package fr.exanpe.roomeeting.web.services;

import org.apache.tapestry5.ioc.services.Coercion;

import fr.exanpe.roomeeting.domain.business.SiteManager;
import fr.exanpe.roomeeting.domain.model.Site;

public class SiteStringCoercer implements Coercion<Site, String>
{

    private SiteManager siteManager;

    public SiteStringCoercer(SiteManager siteManager)
    {
        this.siteManager = siteManager;
    }

    @Override
    public String coerce(Site input)
    {
        if (input == null) { return null; }

        return input.getId() + "";
    }
}
