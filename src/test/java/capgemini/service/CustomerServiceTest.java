package capgemini.service;

import capgemini.dto.ApartmentTo;
import capgemini.dto.BuildingTo;
import capgemini.dto.CustomerTo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
public class CustomerServiceTest {

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private CustomerService customerService;

    private ApartmentTo apartment;
    private BuildingTo building;
    private CustomerTo customer;

    @Before
    public void init() {
        CustomerTo customerTo = new CustomerTo.CustomerToBuilder()
                .withFirstName("jan")
                .withLastName("kowalski")
                .withPhone(77777777L)
                .withApartmentIds(new ArrayList<>())
                .build();
        customer = customerService.addNewCustomer(customerTo);

        BuildingTo buildingTo = new BuildingTo.BuildingToBuilder()
                .withDescription("Test description")
                .withLocation("Test location")
                .withFloorsNumber(2)
                .withElevator(false)
                .withApartmentsNumber(12)
                .withApartmentIds(new ArrayList<>())
                .build();
        BuildingTo buildingWithoutApartment = buildingService.addNewBuilding(buildingTo);

        ApartmentTo apartmentTo = new ApartmentTo.ApartmentToBuilder()
                .withArea(42.0)
                .withRoomsNumber(2)
                .withBalconiesNumber(1)
                .withfloor(2)
                .withAddress("Test address")
                .withStatus("Bought")
                .withBuildingId(buildingWithoutApartment.getId())
                .build();
        apartment = apartmentService.addNewApartment(apartmentTo);
        building = buildingService.updateBuilding(buildingService.findBuildingById(buildingWithoutApartment.getId()));
    }

    @Test
    @Transactional
    public void shouldFindCustomerById() {
        //When
        CustomerTo customerById = customerService.findCustomerById(customer.getId());

        //Then
        assertEquals(customerById.getLastName(), customer.getLastName());
    }

    @Test
    @Transactional
    public void shouldUpdateCustomer() {
        //When
        customer.setFirstName("Updated name");
        CustomerTo updatedCustomer = customerService.updateCustomer(customer);

        //Then
        assertEquals("Updated name", updatedCustomer.getFirstName());
    }

    @Test
    @Transactional
    public void shouldAddNewCustomer() {
        //Given
        CustomerTo customerTo = new CustomerTo.CustomerToBuilder()
                .withFirstName("jan")
                .withLastName("nowak")
                .withPhone(77777777L)
                .withApartmentIds(new ArrayList<>())
                .build();

        //When
        customer = customerService.addNewCustomer(customerTo);
        CustomerTo customerById = customerService.findCustomerById(customer.getId());

        //Then
        assertEquals(customerTo.getLastName(), customer.getLastName());
        assertEquals(customer.getId(), customerById.getId());
    }

    @Test(expected = OptimisticLockingFailureException.class)
    @Transactional
    public void shouldTestOptimisticLookingException() {
        //When
        customer.setLastName("Successful update");
        customerService.updateCustomer(customer);
        customer.setLastName("Optimistic failure exception");
        customerService.updateCustomer(customer);
    }
}
