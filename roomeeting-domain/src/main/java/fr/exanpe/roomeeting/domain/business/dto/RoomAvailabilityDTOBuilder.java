package fr.exanpe.roomeeting.domain.business.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.MultiKeyMap;

import fr.exanpe.roomeeting.domain.model.Gap;
import fr.exanpe.roomeeting.domain.model.Room;

public class RoomAvailabilityDTOBuilder
{
    public static RoomAvailabilityDTOBuilder create()
    {
        return new RoomAvailabilityDTOBuilder();
    }

    private List<DateAvailabilityDTO> dateAvailabilityDTOs = new ArrayList<DateAvailabilityDTO>();

    private MultiKeyMap mkmap = new MultiKeyMap();

    public RoomAvailabilityDTOBuilder organise(Room room, Date date)
    {
        if (CollectionUtils.isEmpty(dateAvailabilityDTOs) || !date.equals(dateAvailabilityDTOs.get(dateAvailabilityDTOs.size() - 1).getDate()))
        {
            dateAvailabilityDTOs.add(new DateAvailabilityDTO(date));
        }

        DateAvailabilityDTO dateDTO = dateAvailabilityDTOs.get(dateAvailabilityDTOs.size() - 1);

        if (CollectionUtils.isEmpty(dateDTO.getSiteAvailabilityDTOs())
                || !room.getSite().equals(dateDTO.getSiteAvailabilityDTOs().get(dateDTO.getSiteAvailabilityDTOs().size() - 1).getSite()))
        {
            dateDTO.getSiteAvailabilityDTOs().add(new SiteAvailabilityDTO(room.getSite()));
        }

        SiteAvailabilityDTO siteDTO = dateDTO.getSiteAvailabilityDTOs().get(dateDTO.getSiteAvailabilityDTOs().size() - 1);

        RoomAvailabilityDTO roomavailibity = new RoomAvailabilityDTO(room);

        siteDTO.getRoomAvailabilityDTOs().add(roomavailibity);

        mkmap.put(date, room, roomavailibity);

        return this;
    }

    public RoomAvailabilityDTOBuilder organise(Gap gap, Date dateSearch)
    {
        RoomAvailabilityDTO roomavailibity = (RoomAvailabilityDTO) mkmap.get(dateSearch, gap.getRoom());
        roomavailibity.getGaps().add(gap);

        return this;
    }

    public List<DateAvailabilityDTO> getResult()
    {
        return dateAvailabilityDTOs;
    }
}
