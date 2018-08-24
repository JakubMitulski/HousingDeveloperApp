package capgemini.repository;

import capgemini.entities.BuildingEntity;

public interface CustomizedBuildingRepository {

    Double calculateAvgApartmentPriceOfBuilding(Long buildingId);

    Long countApartmentsWithSpecifiedStatusInSpecifiedBuilding(String status, Long buildingId);

    BuildingEntity findBuildingWithLargestAmountOfAvailableApartments();

}
