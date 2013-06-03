/**
 * 
 */
package fr.exanpe.roomeeting.domain.business;

import fr.exanpe.roomeeting.domain.core.business.DefaultManager;
import fr.exanpe.roomeeting.domain.model.ref.RoomFeature;

public interface RoomFeatureManager extends DefaultManager<RoomFeature, Long>
{
    void create(String name, String classpathIcon);
}
