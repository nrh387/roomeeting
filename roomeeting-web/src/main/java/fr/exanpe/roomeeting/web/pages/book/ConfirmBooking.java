package fr.exanpe.roomeeting.web.pages.book;

import org.apache.commons.io.IOUtils;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionAttribute;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.RequestGlobals;

import fr.exanpe.roomeeting.common.exception.BusinessException;
import fr.exanpe.roomeeting.domain.business.ICalManager;
import fr.exanpe.roomeeting.domain.model.Booking;
import fr.exanpe.roomeeting.web.pages.Home;
import fr.exanpe.roomeeting.web.services.FileStreamResponse;

public class ConfirmBooking
{
    @SessionAttribute
    @Property
    private Booking booking;

    @Inject
    private ICalManager iCalManager;

    @Inject
    private Messages messages;

    @Inject
    private RequestGlobals requestGlobals;

    @Property
    @Persist(PersistenceConstants.FLASH)
    private String exportMessage;

    Object onActivate()
    {
        if (booking == null) { return Search.class; }

        return null;
    }

    @OnEvent(value = EventConstants.ACTION, component = "exportICal")
    StreamResponse exportOutlook() throws BusinessException
    {
        if (booking == null)
        {
            exportMessage = messages.get("booking-flushed");
            return null;
        }

        String export = iCalManager.generateICal(booking);

        if (export == null)
        {
            exportMessage = messages.get("export-error");
            return null;
        }

        return new FileStreamResponse(IOUtils.toInputStream(export), "text/calendar", iCalManager.generateICalName(booking));
    }

    @OnEvent(value = EventConstants.ACTION, component = "end")
    Object end()
    {
        booking = null;
        return Home.class;
    }
}
