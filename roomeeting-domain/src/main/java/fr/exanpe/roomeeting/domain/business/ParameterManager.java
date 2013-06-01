/**
 * 
 */
package fr.exanpe.roomeeting.domain.business;

import fr.exanpe.roomeeting.common.enums.ParameterEnum;
import fr.exanpe.roomeeting.domain.core.business.DefaultManager;
import fr.exanpe.roomeeting.domain.model.ref.Parameter;

public interface ParameterManager extends DefaultManager<Parameter, Long>
{
    Parameter find(ParameterEnum e);

    Integer findInteger(ParameterEnum e);

    String findString(ParameterEnum e);
}
