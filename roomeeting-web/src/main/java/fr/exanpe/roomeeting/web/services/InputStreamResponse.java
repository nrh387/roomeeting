package fr.exanpe.roomeeting.web.services;

import java.io.IOException;
import java.io.InputStream;

import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.services.Response;

import fr.exanpe.roomeeting.web.components.RestImage;

/**
 * Specific response to handle a stream
 * 
 * @see RestImage
 * @author jmaupoux
 */
public class InputStreamResponse implements StreamResponse
{
    /**
     * The stream to return
     */
    private final InputStream inputStream;

    /**
     * The content type to return
     */
    private final String contentType;

    public InputStreamResponse(InputStream inputStream, String contentType)
    {
        this.inputStream = inputStream;
        this.contentType = contentType;
    }

    public String getContentType()
    {
        return contentType;
    }

    public InputStream getStream() throws IOException
    {
        return inputStream;
    }

    public void prepareResponse(Response arg0)
    {
        arg0.setHeader("Cache-Control", "no-cache");
    }
}
