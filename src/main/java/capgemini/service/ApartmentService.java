package capgemini.service;

import capgemini.dto.ApartmentTo;

public interface ApartmentService {

    ApartmentTo addNewApartment(ApartmentTo apartmentTo);

    ApartmentTo findApartmentById(Long id);

    ApartmentTo findApartmentByAddress(String address);

    ApartmentTo updateApartment(ApartmentTo apartmentTo);

    void deleteApartment(ApartmentTo apartmentTo);

}
