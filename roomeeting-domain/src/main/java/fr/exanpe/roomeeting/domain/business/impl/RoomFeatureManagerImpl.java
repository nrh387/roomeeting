/**
 * 
 */
package fr.exanpe.roomeeting.domain.business.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.exanpe.roomeeting.domain.business.RoomFeatureManager;
import fr.exanpe.roomeeting.domain.core.business.impl.DefaultManagerImpl;
import fr.exanpe.roomeeting.domain.core.dao.CrudDAO;
import fr.exanpe.roomeeting.domain.model.ref.RoomFeature;

@Service
public class RoomFeatureManagerImpl extends DefaultManagerImpl<RoomFeature, Long> implements RoomFeatureManager
{
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomFeatureManagerImpl.class);

    @Autowired
    private CrudDAO crudDAO;

}
