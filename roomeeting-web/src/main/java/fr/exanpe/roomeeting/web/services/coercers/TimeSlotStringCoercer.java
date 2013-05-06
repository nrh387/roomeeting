package fr.exanpe.roomeeting.web.services.coercers;

import org.apache.tapestry5.ioc.services.Coercion;

import fr.exanpe.roomeeting.domain.business.dto.TimeSlot;

public class TimeSlotStringCoercer implements Coercion<TimeSlot, String>
{

    @Override
    public String coerce(TimeSlot input)
    {
        if (input == null) { return null; }

        return input.getHours() + "_" + input.getMinutes();
    }
}
