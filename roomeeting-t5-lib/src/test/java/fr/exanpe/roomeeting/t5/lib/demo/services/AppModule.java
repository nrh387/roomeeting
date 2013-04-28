package fr.exanpe.roomeeting.t5.lib.demo.services;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.SubModule;

import fr.exanpe.roomeeting.t5.lib.services.RooMeetingLibraryModule;

@SubModule(RooMeetingLibraryModule.class)
public class AppModule
{
    public static void contributeApplicationDefaults(MappedConfiguration<String, String> configuration)
    {
        configuration.add(SymbolConstants.PRODUCTION_MODE, "false");
    }

    public static void bind(ServiceBinder binder)
    {
        binder.bind(DummyDataService.class);
    }
}
