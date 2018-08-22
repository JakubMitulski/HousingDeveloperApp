package capgemini.service;

import capgemini.dto.ApartmentTo;
import capgemini.dto.CustomerTo;

import java.util.List;

public interface CustomerService {

    CustomerTo addNewCustomer(CustomerTo customerTo);

    CustomerTo findCustomerById(Long id);

    CustomerTo updateCustomer(CustomerTo customerTo);

    List<CustomerTo> findCustomersWhoHasApartment(ApartmentTo apartmentTo);

    CustomerTo buyApartment(ApartmentTo apartmentTo, CustomerTo customerTo);

    CustomerTo bookApartment(ApartmentTo apartmentTo, CustomerTo customerTo);

}
