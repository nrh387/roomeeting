package fr.exanpe.roomeeting.web.components;

import java.util.List;

import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.ValidationTracker;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.internal.InternalMessages;

public class FormErrors
{
    // Allow null so we can generate a better error message if missing
    @Environmental(false)
    private ValidationTracker tracker;

    @Property
    private List<String> errors;

    @Property
    private String error;

    boolean setupRender(MarkupWriter writer)
    {
        if (tracker == null)
            throw new RuntimeException(InternalMessages.encloseErrorsInForm());

        if (!tracker.getHasErrors())
            return false;

        errors = tracker.getErrors();

        return true;
    }
}
