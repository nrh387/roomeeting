/**
 * 
 */
package fr.exanpe.roomeeting.t5.lib.services;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Environment;
import org.apache.tapestry5.services.MarkupRenderer;
import org.apache.tapestry5.services.MarkupRendererFilter;
import org.apache.tapestry5.services.javascript.InitializationPriority;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 * Implémentation d'un pipeline de type {@link MarkupRenderer} afin de pouvoir utiliser notre
 * service de notifications de {@link FlashMessages} de manière transverse, au travers de toutes les
 * pages et composants de l'application.
 * 
 * @author lguerin
 */
public class FlashMessagesMarkupRendererFilter implements MarkupRendererFilter
{
    private Environment environment;
    private Asset flashMessagesJsScript;
    private Asset flashMessagesCss;
    private FlashMessagesManager flashMessagesManager;

    public FlashMessagesMarkupRendererFilter(Asset flashMessagesJsScript, Asset flashMessagesCss, Environment environment,
            FlashMessagesManager flashMessagesManager)
    {
        this.environment = environment;
        this.flashMessagesManager = flashMessagesManager;
        this.flashMessagesJsScript = flashMessagesJsScript;
        this.flashMessagesCss = flashMessagesCss;
    }

    public void renderMarkup(MarkupWriter writer, MarkupRenderer renderer)
    {
        if (environment.peek(JavaScriptSupport.class) != null)
        {
            FlashMessages flashMessages = flashMessagesManager.getFlashMessages();
            JavaScriptSupport javaScriptSupport = environment.peek(JavaScriptSupport.class);
            if (flashMessages.getHasMessages())
            {
                JSONObject spec = new JSONObject();

                // Messages de type "information"
                spec.put("informations", new JSONArray(flashMessages.getInformations().toArray()));

                javaScriptSupport.importJavaScriptLibrary(flashMessagesJsScript);
                javaScriptSupport.importStylesheet(flashMessagesCss);
                javaScriptSupport.addScript(InitializationPriority.LATE, "FlashMessages.display(%s);", spec);
                flashMessages.clear();
            }
        }
        renderer.renderMarkup(writer);
    }

}
