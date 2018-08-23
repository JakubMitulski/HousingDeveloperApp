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
                .build();
        testApartment5 = apartmentService.addNewApartment(apartmentToTest5);

        building = buildingService.updateBuilding(buildingService.findBuildingById(buildingWithoutApartment.getId()));

        ArrayList<Long> apartmentIds = new ArrayList<>();
        apartmentIds.add(apartment.getId());

        CustomerTo customerTo1 = new CustomerTo.CustomerToBuilder()
                .withFirstName("jan")
                .withLastName("kowalski")
                .withPhone(77777777L)
                .withApartmentIds(apartmentIds)
                .build();
        customer = customerService.addNewCustomer(customerTo1);

        CustomerTo customerTo2 = new CustomerTo.CustomerToBuilder()
                .withFirstName("jan")
                .withLastName("kowalski")
                .withPhone(77777777L)
                .withApartmentIds(new ArrayList<>())
                .build();
        testCustomer1 = customerService.addNewCustomer(customerTo2);

        CustomerTo customerTo3 = new CustomerTo.CustomerToBuilder()
                .withFirstName("jan")
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
        assertEquals(customerWhoBought.getApartmentIds().get(1), testApartment1.getId());
    }

    @Test(expected = ToManyBookingsException.class)
    @Transactional
    public void shouldCustomerBookApartment() {
        //When
        CustomerTo customerAfterFirstSingleBooking = customerService.bookApartment(testApartment1, testCustomer1);
        CustomerTo customerAfterSecondSingleBooking = customerService.bookApartment(testApartment2, customerAfterFirstSingleBooking);
        CustomerTo customerAfterThirdSingleBooking = customerService.bookApartment(testApartment3, customerAfterSecondSingleBooking);
        CustomerTo anotherCustomer = customerService.bookApartment(testApartment3, testCustomer2);
        CustomerTo customerAfterFirstDoubleBooking = customerService.bookApartment(testApartment4, customerAfterThirdSingleBooking);

        ApartmentTo apartmentById = apartmentService.findApartmentById(testApartment1.getId());

        //Then
        assertEquals(apartmentById.getStatus(), "BOOKED");
        assertEquals(customerAfterFirstSingleBooking.getApartmentIds().get(0), testApartment1.getId());
        customerService.bookApartment(testApartment5, customerAfterFirstDoubleBooking);
    }

    @Test
    @Transactional
    public void shouldFindCustomersWhoBoughtMoreThanOneApartment() {
        //When
        List<CustomerTo> customers = customerService.findCustomersWhoBoughtMoreThanOneApartment();

        //Then
        assertEquals(1, customers.size());
    }
}
