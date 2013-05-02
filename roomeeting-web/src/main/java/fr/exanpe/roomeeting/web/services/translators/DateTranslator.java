package fr.exanpe.roomeeting.web.services.translators;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.apache.tapestry5.Field;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.internal.translator.AbstractTranslator;
import org.apache.tapestry5.services.FormSupport;

public class DateTranslator extends AbstractTranslator<Date>
{

    public DateTranslator()
    {
        super("date-translator", Date.class, "date-format-exception");
    }

    private static final String DATE_FORMAT = "dd/MM/yyyy";

    @Override
    public String toClient(Date value)
    {
        if (value == null) { return ""; }
        return FastDateFormat.getInstance(DATE_FORMAT).format(value);
    }

    @Override
    public Date parseClient(Field field, String clientValue, String message) throws ValidationException
    {
        if (StringUtils.isEmpty(clientValue)) { return null; }

        try
        {
            return DateUtils.parseDate(clientValue, new String[]
            { DATE_FORMAT });
        }
        catch (ParseException ex)
        {
            throw new ValidationException(message);
        }
    }

    @Override
    public void render(Field field, String message, MarkupWriter writer, FormSupport formSupport)
    {

    }

}
