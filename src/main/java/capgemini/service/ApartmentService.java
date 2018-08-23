package capgemini.service;

import capgemini.dto.ApartmentTo;
import capgemini.dto.BuildingTo;
import capgemini.dto.CriteriaApartmentTo;
import capgemini.dto.CustomerTo;

import java.util.List;

public interface ApartmentService {

    ApartmentTo addNewApartment(ApartmentTo apartmentTo);

    ApartmentTo findApartmentById(Long id);

    ApartmentTo findApartmentByAddress(String address);

    ApartmentTo updateApartment(ApartmentTo apartmentTo);

    void deleteApartment(ApartmentTo apartmentTo);

    List<ApartmentTo> findApartmentsByCriteria(CriteriaApartmentTo criteriaApartmentTo);

    Double calculateApartmentsTotalPriceBoughtBySpecifiedCustomer(CustomerTo customerTo);

    Double calculateAvgApartmentPriceOfBuilding(BuildingTo buildingTo);

    Long countApartmentsWithSpecifiedStatusInSpecifiedBuilding(String status, BuildingTo buildingTo);

}
