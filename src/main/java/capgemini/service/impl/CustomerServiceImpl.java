package capgemini.service.impl;

import capgemini.dto.ApartmentTo;
import capgemini.dto.CustomerTo;
import capgemini.entities.ApartmentEntity;
import capgemini.entities.BuildingEntity;
import capgemini.entities.CustomerEntity;
import capgemini.exception.ApartmentNotFoundException;
import capgemini.exception.CustomerNotFoundException;
import capgemini.exception.ToManyBookingsException;
import capgemini.mappers.CustomerMapper;
import capgemini.repository.ApartmentRepository;
import capgemini.repository.CustomerRepository;
import capgemini.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ApartmentRepository apartmentRepository;

    private final static String BOUGHT = "BOUGHT";
    private final static String BOOKED = "BOOKED";

    @Override
    public CustomerTo addNewCustomer(CustomerTo customerTo) {
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

    @Override
    public List<CustomerTo> findCustomersWhoHasApartment(ApartmentTo apartmentTo) {
        ApartmentEntity apartmentEntity = apartmentRepository.findByAddress(apartmentTo.getAddress());
        List<CustomerEntity> customers = customerRepository.findCustomersWhoHasApartment(apartmentEntity.getId());
        return CustomerMapper.map2Tos(customers);
    }

    @Override
    public CustomerTo buyApartment(ApartmentTo apartmentTo, CustomerTo customerTo) {
        ApartmentEntity apartmentEntity = apartmentRepository.findById(apartmentTo.getId())
                .orElseThrow(ApartmentNotFoundException::new);
        CustomerEntity customerEntity = customerRepository.findById(customerTo.getId())
                .orElseThrow(CustomerNotFoundException::new);

        apartmentEntity.setStatus(BOUGHT);
        ApartmentEntity updatedApartment = apartmentRepository.save(apartmentEntity);

        customerEntity.addApartmentToCustomer(updatedApartment);
        CustomerEntity updatedCustomer = customerRepository.save(customerEntity);

        return CustomerMapper.toCustomerTo(updatedCustomer);
    }

    @Override
    public CustomerTo bookApartment(ApartmentTo apartmentTo, CustomerTo customerTo) {
        ApartmentEntity apartmentEntity = apartmentRepository.findById(apartmentTo.getId())
                .orElseThrow(ApartmentNotFoundException::new);
        CustomerEntity customerEntity = customerRepository.findById(customerTo.getId())
                .orElseThrow(CustomerNotFoundException::new);

        validateEntryDataForBooking(customerEntity);

        apartmentEntity.setStatus(BOOKED);
        ApartmentEntity updatedApartment = apartmentRepository.save(apartmentEntity);

        customerEntity.addApartmentToCustomer(updatedApartment);
        CustomerEntity updatedCustomer = customerRepository.save(customerEntity);

        return CustomerMapper.toCustomerTo(updatedCustomer);
    }

    private void validateEntryDataForBooking(CustomerEntity customerEntity) {
        List<ApartmentEntity> bookedApartments = customerEntity
                .getApartments()
                .stream()
                .filter(apartment -> apartment
                        .getStatus()
                        .toUpperCase()
                        .equals("BOOKED"))
                .collect(Collectors.toList());

        List<ApartmentEntity> apartmentsBookedByOnlyOneCustomer = new ArrayList<>();

        for (ApartmentEntity apartment : bookedApartments) {
            if (customerRepository.findCustomersWhoHasApartment(apartment.getId()).size() < 2) {
                apartmentsBookedByOnlyOneCustomer.add(apartment);
            }
        }

        if (apartmentsBookedByOnlyOneCustomer.size() >= 3) {
            throw new ToManyBookingsException();
        }
    }

    @Override
    public List<CustomerTo> findCustomersWhoBoughtMoreThanOneApartment() {
        List<CustomerEntity> customerEntities = customerRepository
                .findCustomersWhoBoughtMoreThanOneApartment();
        return CustomerMapper.map2Tos(customerEntities);
    }

    @Override
    public Double calculateApartmentsTotalPriceBoughtBySpecifiedCustomer(CustomerTo customerTo) {
        return customerRepository
                .calculateApartmentsTotalPriceBoughtBySpecifiedCustomer(customerTo.getId());
    }
}
