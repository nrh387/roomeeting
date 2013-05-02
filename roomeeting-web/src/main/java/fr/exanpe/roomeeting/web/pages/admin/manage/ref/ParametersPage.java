package fr.exanpe.roomeeting.web.pages.admin.manage.ref;

import org.apache.tapestry5.ioc.annotations.Inject;

import fr.exanpe.roomeeting.domain.business.ParameterManager;
import fr.exanpe.roomeeting.domain.core.business.DefaultManager;
import fr.exanpe.roomeeting.domain.model.ref.Parameter;
import fr.exanpe.roomeeting.web.base.AbstractRefPage;

public class ParametersPage extends AbstractRefPage<Parameter>
{
    @Inject
    private ParameterManager parameterManager;

    @Override
    public DefaultManager<Parameter, Long> getManager()
    {
        return parameterManager;
    }

    @Override
    public boolean save(Parameter t)
    {
        parameterManager.update(t);

        return true;
    }
}
