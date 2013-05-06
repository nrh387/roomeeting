package fr.exanpe.roomeeting.web.services;

import java.util.Calendar;

import org.apache.commons.lang.time.FastDateFormat;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.internal.SelectModelImpl;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import fr.exanpe.roomeeting.common.enums.ParameterEnum;
import fr.exanpe.roomeeting.domain.business.ParameterManager;
import fr.exanpe.roomeeting.domain.business.dto.TimeSlot;

public class SelectTimeSlotService
{
    @Inject
    private ParameterManager parameterManager;

    @Inject
    private Messages messages;

    public SelectModel create(boolean todayRestrict)
    {
        int startHour = parameterManager.find(ParameterEnum.HOUR_DAY_START.getCode()).getIntegerValue();
        int endHour = parameterManager.find(ParameterEnum.HOUR_DAY_END.getCode()).getIntegerValue();

        if (todayRestrict)
        {
            if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) > startHour)
            {
                startHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            }
        }

        return create(startHour, endHour, 30);
    }

    public SelectModel create(int startHour, int endHour, int minutes)
    {
        String separator = messages.get("hour-separator");

        int perHour = 60 / minutes;

        OptionModel[] oms = new OptionModel[(endHour - startHour) * perHour + 1];

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, startHour);
        c.set(Calendar.MINUTE, 0);

        for (int i = 0; i < oms.length; i++)
        {
            String time = FastDateFormat.getInstance("HH" + separator + "mm").format(c);

            oms[i] = new OptionModelImpl(time, new TimeSlot(i / perHour + startHour, (i % perHour) * minutes));

            c.add(Calendar.MINUTE, minutes);
        }

        SelectModel hoursModel = new SelectModelImpl(oms);

        return hoursModel;
    }
}
