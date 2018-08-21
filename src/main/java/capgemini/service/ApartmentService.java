package capgemini.service;

import capgemini.dto.ApartmentTo;
import capgemini.entities.ApartmentEntity;

public interface ApartmentService {

    void addNewApartment(ApartmentTo apartmentTo);

    ApartmentEntity findApartmentById(Long id);

    void updateApartment(ApartmentTo apartmentTo);

    void deleteApartment(ApartmentTo apartmentTo);

}
