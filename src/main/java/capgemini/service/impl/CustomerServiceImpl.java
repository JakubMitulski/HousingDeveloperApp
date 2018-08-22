package capgemini.service.impl;

import capgemini.dto.CustomerTo;
import capgemini.entities.ApartmentEntity;
import capgemini.entities.CustomerEntity;
import capgemini.exception.CustomerNotFoundException;
import capgemini.mappers.CustomerMapper;
import capgemini.repository.ApartmentRepository;
import capgemini.repository.CustomerRepository;
import capgemini.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Override
    public CustomerTo addNewCustomer(CustomerTo customerTo) {
        CustomerEntity customerEntity = customerRepository.save(CustomerMapper.toCustomerEntity(customerTo));
        return CustomerMapper.toCustomerTo(customerEntity);
    }

    @Override
    public CustomerTo findCustomerById(Long id) {
        CustomerEntity customerEntity = customerRepository.findById(id).orElseThrow(CustomerNotFoundException::new);
        return CustomerMapper.toCustomerTo(customerEntity);
    }

    @Override
    public CustomerTo updateCustomer(CustomerTo customerTo) {
        if (customerTo.getId() == null || !customerRepository.existsById(customerTo.getId())) {
            throw new CustomerNotFoundException();
        }
        CustomerEntity customerEntity = CustomerMapper.toCustomerEntity(customerTo);
        Collection<Long> apartmentIds = customerTo.getApartmentIds();

        List<ApartmentEntity> apartments = new ArrayList<>();
        for (Long id : apartmentIds) {
            ApartmentEntity apartmentEntity = apartmentRepository.findById(id).get();
            apartments.add(apartmentEntity);
        }

        customerEntity.setApartments(apartments);

        CustomerEntity savedCustomer = customerRepository.save(customerEntity);
        return CustomerMapper.toCustomerTo(customerEntity);
    }
}
