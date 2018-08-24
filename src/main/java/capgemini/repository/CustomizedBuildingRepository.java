package capgemini.repository;

import capgemini.entities.BuildingEntity;

import java.util.List;

public interface CustomizedBuildingRepository {

    Double calculateAvgApartmentPriceOfBuilding(Long buildingId);

    Long countApartmentsWithSpecifiedStatusInSpecifiedBuilding(String status, Long buildingId);

    List<BuildingEntity> findBuildingWithLargestAmountOfAvailableApartments();

}
