/**
 * 
 */
package fr.exanpe.roomeeting.domain.business.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import fr.exanpe.roomeeting.domain.business.ParameterManager;
import fr.exanpe.roomeeting.domain.core.business.impl.DefaultManagerImpl;
import fr.exanpe.roomeeting.domain.model.ref.Parameter;

@Service
public class ParameterManagerImpl extends DefaultManagerImpl<Parameter, Long> implements ParameterManager
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ParameterManagerImpl.class);

}
