package capgemini.repository;

import capgemini.dto.CriteriaApartmentTo;
import capgemini.entities.ApartmentEntity;

import java.util.List;

public interface CustomizedApartmentRepository {

    List<ApartmentEntity> findApartmentsByCriteria(CriteriaApartmentTo criteriaApartmentTo);

}
