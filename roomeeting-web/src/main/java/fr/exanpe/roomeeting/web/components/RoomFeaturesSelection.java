package fr.exanpe.roomeeting.web.components;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.corelib.base.AbstractField;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.services.Request;

import fr.exanpe.roomeeting.domain.business.RoomFeatureManager;
import fr.exanpe.roomeeting.domain.model.ref.RoomFeature;

public class RoomFeaturesSelection extends AbstractField
{

    @Inject
    private Request request;

    @Inject
    private RoomFeatureManager roomFeatureManager;

    @Persist
    @Property
    private List<RoomFeature> features;

    @Property
    private RoomFeature currentFeature;

    @Parameter(required = true, autoconnect = true)
    private List<RoomFeature> selected;

    @SetupRender
    void init()
    {
        features = roomFeatureManager.list();
    }

    @Override
    protected void processSubmission(final String controlName)
    {

        final String[] parameters = request.getParameters(controlName);

        List<RoomFeature> selected = this.selected;

        if (selected == null)
        {
            selected = CollectionFactory.newList();
        }
        else
        {
            selected.clear();
        }

        if (parameters != null)
        {
            for (final String value : parameters)
            {
                final RoomFeature objectValue = roomFeatureManager.find(Long.parseLong(value));

                selected.add(objectValue);
            }

        }
    }

    Set<RoomFeature> getSelected()
    {
        if (selected == null) { return Collections.emptySet(); }

        return CollectionFactory.newSet(selected);
    }

    @Override
    public boolean isRequired()
    {
        return false;
    }

    public boolean isFeatureSelected()
    {
        return selected.contains(currentFeature);
    }

    public String getCssClassIfSelected()
    {
        if (isFeatureSelected()) { return "active"; }
        return "";
    }

}
