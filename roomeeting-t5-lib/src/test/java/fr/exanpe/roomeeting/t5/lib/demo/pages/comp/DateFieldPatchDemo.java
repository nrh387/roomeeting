package fr.exanpe.roomeeting.t5.lib.demo.pages.comp;

import java.util.Date;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;

import fr.exanpe.roomeeting.t5.lib.components.DateFieldPatch;

public class DateFieldPatchDemo
{
    /** Composant du champ date */
    @SuppressWarnings("unused")
    @Component
    private DateFieldPatch date;

    @SuppressWarnings("unused")
    @Property
    private Date testDate;

    @SetupRender
    void setup()
    {
        testDate = new Date();
    }
}
