package fr.exanpe.roomeeting.domain.database;

import org.springframework.core.io.Resource;

public interface DatabaseVersion
{
    String getVersion();

    Resource[] getResources();
}
