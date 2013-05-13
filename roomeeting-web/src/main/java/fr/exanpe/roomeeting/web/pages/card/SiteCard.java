package fr.exanpe.roomeeting.web.pages.card;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import fr.exanpe.roomeeting.domain.business.SiteManager;
import fr.exanpe.roomeeting.domain.model.Site;

public class SiteCard
{
    @Inject
    private SiteManager siteManager;

    @Property
    @Persist(PersistenceConstants.FLASH)
    private Site site;

    void onActivate(long id)
    {
        site = siteManager.find(id);
    }

    public boolean hasMap()
    {
        return StringUtils.isNotEmpty(site.getLatitude()) && StringUtils.isNotEmpty(site.getLongitude());
    }
}
