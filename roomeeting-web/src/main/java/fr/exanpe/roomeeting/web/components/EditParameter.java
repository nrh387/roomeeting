package fr.exanpe.roomeeting.web.components;

import java.util.Date;

import org.apache.commons.lang.time.FastDateFormat;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

public class EditParameter
{
    @Parameter(autoconnect = true)
    @Property
    private fr.exanpe.roomeeting.domain.model.ref.Parameter parameter;

    public boolean testIsNull(Object o)
    {
        return o == null;
    }

    public String toStringDate(Date d)
    {
        return FastDateFormat.getInstance("dd/MM/yyyy").format(d);
    }
}
