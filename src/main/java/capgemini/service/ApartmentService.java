package capgemini.service;

import capgemini.dto.ApartmentTo;
import capgemini.dto.CriteriaApartmentTo;
import capgemini.exception.ApartmentNotFoundException;
import capgemini.exception.BuildingNotFoundException;

import java.util.List;

public interface ApartmentService {

    ApartmentTo addNewApartment(ApartmentTo apartmentTo) throws BuildingNotFoundException;

    ApartmentTo findApartmentById(Long id) throws ApartmentNotFoundException;

    ApartmentTo findApartmentByAddress(String address) throws ApartmentNotFoundException;

    ApartmentTo updateApartment(ApartmentTo apartmentTo) throws ApartmentNotFoundException;

    void deleteApartment(ApartmentTo apartmentTo) throws ApartmentNotFoundException;

    List<ApartmentTo> findApartmentsByCriteria(CriteriaApartmentTo criteriaApartmentTo);

    List<ApartmentTo> findAllApartmentsInBuildingWithElevatorOrOnGroundFloor();

}
