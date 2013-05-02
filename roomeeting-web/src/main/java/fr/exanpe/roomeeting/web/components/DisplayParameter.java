package fr.exanpe.roomeeting.web.components;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

public class DisplayParameter
{
    @Parameter
    @Property
    private fr.exanpe.roomeeting.domain.model.ref.Parameter parameter;

    public boolean testIsNull(Object o)
    {
        return o == null;
    }

}
