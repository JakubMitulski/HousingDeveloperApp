package capgemini.service.impl;

import capgemini.dto.ApartmentTo;
import capgemini.dto.CustomerTo;
import capgemini.entities.ApartmentEntity;
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

    private final static String BOUGHT = "Bought";
    private final static String BOOKED = "Booked";

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
        return CustomerMapper.toCustomerTo(savedCustomer);
    }

    @Override
    public CustomerTo findCustomerById(Long id) throws CustomerNotFoundException {
        CustomerEntity customerEntity = customerRepository.findById(id).orElseThrow(CustomerNotFoundException::new);
        return CustomerMapper.toCustomerTo(customerEntity);
    }

    @Override
    public CustomerTo updateCustomer(CustomerTo customerTo) throws CustomerNotFoundException {
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
        return CustomerMapper.toCustomerTo(savedCustomer);
    }

    @Override
    public List<CustomerTo> findCustomersWhoHasApartment(ApartmentTo apartmentTo) {
        ApartmentEntity apartmentEntity = apartmentRepository.findByAddress(apartmentTo.getAddress());
        List<CustomerEntity> customers = customerRepository.findCustomersWhoHasApartment(apartmentEntity.getId());
        return CustomerMapper.map2Tos(customers);
    }

    /**
     * Method for buying specified apartment by specified customer.
     *
     * @param apartmentTo
     * @param customerTo
     * @return customer with updated apartments list.
     * @throws ApartmentNotFoundException
     * @throws CustomerNotFoundException
     */
    @Override
    public CustomerTo buyApartment(ApartmentTo apartmentTo, CustomerTo customerTo) throws ApartmentNotFoundException, CustomerNotFoundException {
        ApartmentEntity apartmentEntity = apartmentRepository.findById(apartmentTo.getId())
                .orElseThrow(ApartmentNotFoundException::new);
        CustomerEntity customerEntity = customerRepository.findById(customerTo.getId())
                .orElseThrow(CustomerNotFoundException::new);

        apartmentEntity.setStatus(BOUGHT);

        customerEntity.getApartments().add(apartmentEntity);
        CustomerEntity updatedCustomer = customerRepository.save(customerEntity);

        return CustomerMapper.toCustomerTo(updatedCustomer);
    }

    /**
     * Method for booking specified apartment by specified customer.
     *
     * @param apartmentTo
     * @param customerTo
     * @return customer with updated apartments list.
     * @throws ApartmentNotFoundException
     * @throws CustomerNotFoundException
     * @throws ToManyBookingsException
     */
    @Override
    public CustomerTo bookApartment(ApartmentTo apartmentTo, CustomerTo customerTo) throws ApartmentNotFoundException, CustomerNotFoundException, ToManyBookingsException {
        ApartmentEntity apartmentEntity = apartmentRepository.findById(apartmentTo.getId())
                .orElseThrow(ApartmentNotFoundException::new);
        CustomerEntity customerEntity = customerRepository.findById(customerTo.getId())
                .orElseThrow(CustomerNotFoundException::new);

        validateEntryDataForBooking(customerEntity);

        apartmentEntity.setStatus(BOOKED);

        customerEntity.getApartments().add(apartmentEntity);
        CustomerEntity updatedCustomer = customerRepository.save(customerEntity);

        return CustomerMapper.toCustomerTo(updatedCustomer);
    }

    /**
     * Auxiliary method for apartment booking validation. It does not allow to book more than three apartments
     * by a specified customer, unless customer has co-owner.
     *
     * @param customerEntity
     * @throws ToManyBookingsException
     */
    private void validateEntryDataForBooking(CustomerEntity customerEntity) throws ToManyBookingsException {
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

    /**
     * Method returns list of customers who has bought more than one apartment.
     *
     * @return result list of customers.
     */
    @Override
    public List<CustomerTo> findCustomersWhoBoughtMoreThanOneApartment() {
        List<CustomerEntity> customerEntities = customerRepository
                .findCustomersWhoBoughtMoreThanOneApartment();
        return CustomerMapper.map2Tos(customerEntities);
    }

    /**
     * Method calculates total price of the apartments purchased by the given customer.
     *
     * @param customerTo
     * @return calculated price.
     * @throws CustomerNotFoundException
     */
    @Override
    public Double calculateApartmentsTotalPriceBoughtBySpecifiedCustomer(CustomerTo customerTo) throws CustomerNotFoundException {
        if (customerTo.getId() == null || !customerRepository.existsById(customerTo.getId())) {
            throw new CustomerNotFoundException();
        }
        return customerRepository
                .calculateApartmentsTotalPriceBoughtBySpecifiedCustomer(customerTo.getId());
    }
}
