package capgemini.repository;

import capgemini.entities.CustomerEntity;

import java.util.List;

public interface CustomizedCustomerRepository {

    Double calculateApartmentsTotalPriceBoughtBySpecifiedCustomer(Long customerId);

    List<CustomerEntity> findCustomersWhoBoughtMoreThanOneApartment();

}
