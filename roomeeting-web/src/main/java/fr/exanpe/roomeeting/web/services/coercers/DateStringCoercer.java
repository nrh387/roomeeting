package fr.exanpe.roomeeting.web.services.coercers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.tapestry5.ioc.services.Coercion;

public class DateStringCoercer implements Coercion<String, Date>
{

    @Override
    public Date coerce(String input)
    {
        if (input == null) { return null; }

        try
        {
            return new SimpleDateFormat("yyyyMMdd").parse(input);
        }
        catch (ParseException e)
        {
            return null;
        }
    }
}
