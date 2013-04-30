package fr.exanpe.roomeeting.web.pages.room;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionAttribute;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.internal.SelectModelImpl;
import org.apache.tapestry5.ioc.annotations.Inject;

import fr.exanpe.roomeeting.common.enums.ParameterEnum;
import fr.exanpe.roomeeting.domain.business.BookingManager;
import fr.exanpe.roomeeting.domain.business.ParameterManager;
import fr.exanpe.roomeeting.domain.business.SiteManager;
import fr.exanpe.roomeeting.domain.business.UserManager;
import fr.exanpe.roomeeting.domain.business.dto.RoomAvailabilityDTO;
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
    private ParameterManager parameterManager;

    @Inject
    private RooMeetingSecurityContext securityContext;

    @SessionAttribute
    private List<RoomAvailabilityDTO> roomAvailabilityDTO;

    @Persist
    @Property
    private RoomFilter filter;

    @Property
    @Persist
    private SelectModel sitesSelectModel;

    @Property
    @Persist
    private SelectModel hoursModel;

    @Property
    @Persist(PersistenceConstants.FLASH)
    private boolean noResult;

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
        if (hoursModel == null)
        {
            int start = parameterManager.find(ParameterEnum.HOUR_DAY_START.getCode()).getIntegerValue();
            int end = parameterManager.find(ParameterEnum.HOUR_DAY_END.getCode()).getIntegerValue();

            OptionModel[] oms = new OptionModel[end - start + 1];

            for (int i = 0; i < oms.length; i++)
            {
                oms[i] = new OptionModelImpl("" + (start + i));
            }

            hoursModel = new SelectModelImpl(oms);
        }
    }

    @OnEvent(value = "search")
    public Object search()
    {
        List<RoomAvailabilityDTO> list = bookingManager.searchRoomAvailable(filter);

        if (CollectionUtils.isEmpty(list))
        {
            noResult = true;
            return this;
        }

        roomAvailabilityDTO = list;

        return ShowAvailability.class;
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
