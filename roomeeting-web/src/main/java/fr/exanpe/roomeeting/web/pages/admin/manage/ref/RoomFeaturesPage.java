package fr.exanpe.roomeeting.web.pages.admin.manage.ref;

import java.io.IOException;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.io.IOUtils;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.upload.services.UploadedFile;

import fr.exanpe.roomeeting.domain.business.RoomFeatureManager;
import fr.exanpe.roomeeting.domain.core.business.DefaultManager;
import fr.exanpe.roomeeting.domain.model.ref.RoomFeature;
import fr.exanpe.roomeeting.web.base.AbstractRefPage;

public class RoomFeaturesPage extends AbstractRefPage<RoomFeature>
{
    @Inject
    private RoomFeatureManager roomFeatureManager;

    @Property
    private UploadedFile icon;

    @Property
    @Persist(PersistenceConstants.FLASH)
    private String errorUpload;

    @Inject
    private Messages messages;

    Object onUploadException(FileUploadException ex)
    {
        errorUpload = messages.get("file-error");

        return this;
    }

    @Override
    public DefaultManager<RoomFeature, Long> getManager()
    {
        return roomFeatureManager;
    }

    @Override
    public boolean save(RoomFeature t)
    {
        if (icon != null)
        {
            if (!(icon.getFileName().endsWith(".png") || icon.getFileName().endsWith(".jpg") || icon.getFileName().endsWith(".jpeg")))
            {
                errorUpload = messages.get("file-format");
                return false;
            }
            byte[] b;

            try
            {
                b = IOUtils.toByteArray(icon.getStream());
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }

            t.setIcon(b);
        }

        if (t.getIcon() == null || t.getIcon().length == 0)
        {
            errorUpload = messages.get("file-required");
            return false;
        }

        roomFeatureManager.update(t);

        return true;
    }
}
