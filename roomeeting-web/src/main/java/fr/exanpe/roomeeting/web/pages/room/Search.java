package fr.exanpe.roomeeting.web.pages.room;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.internal.SelectModelImpl;
import org.apache.tapestry5.ioc.annotations.Inject;

import fr.exanpe.roomeeting.domain.business.BookingManager;
import fr.exanpe.roomeeting.domain.business.SiteManager;
import fr.exanpe.roomeeting.domain.business.UserManager;
import fr.exanpe.roomeeting.domain.business.filters.RoomFilter;
import fr.exanpe.roomeeting.domain.model.Site;
import fr.exanpe.roomeeting.domain.security.RooMeetingSecurityContext;

public class Search
{
    @Inject
    private UserManager userManager;

    @Inject
    private SiteManager siteManager;

    @Inject
    private BookingManager bookingManager;

    @Inject
    private RooMeetingSecurityContext securityContext;

    @Persist
    @Property
    private RoomFilter filter;

    @Property
    @Persist
    private SelectModel sitesSelectModel;

    void onActivate()
    {
        if (filter == null)
        {
            filter = new RoomFilter();
            filter.setSite(userManager.findDefaultSite(securityContext.getUser()));
        }
        if (sitesSelectModel == null)
        {
            List<Site> sites = siteManager.list();

            if (CollectionUtils.isNotEmpty(sites))
            {
                OptionModel[] oms = new OptionModel[sites.size()];

                for (int i = 0; i < sites.size(); i++)
                {
                    oms[i] = new OptionModelImpl(sites.get(i).getName(), sites.get(i).getId());
                }

                sitesSelectModel = new SelectModelImpl(oms);
            }
        }
    }

    @OnEvent(value = "search")
    public void search()
    {
        bookingManager.searchRoomAvailable(filter);
    }

    public String toStringDate(Date d)
    {
        return FastDateFormat.getInstance("dd/MM/yyyy").format(d);
    }

    public Date getToday()
    {
        return new Date();
    }
}
