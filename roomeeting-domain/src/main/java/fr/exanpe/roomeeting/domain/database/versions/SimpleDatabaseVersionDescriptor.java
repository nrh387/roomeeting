package fr.exanpe.roomeeting.domain.database.versions;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import fr.exanpe.roomeeting.domain.database.DatabaseVersion;

public class SimpleDatabaseVersionDescriptor implements DatabaseVersion
{
    private static final PathMatchingResourcePatternResolver RESOURCE_LOADER = new PathMatchingResourcePatternResolver();

    private String version;

    private String location;

    private Resource[] resources;

    public SimpleDatabaseVersionDescriptor(String version, String location)
    {
        this.version = version;
        this.location = location;
    }

    @PostConstruct
    public void loadUpResources() throws IOException
    {
        resources = RESOURCE_LOADER.getResources(location);
    }

    public String getVersion()
    {
        return version;
    }

    @Override
    public Resource[] getResources()
    {
        return resources;
    }
}
