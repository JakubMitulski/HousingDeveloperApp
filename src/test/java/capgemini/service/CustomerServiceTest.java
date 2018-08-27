package capgemini.service;

import capgemini.dto.ApartmentTo;
import capgemini.dto.BuildingTo;
import capgemini.dto.CustomerTo;
import capgemini.exception.ApartmentNotFoundException;
import capgemini.exception.BuildingNotFoundException;
import capgemini.exception.CustomerNotFoundException;
import capgemini.exception.ToManyBookingsException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
    private ApartmentTo testApartment1;
    private ApartmentTo testApartment2;
    private ApartmentTo testApartment3;
    private ApartmentTo testApartment4;
    private ApartmentTo testApartment5;
    private CustomerTo customer;
    private CustomerTo testCustomer1;
    private CustomerTo testCustomer2;
    private final String booked = "Booked";
    private final String bought = "Bought";

    @Before
    public void init() throws BuildingNotFoundException {
        BuildingTo buildingTo = new BuildingTo.BuildingToBuilder()
                .withDescription("Test description")
                .withLocation("Test location")
                .withFloorsAmount(2)
                .withElevator(false)
                .withApartmentsAmount(12)
                .withApartmentIds(new ArrayList<>())
                .build();
        BuildingTo buildingWithoutApartment = buildingService.addNewBuilding(buildingTo);

        ApartmentTo apartmentTo = new ApartmentTo.ApartmentToBuilder()
                .withArea(42.0)
                .withRoomsAmount(2)
                .withBalconiesAmount(1)
                .withFloorNumber(2)
                .withAddress("Test address")
                .withStatus(bought)
                .withBuildingId(buildingWithoutApartment.getId())
                .withPrice(100000.0D)
                .build();
        apartment = apartmentService.addNewApartment(apartmentTo);

        ApartmentTo apartmentToTest1 = new ApartmentTo.ApartmentToBuilder()
                .withArea(92.0)
                .withRoomsAmount(4)
                .withBalconiesAmount(3)
                .withFloorNumber(7)
                .withAddress("Some address")
                .withStatus(bought)
                .withBuildingId(buildingWithoutApartment.getId())
                .withPrice(200000.0D)
                .build();
        testApartment1 = apartmentService.addNewApartment(apartmentToTest1);

        ApartmentTo apartmentToTest2 = new ApartmentTo.ApartmentToBuilder()
                .withArea(92.0)
                .withRoomsAmount(4)
                .withBalconiesAmount(3)
                .withFloorNumber(7)
                .withAddress("Some address")
                .withStatus("Some status")
                .withBuildingId(buildingWithoutApartment.getId())
                .withPrice(390000.0D)
                .build();
        testApartment2 = apartmentService.addNewApartment(apartmentToTest2);

        ApartmentTo apartmentToTest3 = new ApartmentTo.ApartmentToBuilder()
                .withArea(92.0)
                .withRoomsAmount(4)
                .withBalconiesAmount(3)
                .withFloorNumber(7)
                .withAddress("Some address")
                .withStatus("Some status")
                .withBuildingId(buildingWithoutApartment.getId())
                .withPrice(205000.0D)
                .build();
        testApartment3 = apartmentService.addNewApartment(apartmentToTest3);

        ApartmentTo apartmentToTest4 = new ApartmentTo.ApartmentToBuilder()
                .withArea(92.0)
                .withRoomsAmount(4)
                .withBalconiesAmount(3)
                .withFloorNumber(7)
                .withAddress("Some address")
                .withStatus("Some status")
                .withBuildingId(buildingWithoutApartment.getId())
                .withPrice(550000.0D)
                .build();
        testApartment4 = apartmentService.addNewApartment(apartmentToTest4);

        ApartmentTo apartmentToTest5 = new ApartmentTo.ApartmentToBuilder()
                .withArea(92.0)
                .withRoomsAmount(4)
                .withBalconiesAmount(3)
                .withFloorNumber(7)
                .withAddress("Some address")
                .withStatus("Some status")
                .withBuildingId(buildingWithoutApartment.getId())
                .withPrice(170000.0D)
                .build();
        testApartment5 = apartmentService.addNewApartment(apartmentToTest5);

        ApartmentTo apartmentToTest6 = new ApartmentTo.ApartmentToBuilder()
                .withArea(92.0)
                .withRoomsAmount(4)
                .withBalconiesAmount(3)
                .withFloorNumber(7)
                .withAddress("Some address")
                .withStatus(bought)
                .withBuildingId(buildingWithoutApartment.getId())
                .withPrice(170000.0D)
                .build();
        ApartmentTo testApartment6 = apartmentService.addNewApartment(apartmentToTest6);

        BuildingTo building = buildingService.updateBuilding(buildingService.findBuildingById(buildingWithoutApartment.getId()));

        ArrayList<Long> apartmentIds = new ArrayList<>();
        apartmentIds.add(apartment.getId());
        apartmentIds.add(testApartment6.getId());

        CustomerTo customerTo1 = new CustomerTo.CustomerToBuilder()
                .withFirstName("jan")
                .withLastName("kowalski")
                .withPhone(77777777L)
                .withApartmentIds(apartmentIds)
                .build();
        customer = customerService.addNewCustomer(customerTo1);

        CustomerTo customerTo2 = new CustomerTo.CustomerToBuilder()
                .withFirstName("tomasz")
                .withLastName("kowalski")
                .withPhone(77777777L)
                .withApartmentIds(new ArrayList<>())
                .build();
        testCustomer1 = customerService.addNewCustomer(customerTo2);

        CustomerTo customerTo3 = new CustomerTo.CustomerToBuilder()
                .withFirstName("adam")
                .withLastName("kowalski")
                .withPhone(77777777L)
                .withApartmentIds(new ArrayList<>())
                .build();
        testCustomer2 = customerService.addNewCustomer(customerTo3);
    }

    @Test
    @Transactional
    public void shouldFindCustomerById() throws CustomerNotFoundException {
        //When
        CustomerTo customerById = customerService.findCustomerById(customer.getId());

        //Then
        assertEquals(customerById.getLastName(), customer.getLastName());
    }

    @Test
    @Transactional
    public void shouldUpdateCustomer() throws CustomerNotFoundException {
        //Given
        String updatedName = "Updated name";

        //When
        customer.setFirstName(updatedName);
        CustomerTo updatedCustomer = customerService.updateCustomer(customer);

        //Then
        assertEquals(updatedName, updatedCustomer.getFirstName());
    }

    @Test
    @Transactional
    public void shouldAddNewCustomer() throws CustomerNotFoundException {
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
    public void shouldTestOptimisticLookingException() throws CustomerNotFoundException {
        //When
        customer.setLastName("Successful update");
        customerService.updateCustomer(customer);
        customer.setLastName("Optimistic failure exception");

        //Then
        customerService.updateCustomer(customer);
    }

    @Test
    @Transactional
    public void shouldFindCustomersWhoHasApartment() {
        //Given
        int resultListSize = 1;

        //When
        List<CustomerTo> customers = customerService.findCustomersWhoHasApartment(apartment);

        //Then
        assertEquals(resultListSize, customers.size());
    }

    @Test
    @Transactional
    public void shouldCustomerBuyApartment() throws CustomerNotFoundException, ApartmentNotFoundException {
        //Given
        int resultListSize = 3;

        //When
        CustomerTo customerWhoBought = customerService.buyApartment(testApartment1, customer);
        ApartmentTo apartmentById = apartmentService.findApartmentById(testApartment1.getId());

        //Then
        assertEquals(bought, apartmentById.getStatus());
        assertEquals(resultListSize, customerWhoBought.getApartmentIds().size());
    }

    @Test(expected = ToManyBookingsException.class)
    @Transactional
    public void shouldCustomerBookApartment() throws CustomerNotFoundException, ApartmentNotFoundException, ToManyBookingsException {
        //When
        customerService.bookApartment(testApartment1, testCustomer1);
        customerService.bookApartment(testApartment2, customerService.findCustomerById(testCustomer1.getId()));
        customerService.bookApartment(testApartment3, customerService.findCustomerById(testCustomer1.getId()));
        customerService.bookApartment(testApartment3, testCustomer2);
        customerService.bookApartment(testApartment4, customerService.findCustomerById(testCustomer1.getId()));

        ApartmentTo apartmentById = apartmentService.findApartmentById(testApartment1.getId());
        CustomerTo customerById = customerService.findCustomerById(testCustomer1.getId());

        //Then
        assertEquals(booked, apartmentById.getStatus());
        assertEquals(customerById.getApartmentIds().get(0), testApartment1.getId());
        customerService.bookApartment(testApartment5, customerService.findCustomerById(testCustomer1.getId()));
    }

    @Test
    @Transactional
    public void shouldFindCustomersWhoBoughtMoreThanOneApartment() {
        //Given
        int resultListSize = 1;

        //When
        List<CustomerTo> customers = customerService.findCustomersWhoBoughtMoreThanOneApartment();

        //Then
        assertEquals(resultListSize, customers.size());
    }

    @Test
    @Transactional
    public void shouldCalculateApartmentsTotalPriceBoughtBySpecifiedCustomer() throws CustomerNotFoundException {
        //Given
        Double resultTotalPrice = 270000.0D;

        //When
        Double price = customerService.calculateApartmentsTotalPriceBoughtBySpecifiedCustomer(customer);

        //Then
        assertEquals(resultTotalPrice, price);
    }
}
