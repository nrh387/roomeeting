package fr.exanpe.roomeeting.t5.lib.services;

import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.services.LibraryMapping;

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

    }

    public static void contributeClasspathAssetAliasManager(MappedConfiguration<String, String> configuration)
    {
        configuration.add("assets", "assets");
    }

}
