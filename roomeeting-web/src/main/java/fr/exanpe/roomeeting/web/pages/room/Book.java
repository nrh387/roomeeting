package fr.exanpe.roomeeting.web.pages.room;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionAttribute;

import fr.exanpe.roomeeting.domain.model.Gap;

public class Book
{

    @SessionAttribute
    @Property
    private Gap bookGap;

    Object onActivate()
    {
        if (bookGap == null) { return Search.class; }

        return null;
    }

}
