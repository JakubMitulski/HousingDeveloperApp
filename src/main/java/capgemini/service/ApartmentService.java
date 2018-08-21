package capgemini.service;

import capgemini.dto.ApartmentTo;

public interface ApartmentService {

    void addNewApartment(ApartmentTo apartmentTo);

    ApartmentTo findApartmentById(Long id);

    ApartmentTo findApartmentByAddress(String address);

    void updateApartment(ApartmentTo apartmentTo);

    void deleteApartment(ApartmentTo apartmentTo);

}
