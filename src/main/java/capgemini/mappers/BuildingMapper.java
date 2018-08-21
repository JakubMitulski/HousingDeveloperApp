package capgemini.mappers;

import capgemini.dto.BuildingTo;
import capgemini.entities.ApartmentEntity;
import capgemini.entities.BuildingEntity;

import java.util.List;
import java.util.stream.Collectors;

public class BuildingMapper {

    public static BuildingTo toBuildingTo(BuildingEntity buildingEntity) {
        if (buildingEntity == null)
            return null;

        return new BuildingTo.BuildingToBuilder()
                .withVersion(buildingEntity.getVersion())
                .withId(buildingEntity.getId())
                .withDescription(buildingEntity.getDescription())
                .withLocation(buildingEntity.getLocation())
                .withFloorsNumber(buildingEntity.getFloorsNumber())
                .withElevator(buildingEntity.getHasElevator())
                .withApartmentsNumber(buildingEntity.getApartmentsNumber())
                .withApartmentIds(buildingEntity.getApartments()
                        .stream()
                        .map(ApartmentEntity::getId)
                        .collect(Collectors.toSet()))
                .build();
    }

    public static BuildingEntity toBuildingEntity(BuildingTo buildingTo) {
        if (buildingTo == null)
            return null;

        BuildingEntity buildingEntity = new BuildingEntity();
        buildingEntity.setVersion(buildingTo.getVersion());
        buildingEntity.setId(buildingTo.getId());
        buildingEntity.setDescription(buildingTo.getDescription());
        buildingEntity.setLocation(buildingTo.getLocation());
        buildingEntity.setFloorsNumber(buildingTo.getFloorsNumber());
        buildingEntity.setHasElevator(buildingTo.getHasElevator());
        buildingEntity.setApartmentsNumber(buildingTo.getApartmentsNumber());

        return buildingEntity;
    }

    public static List<BuildingTo> map2Tos(List<BuildingEntity> buildingEntities) {
        return buildingEntities.stream().map(BuildingMapper::toBuildingTo).collect(Collectors.toList());
    }

    public static List<BuildingEntity> map2Entities(List<BuildingTo> buildingTos) {
        return buildingTos.stream().map(BuildingMapper::toBuildingEntity).collect(Collectors.toList());
    }

}
