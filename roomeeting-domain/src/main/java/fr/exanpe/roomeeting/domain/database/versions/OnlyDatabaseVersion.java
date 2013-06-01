package fr.exanpe.roomeeting.domain.database.versions;

import org.springframework.core.io.Resource;

import fr.exanpe.roomeeting.domain.database.DatabaseVersion;

public class OnlyDatabaseVersion implements DatabaseVersion
{
    private String version;

    public OnlyDatabaseVersion(String version)
    {
        this.version = version;
    }

    public String getVersion()
    {
        return version;
    }

    @Override
    public Resource[] getResources()
    {
        throw new UnsupportedOperationException("not implemented");
    }
}
