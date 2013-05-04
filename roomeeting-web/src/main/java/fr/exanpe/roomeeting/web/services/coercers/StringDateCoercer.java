package fr.exanpe.roomeeting.web.services.coercers;

import java.util.Date;

import org.apache.commons.lang.time.FastDateFormat;
import org.apache.tapestry5.ioc.services.Coercion;

public class StringDateCoercer implements Coercion<Date, String>
{

    @Override
    public String coerce(Date input)
    {
        if (input == null) { return null; }

        return FastDateFormat.getInstance("yyyyMMdd").format(input);
    }
}
