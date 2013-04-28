package fr.exanpe.roomeeting.web.components;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.annotations.Inject;

import fr.exanpe.roomeeting.domain.business.RoomFeatureManager;
import fr.exanpe.roomeeting.web.services.InputStreamResponse;

/**
 * Component rendering an &lt;img&gt; from an InputStream.
 * Do not create any physical file.<br/>
 * The binary image will be copied in session before rendered to the user. The inputStream parameter
 * will be automatically closed by the component once in session.<br/>
 * Once consumed by the browser, the image is automatically cleared.
 * A clear() method allow you to clear the inputstream data if not consumed by the browser (you can
 * also discard persistent fields on your page).<br/>
 * <br/>
 * If an error occurs with the stream parameter, an error is logged and the image of parameter
 * "errorImage" is displayed.
 * 
 * @author jmaupoux
 * @since 1.3
 */
@SupportsInformalParameters
public class RestImage<T>
{
    /**
     * Render event
     */
    private static final String EVENT = "roomFeatureIcon";

    /**
     * Content type of the image. Default to image/jpeg
     */
    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL, value = "image/jpeg")
    private String contentType;

    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String imageType;

    @Parameter
    private T context;

    @Inject
    private RoomFeatureManager roomFeatureManager;

    /**
     * resources
     */
    @Inject
    private ComponentResources resources;

    @BeginRender
    void init(MarkupWriter writer) throws IOException
    {
        String src = resources.createEventLink(imageType, context).toURI();

        writer.element("img", "src", src);

        resources.renderInformalParameters(writer);

        writer.end();
    }

    /**
     * Event rendering the image
     * 
     * @return the stream response containing the image
     */
    @OnEvent(value = "roomFeatureIcon")
    StreamResponse render(Long id)
    {
        StreamResponse response = new InputStreamResponse(new ByteArrayInputStream(roomFeatureManager.find(id).getIcon()), contentType);

        return response;
    }
}
