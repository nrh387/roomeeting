package fr.exanpe.roomeeting.web.services.coercers;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.ioc.services.Coercion;

import fr.exanpe.roomeeting.domain.business.dto.TimeSlot;

public class StringTimeSlotCoercer implements Coercion<String, TimeSlot>
{

    @Override
    public TimeSlot coerce(String input)
    {
        if (StringUtils.isEmpty(input)) { return null; }

        String[] hm = input.split("_");

        return new TimeSlot(Integer.parseInt(hm[0]), Integer.parseInt(hm[1]));
    }
}
