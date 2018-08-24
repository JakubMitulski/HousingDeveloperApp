package capgemini.service;

import capgemini.dto.BuildingTo;

public interface BuildingService {

    BuildingTo addNewBuilding(BuildingTo buildingTo);

    BuildingTo findBuildingById(Long id);

    BuildingTo findBuildingByLocation(String location);

    BuildingTo updateBuilding(BuildingTo buildingTo);

    void deleteBuilding(BuildingTo buildingTo);

    Double calculateAvgApartmentPriceOfBuilding(BuildingTo buildingTo);

    Long countApartmentsWithSpecifiedStatusInSpecifiedBuilding(String status, BuildingTo buildingTo);

}
