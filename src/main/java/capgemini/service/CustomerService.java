package capgemini.service;

import capgemini.dto.ApartmentTo;
import capgemini.dto.CustomerTo;
import capgemini.exception.ApartmentNotFoundException;
import capgemini.exception.CustomerNotFoundException;
import capgemini.exception.ToManyBookingsException;

import java.util.List;

public interface CustomerService {

    CustomerTo addNewCustomer(CustomerTo customerTo);

    CustomerTo findCustomerById(Long id) throws CustomerNotFoundException;

    CustomerTo updateCustomer(CustomerTo customerTo) throws CustomerNotFoundException;

    List<CustomerTo> findCustomersWhoHasApartment(ApartmentTo apartmentTo);

    CustomerTo buyApartment(ApartmentTo apartmentTo, CustomerTo customerTo) throws ApartmentNotFoundException, CustomerNotFoundException;

    CustomerTo bookApartment(ApartmentTo apartmentTo, CustomerTo customerTo) throws ApartmentNotFoundException, CustomerNotFoundException, ToManyBookingsException;

    List<CustomerTo> findCustomersWhoBoughtMoreThanOneApartment();

    Double calculateApartmentsTotalPriceBoughtBySpecifiedCustomer(CustomerTo customerTo) throws CustomerNotFoundException;

}
