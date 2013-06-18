package fr.exanpe.roomeeting.web.services.binding;

import org.apache.tapestry5.Binding;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.internal.bindings.AbstractBinding;

public class OptionalMessageBinding extends AbstractBinding
{

    private ComponentResources container;

    private Binding delegate;

    public OptionalMessageBinding(ComponentResources container, Binding delegate)
    {
        this.container = container;
        this.delegate = delegate;
    }

    @Override
    public Class<String> getBindingType()
    {
        return String.class;
    }

    @Override
    public boolean isInvariant()
    {
        return false;
    }

    @Override
    public Object get()
    {
        Object o = (Object) delegate.get();
        if (o == null) { return ""; }

        if (!(o instanceof String)) { throw new IllegalArgumentException("optmessage must be used with a String key. Provided : " + o.getClass().getName()); }
        String s = (String) o;
        if (container.getMessages().contains(s)) { return container.getMessages().get(s); }
        return s;
    }
}
