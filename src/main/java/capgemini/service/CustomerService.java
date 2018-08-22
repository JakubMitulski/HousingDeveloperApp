package capgemini.service;

import capgemini.dto.CustomerTo;

public interface CustomerService {

    CustomerTo addNewCustomer(CustomerTo customerTo);

    CustomerTo findCustomerById(Long id);

    CustomerTo updateCustomer(CustomerTo customerTo);

}
