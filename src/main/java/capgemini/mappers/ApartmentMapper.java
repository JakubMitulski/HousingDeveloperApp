package capgemini.mappers;

import capgemini.dto.ApartmentTo;
import capgemini.entities.ApartmentEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ApartmentMapper {
    public static ApartmentTo toApartmentTo(ApartmentEntity apartmentEntity) {
        if (apartmentEntity == null)
            return null;

        return new ApartmentTo.ApartmentToBuilder()
                .withVersion(apartmentEntity.getVersion())
                .withId(apartmentEntity.getId())
                .withArea(apartmentEntity.getArea())
                .withRoomsAmount(apartmentEntity.getRoomsAmount())
                .withBalconiesAmount(apartmentEntity.getBalconiesAmount())
                .withFloorNumber(apartmentEntity.getFloorNumber())
                .withAddress(apartmentEntity.getAddress())
                .withStatus(apartmentEntity.getStatus())
                .withBuildingId(apartmentEntity.getBuilding().getId())
                .withPrice(apartmentEntity.getPrice())
                .build();
    }

    public static ApartmentEntity toApartmentEntity(ApartmentTo apartmentTo) {
        if (apartmentTo == null)
            return null;

        ApartmentEntity apartmentEntity = new ApartmentEntity();
        apartmentEntity.setVersion(apartmentTo.getVersion());
        apartmentEntity.setId(apartmentTo.getId());
        apartmentEntity.setArea(apartmentTo.getArea());
        apartmentEntity.setRoomsAmount(apartmentTo.getRoomsAmount());
        apartmentEntity.setBalconiesAmount(apartmentTo.getBalconiesAmount());
        apartmentEntity.setFloorNumber(apartmentTo.getFloorNumber());
        apartmentEntity.setAddress(apartmentTo.getAddress());
        apartmentEntity.setStatus(apartmentTo.getStatus());
        apartmentEntity.setPrice(apartmentTo.getPrice());

        return apartmentEntity;
    }

    public static List<ApartmentTo> map2Tos(List<ApartmentEntity> apartmentEntities) {
        return apartmentEntities.stream().map(ApartmentMapper::toApartmentTo).collect(Collectors.toList());
    }

    public static List<ApartmentEntity> map2Entities(List<ApartmentTo> apartmentTos) {
        return apartmentTos.stream().map(ApartmentMapper::toApartmentEntity).collect(Collectors.toList());
    }
}
