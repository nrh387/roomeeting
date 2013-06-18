package fr.exanpe.roomeeting.web.services.binding;

import org.apache.tapestry5.Binding;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.ioc.Location;
import org.apache.tapestry5.services.BindingFactory;
import org.apache.tapestry5.services.BindingSource;

public class OptionalMessageBindingFactory implements BindingFactory
{
    private BindingSource bindingSource;

    public OptionalMessageBindingFactory(BindingSource bindingSource)
    {
        this.bindingSource = bindingSource;
    }

    @Override
    public Binding newBinding(String description, ComponentResources container, ComponentResources component, String expression, Location location)
    {
        Binding b = bindingSource.newBinding(description, container, BindingConstants.PROP, expression);

        return new OptionalMessageBinding(container, b);
    }

}
