package capgemini.repository;

import capgemini.dto.CriteriaApartmentTo;
import capgemini.entities.ApartmentEntity;
import capgemini.entities.CustomerEntity;

import java.util.List;

public interface CustomizedQueriesRepository {

    List<ApartmentEntity> findApartmentsByCriteria(CriteriaApartmentTo criteriaApartmentTo);

    Double calculateApartmentsTotalPriceBoughtBySpecifiedCustomer(Long customerId);

    Double calculateAvgApartmentPriceOfBuilding(Long buildingId);

    Long countApartmentsWithSpecifiedStatusInSpecifiedBuilding(String status, Long buildingId);

    List<CustomerEntity> findCustomersWhoBoughtMoreThanOneApartment();

}
