package fr.exanpe.roomeeting.web.pages.room;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionAttribute;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.internal.SelectModelImpl;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import fr.exanpe.roomeeting.common.enums.ParameterEnum;
import fr.exanpe.roomeeting.common.utils.RoomDateUtils;
import fr.exanpe.roomeeting.domain.business.BookingManager;
import fr.exanpe.roomeeting.domain.business.ParameterManager;
import fr.exanpe.roomeeting.domain.business.SiteManager;
import fr.exanpe.roomeeting.domain.business.UserManager;
import fr.exanpe.roomeeting.domain.business.dto.DateAvailabilityDTO;
import fr.exanpe.roomeeting.domain.business.dto.SearchAvailabilityDTO;
import fr.exanpe.roomeeting.domain.business.dto.TimeSlot;
import fr.exanpe.roomeeting.domain.business.filters.RoomFilter;
import fr.exanpe.roomeeting.domain.model.Site;
import fr.exanpe.roomeeting.domain.security.RooMeetingSecurityContext;
import fr.exanpe.roomeeting.web.services.SelectTimeSlotService;

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
    private SelectTimeSlotService selectHoursService;

    @Inject
    private RooMeetingSecurityContext securityContext;

    @SessionAttribute
    private SearchAvailabilityDTO search;

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
            hoursModel = selectHoursService.create(false);

            int start = parameterManager.find(ParameterEnum.HOUR_DAY_START.getCode()).getIntegerValue();
            int end = parameterManager.find(ParameterEnum.HOUR_DAY_END.getCode()).getIntegerValue();

            filter.setRestrictFrom(new TimeSlot(start, 0));
            filter.setRestrictTo(new TimeSlot(end, 0));
        }
    }

    @BeginRender
    void begin()
    {
        // date already passed... change day
        if (RoomDateUtils.setHourMinutes(filter.getDate(), filter.getRestrictTo().getHours(), filter.getRestrictTo().getMinutes()).before(new Date()))
        {
            filter.setDate(RoomDateUtils.nextWorkingDay(new Date()));
        }
        // date from is passed, adjust it
        if (RoomDateUtils.setHourMinutes(filter.getDate(), filter.getRestrictFrom().getHours(), filter.getRestrictTo().getMinutes()).before(new Date()))
        {
            filter.setRestrictFrom(new TimeSlot(Calendar.getInstance().get(Calendar.HOUR_OF_DAY), 0));
        }
    }

    @OnEvent(value = "search")
    public Object search()
    {
        List<DateAvailabilityDTO> list = bookingManager.searchRoomAvailable(filter);

        if (CollectionUtils.isEmpty(list))
        {
            noResult = true;
            return this;
        }

        search = new SearchAvailabilityDTO(list, filter);

        return Choose.class;
    }

    @Inject
    private Messages messages;

    public String toStringDate(Date d)
    {
        return FastDateFormat.getInstance(messages.get("date-format")).format(d);
    }

    public Date getToday()
    {
        if (Calendar.getInstance().after(RoomDateUtils.setHourMinutes(new Date(), filter.getRestrictTo().getHours(), filter.getRestrictTo().getMinutes()))) { return RoomDateUtils
                .nextWorkingDay(new Date()); }
        return new Date();
    }
}
