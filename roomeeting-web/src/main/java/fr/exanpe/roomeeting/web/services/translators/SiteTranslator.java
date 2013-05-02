package fr.exanpe.roomeeting.web.services.translators;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.Field;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.internal.translator.AbstractTranslator;
import org.apache.tapestry5.services.FormSupport;

import fr.exanpe.roomeeting.domain.business.SiteManager;
import fr.exanpe.roomeeting.domain.model.Site;

public class SiteTranslator extends AbstractTranslator<Site>
{

    private SiteManager siteManager;

    public SiteTranslator(SiteManager siteManager)
    {
        super("site-translator", Site.class, "date-format-exception");

        this.siteManager = siteManager;
    }

    @Override
    public String toClient(Site value)
    {
        if (value == null) { return ""; }

        return value.getId().toString();
    }

    @Override
    public Site parseClient(Field field, String clientValue, String message) throws ValidationException
    {
        if (StringUtils.isEmpty(clientValue)) { return null; }

        return siteManager.find(Long.parseLong(clientValue));
    }

    @Override
    public void render(Field field, String message, MarkupWriter writer, FormSupport formSupport)
    {

    }

}
