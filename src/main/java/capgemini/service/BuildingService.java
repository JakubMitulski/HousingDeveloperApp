package capgemini.service;

import capgemini.dto.BuildingTo;
import capgemini.exception.BuildingNotFoundException;

import java.util.List;

public interface BuildingService {

    BuildingTo addNewBuilding(BuildingTo buildingTo);

    BuildingTo findBuildingById(Long id) throws BuildingNotFoundException;

    BuildingTo findBuildingByLocation(String location) throws BuildingNotFoundException;

    BuildingTo updateBuilding(BuildingTo buildingTo) throws BuildingNotFoundException;

    void deleteBuilding(BuildingTo buildingTo) throws BuildingNotFoundException;

    Double calculateAvgApartmentPriceOfBuilding(BuildingTo buildingTo) throws BuildingNotFoundException;

    Long countApartmentsWithSpecifiedStatusInSpecifiedBuilding(String status, BuildingTo buildingTo) throws BuildingNotFoundException;

    List<BuildingTo> findBuildingWithLargestAmountOfAvailableApartments();

}
