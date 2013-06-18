package fr.exanpe.roomeeting.web.services;

import java.util.Date;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.Translator;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.services.CoercionTuple;
import org.apache.tapestry5.services.BindingFactory;
import org.apache.tapestry5.services.BindingSource;
import org.apache.tapestry5.services.ComponentRequestFilter;
import org.apache.tapestry5.services.ComponentSource;
import org.apache.tapestry5.services.RequestExceptionHandler;
import org.apache.tapestry5.services.RequestGlobals;
import org.apache.tapestry5.services.ResponseRenderer;
import org.apache.tapestry5.upload.services.UploadSymbols;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;

import fr.exanpe.roomeeting.domain.business.SiteManager;
import fr.exanpe.roomeeting.domain.business.UserManager;
import fr.exanpe.roomeeting.domain.business.dto.TimeSlot;
import fr.exanpe.roomeeting.domain.model.Role;
import fr.exanpe.roomeeting.domain.model.Site;
import fr.exanpe.roomeeting.t5.lib.services.RooMeetingLibraryModule;
import fr.exanpe.roomeeting.web.services.binding.OptionalMessageBindingFactory;
import fr.exanpe.roomeeting.web.services.coercers.DateStringCoercer;
import fr.exanpe.roomeeting.web.services.coercers.RoleStringCoercer;
import fr.exanpe.roomeeting.web.services.coercers.SiteStringCoercer;
import fr.exanpe.roomeeting.web.services.coercers.StringDateCoercer;
import fr.exanpe.roomeeting.web.services.coercers.StringRoleCoercer;
import fr.exanpe.roomeeting.web.services.coercers.StringSiteCoercer;
import fr.exanpe.roomeeting.web.services.coercers.StringTimeSlotCoercer;
import fr.exanpe.roomeeting.web.services.coercers.TimeSlotStringCoercer;
import fr.exanpe.roomeeting.web.services.exceptionHandler.ExceptionHandlerService;
import fr.exanpe.roomeeting.web.services.exceptionHandler.RooMeetingRequestExceptionHandler;
import fr.exanpe.roomeeting.web.services.translators.DateTranslator;
import fr.exanpe.roomeeting.web.services.translators.SiteTranslator;
import fr.exanpe.t5.lib.constants.ExanpeSymbols;
import fr.exanpe.t5.lib.internal.contextpagereset.ContextPageResetFilter;
import fr.exanpe.t5.lib.services.ExanpeLibraryModule;

/**
 * This module is automatically included as part of the Tapestry IoC Registry, it's a good place to
 * configure and extend Tapestry, or to place your own service definitions.
 */
@SubModule(
{ ExanpeLibraryModule.class, RooMeetingLibraryModule.class })
public class RooMeetingModule
{
    public static void bind(ServiceBinder binder)
    {
        binder.bind(ApplicationListener.class).eagerLoad();
        binder.bind(ExceptionHandlerService.class);
        binder.bind(SelectTimeSlotService.class);
        binder.bind(OptionalMessageService.class);
    }

    public static void contributeApplicationDefaults(MappedConfiguration<String, String> configuration, @InjectService("applicationContext")
    ApplicationContext applicationContext)
    {

        // This code is a bridge to Spring profiles
        boolean productionMode = true;

        String[] profiles = applicationContext.getEnvironment().getActiveProfiles();

        if (profiles != null)
        {
            for (String s : profiles)
            {
                if (s.equals("embedded"))
                {
                    productionMode = false;
                    break;
                }
            }
        }

        configuration.add(SymbolConstants.SUPPORTED_LOCALES, "en,fr");

        configuration.add(SymbolConstants.PRODUCTION_MODE, "" + productionMode);

        configuration.add(SymbolConstants.APPLICATION_VERSION, "1.0.0-SNAPSHOT");

        // < 100kb
        configuration.add(UploadSymbols.FILESIZE_MAX, "99000");
        // 100kb
        configuration.add(UploadSymbols.REPOSITORY_THRESHOLD, "100000");

        configuration.add(ExanpeSymbols.CONTEXT_PAGE_RESET_MARKER, "_cleanup_");
    }

    public static void contributeBindingSource(MappedConfiguration<String, BindingFactory> configuration, BindingSource bindingSource)
    {
        configuration.add("optmessage", new OptionalMessageBindingFactory(bindingSource));
    }

    public void contributeComponentRequestHandler(OrderedConfiguration<ComponentRequestFilter> configuration)
    {
        configuration.addInstance("ContextPageResetFilter", ContextPageResetFilter.class);
        // configuration.addInstance("LocaleFromHeaderFilter", LocaleFromHeaderFilter.class);
        // configuration.addInstance("LocaleSessionFilter", LocaleSessionRequestFilter.class);
    }

    public RequestExceptionHandler decorateRequestExceptionHandler(final Logger logger, final ResponseRenderer renderer, final ComponentSource componentSource,
            @Symbol(SymbolConstants.PRODUCTION_MODE)
            boolean productionMode, Object service, ExceptionHandlerService exceptionService, RequestGlobals request)
    {
        return new RooMeetingRequestExceptionHandler(exceptionService, componentSource, renderer, request, productionMode);
    }

    @SuppressWarnings("rawtypes")
    public static void contributeTranslatorSource(MappedConfiguration<Class, Translator> configuration, @InjectService("siteManager")
    SiteManager siteManager)
    {
        configuration.add(Site.class, new SiteTranslator(siteManager));
        configuration.add(Date.class, new DateTranslator());
    }

    public static void contributeTypeCoercer(Configuration<CoercionTuple<?, ?>> configuration, @InjectService("siteManager")
    SiteManager siteManager, @InjectService("userManager")
    UserManager userManager)
    {
        configuration.add(new CoercionTuple<String, Site>(String.class, Site.class, new StringSiteCoercer(siteManager)));
        configuration.add(new CoercionTuple<Site, String>(Site.class, String.class, new SiteStringCoercer()));

        configuration.add(new CoercionTuple<String, Role>(String.class, Role.class, new StringRoleCoercer(userManager)));
        configuration.add(new CoercionTuple<Role, String>(Role.class, String.class, new RoleStringCoercer()));

        configuration.add(new CoercionTuple<Date, String>(Date.class, String.class, new StringDateCoercer()));
        configuration.add(new CoercionTuple<String, Date>(String.class, Date.class, new DateStringCoercer()));

        configuration.add(new CoercionTuple<TimeSlot, String>(TimeSlot.class, String.class, new TimeSlotStringCoercer()));
        configuration.add(new CoercionTuple<String, TimeSlot>(String.class, TimeSlot.class, new StringTimeSlotCoercer()));

    }

    public static void contributeIgnoredPathsFilter(Configuration<String> configuration)
    {
        configuration.add("/h2.*");
    }
}
