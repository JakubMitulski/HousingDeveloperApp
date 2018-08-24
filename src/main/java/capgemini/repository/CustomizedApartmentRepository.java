package capgemini.repository;

import capgemini.dto.CriteriaApartmentTo;
import capgemini.entities.ApartmentEntity;
import capgemini.entities.CustomerEntity;

import java.util.List;

public interface CustomizedApartmentRepository {

    List<ApartmentEntity> findApartmentsByCriteria(CriteriaApartmentTo criteriaApartmentTo);

}
