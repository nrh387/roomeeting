package fr.exanpe.roomeeting.t5.lib.services;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyShadowBuilder;
import org.apache.tapestry5.services.Environment;
import org.apache.tapestry5.services.LibraryMapping;
import org.apache.tapestry5.services.MarkupRendererFilter;

import fr.exanpe.roomeeting.t5.lib.services.impl.FlashMessagesManagerImpl;

/**
 * Module Tapestry pour RooMeeting.
 * 
 * @author lguerin
 */
public class RooMeetingLibraryModule
{
    public static void contributeComponentClassResolver(Configuration<LibraryMapping> configuration)
    {
        // Mapping for roomeeting prefix
        configuration.add(new LibraryMapping("roomeeting", "fr.exanpe.roomeeting.t5.lib"));
    }

    public static void bind(ServiceBinder binder)
    {
        binder.bind(FlashMessagesManager.class, FlashMessagesManagerImpl.class);
    }

    /**
     * Activation de notre filtre {@link FlashMessagesMarkupRendererFilter}.
     * 
     * @param flashMessagesManager
     * @param flashMessagesJsScript
     * @param environment
     * @param contributions
     */
    public void contributeMarkupRenderer(FlashMessagesManager flashMessagesManager, @Inject
    @Path("assets/flashMessages/flashMessages.js")
    Asset flashMessagesJsScript, @Inject
    @Path("assets/flashMessages/flashMessages.css")
    Asset flashMessagesCss, Environment environment, OrderedConfiguration<MarkupRendererFilter> contributions)
    {
        contributions.add(
                "flashMessages",
                new FlashMessagesMarkupRendererFilter(flashMessagesJsScript, flashMessagesCss, environment, flashMessagesManager),
                "after:JavascriptSupport");
    }

    public static void contributeClasspathAssetAliasManager(MappedConfiguration<String, String> configuration)
    {
        configuration.add("assets", "assets");
    }

    /**
     * Un service Tapestry est par défaut un singleton dont le scope est global à l'application.
     * L'utilisation du {@link PropertyShadowBuilder} permet d'utiliser une copie des propriétés de
     * l'objet transmis, en mode perthread, i.e spécifique au thread courant, et donc par la même
     * occasion à la requête courante.
     * Cela permet de garantir que l'utilisation du service est thread-safe.
     * 
     * @param flashMessagesManager Service de notification de {@link FlashMessages}
     * @param builder Builder Tapestry
     * @return
     */
    public FlashMessages build(FlashMessagesManager flashMessagesManager, PropertyShadowBuilder builder)
    {
        return builder.build(flashMessagesManager, "flashMessages", FlashMessages.class);
    }

}
