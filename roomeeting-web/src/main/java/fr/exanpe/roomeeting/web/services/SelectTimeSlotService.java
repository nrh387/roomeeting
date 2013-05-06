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

        return create(new TimeSlot(startHour, 0), new TimeSlot(endHour, 0), 30);
    }

    public SelectModel create(TimeSlot start, TimeSlot end, int minutes)
    {
        String separator = messages.get("hour-separator");

        int perHour = 60 / minutes;

        // int numberElements = (end.getHours() - start.getHours() - 1) * perHour + 1;
        int numberElements = (end.getMinutes() - start.getMinutes()) / minutes;

        OptionModel[] oms = new OptionModel[(end.getHours() - start.getHours()) * perHour + 1 + numberElements];

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, start.getHours());
        c.set(Calendar.MINUTE, start.getMinutes());

        for (int i = 0; i < oms.length; i++)
        {
            String time = FastDateFormat.getInstance("HH" + separator + "mm").format(c);

            oms[i] = new OptionModelImpl(time, new TimeSlot(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE)));

            c.add(Calendar.MINUTE, minutes);
        }

        SelectModel hoursModel = new SelectModelImpl(oms);

        return hoursModel;
    }
}
