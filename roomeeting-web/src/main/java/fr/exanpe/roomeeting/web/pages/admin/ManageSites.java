package fr.exanpe.roomeeting.web.pages.admin;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

import fr.exanpe.roomeeting.domain.business.SiteManager;
import fr.exanpe.roomeeting.domain.model.Site;

/**
 * Welcome page
 */
public class ManageSites
{
    @Persist
    @Property
    private List<Site> sites;

    @Property
    private Site currentSite;

    @Property
    private Site currentMarkerSite;

    @Inject
    private SiteManager siteManager;

    void onActivate()
    {
        sites = siteManager.list();
    }

    @OnEvent(value = EventConstants.ACTION, component = "delete")
    void delete(long id)
    {
        siteManager.delete(id);
    }

    public boolean hasMap()
    {
        return StringUtils.isNotEmpty(currentSite.getLatitude()) && StringUtils.isNotEmpty(currentSite.getLongitude());
    }
}
