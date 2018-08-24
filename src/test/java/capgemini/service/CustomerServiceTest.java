package capgemini.service;

import capgemini.dto.ApartmentTo;
import capgemini.dto.BuildingTo;
import capgemini.dto.CustomerTo;
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
    private ApartmentTo testApartment6;
    private BuildingTo building;
    private CustomerTo customer;
    private CustomerTo testCustomer1;
    private CustomerTo testCustomer2;

    @Before
    public void init() {
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
                .withPrice(100000.0D)
                .build();
        apartment = apartmentService.addNewApartment(apartmentTo);

        ApartmentTo apartmentToTest1 = new ApartmentTo.ApartmentToBuilder()
                .withArea(92.0)
                .withRoomsNumber(4)
                .withBalconiesNumber(3)
                .withfloor(7)
                .withAddress("Some address")
                .withStatus("Bought")
                .withBuildingId(buildingWithoutApartment.getId())
                .withPrice(200000.0D)
                .build();
        testApartment1 = apartmentService.addNewApartment(apartmentToTest1);

        ApartmentTo apartmentToTest2 = new ApartmentTo.ApartmentToBuilder()
                .withArea(92.0)
                .withRoomsNumber(4)
                .withBalconiesNumber(3)
                .withfloor(7)
                .withAddress("Some address")
                .withStatus("Some status")
                .withBuildingId(buildingWithoutApartment.getId())
                .withPrice(390000.0D)
                .build();
        testApartment2 = apartmentService.addNewApartment(apartmentToTest2);

        ApartmentTo apartmentToTest3 = new ApartmentTo.ApartmentToBuilder()
                .withArea(92.0)
                .withRoomsNumber(4)
                .withBalconiesNumber(3)
                .withfloor(7)
                .withAddress("Some address")
                .withStatus("Some status")
                .withBuildingId(buildingWithoutApartment.getId())
                .withPrice(205000.0D)
                .build();
        testApartment3 = apartmentService.addNewApartment(apartmentToTest3);

        ApartmentTo apartmentToTest4 = new ApartmentTo.ApartmentToBuilder()
                .withArea(92.0)
                .withRoomsNumber(4)
                .withBalconiesNumber(3)
                .withfloor(7)
                .withAddress("Some address")
                .withStatus("Some status")
                .withBuildingId(buildingWithoutApartment.getId())
                .withPrice(550000.0D)
                .build();
        testApartment4 = apartmentService.addNewApartment(apartmentToTest4);

        ApartmentTo apartmentToTest5 = new ApartmentTo.ApartmentToBuilder()
                .withArea(92.0)
                .withRoomsNumber(4)
                .withBalconiesNumber(3)
                .withfloor(7)
                .withAddress("Some address")
                .withStatus("Some status")
                .withBuildingId(buildingWithoutApartment.getId())
                .withPrice(170000.0D)
                .build();
        testApartment5 = apartmentService.addNewApartment(apartmentToTest5);

        ApartmentTo apartmentToTest6 = new ApartmentTo.ApartmentToBuilder()
                .withArea(92.0)
                .withRoomsNumber(4)
                .withBalconiesNumber(3)
                .withfloor(7)
                .withAddress("Some address")
                .withStatus("Bought")
                .withBuildingId(buildingWithoutApartment.getId())
                .withPrice(170000.0D)
                .build();
        testApartment6 = apartmentService.addNewApartment(apartmentToTest6);

        building = buildingService.updateBuilding(buildingService.findBuildingById(buildingWithoutApartment.getId()));

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

        //Then
        customerService.updateCustomer(customer);
    }

    @Test
    @Transactional
    public void shouldFindCustomersWhoHasApartment() {
        //When
        List<CustomerTo> customers = customerService.findCustomersWhoHasApartment(apartment);

        //Then
        assertEquals(1, customers.size());
    }

    @Test
    @Transactional
    public void shouldCustomerBuyApartment() {
        //When
        CustomerTo customerWhoBought = customerService.buyApartment(testApartment1, customer);
        ApartmentTo apartmentById = apartmentService.findApartmentById(testApartment1.getId());

        //Then
        assertEquals(apartmentById.getStatus(), "BOUGHT");
        assertEquals(3, customerWhoBought.getApartmentIds().size());
    }

    @Test(expected = ToManyBookingsException.class)
    @Transactional
    public void shouldCustomerBookApartment() {
        //When
        customerService.bookApartment(testApartment1, testCustomer1);
        customerService.bookApartment(testApartment2, customerService.findCustomerById(testCustomer1.getId()));
        CustomerTo testujemy = customerService.bookApartment(testApartment3, customerService.findCustomerById(testCustomer1.getId()));
        customerService.bookApartment(testApartment3, testCustomer2);
        customerService.bookApartment(testApartment4, customerService.findCustomerById(testCustomer1.getId()));

        ApartmentTo apartmentById = apartmentService.findApartmentById(testApartment1.getId());
        CustomerTo customerById = customerService.findCustomerById(testCustomer1.getId());

        //Then
        assertEquals(apartmentById.getStatus(), "BOOKED");
        assertEquals(customerById.getApartmentIds().get(0), testApartment1.getId());
        customerService.bookApartment(testApartment5, customerService.findCustomerById(testCustomer1.getId()));
    }

    @Test
    @Transactional
    public void shouldFindCustomersWhoBoughtMoreThanOneApartment() {
        //When
        List<CustomerTo> customers = customerService.findCustomersWhoBoughtMoreThanOneApartment();

        //Then
        assertEquals(1, customers.size());
    }

    @Test
    @Transactional
    public void shouldCalculateApartmentsTotalPriceBoughtBySpecifiedCustomer() {
        //When
        Double price = customerService.calculateApartmentsTotalPriceBoughtBySpecifiedCustomer(customer);

        //Then
        assertEquals(Double.valueOf(270000.0D), price);
    }
}
