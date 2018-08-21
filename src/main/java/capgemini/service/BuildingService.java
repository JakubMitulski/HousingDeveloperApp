package capgemini.service;

import capgemini.dto.BuildingTo;

public interface BuildingService {

    void addNewBuilding(BuildingTo buildingTo);

    BuildingTo findBuildingById(Long id);

    BuildingTo findBuildingByLocation(String location);

    void updateBuilding(BuildingTo buildingTo);

    void deleteBuilding(BuildingTo buildingTo);

}
