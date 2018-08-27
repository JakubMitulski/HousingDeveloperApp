package capgemini.service;

import capgemini.dto.ApartmentTo;
import capgemini.dto.BuildingTo;
import capgemini.dto.CriteriaApartmentTo;
import capgemini.dto.CustomerTo;
import capgemini.exception.ApartmentNotFoundException;
import capgemini.exception.BuildingNotFoundException;
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
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
public class ApartmentServiceTest {

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BuildingService buildingService;

    private ApartmentTo apartment;
    private BuildingTo building;
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
                .withStatus(booked)
                .withBuildingId(buildingWithoutApartment.getId())
                .withPrice(120000D)
                .build();
        apartment = apartmentService.addNewApartment(apartmentTo);

        ApartmentTo apartmentTo1 = new ApartmentTo.ApartmentToBuilder()
                .withArea(30.0)
                .withRoomsAmount(1)
                .withBalconiesAmount(0)
                .withFloorNumber(2)
                .withAddress("Test address")
                .withStatus(booked)
                .withBuildingId(buildingWithoutApartment.getId())
                .withPrice(100000D)
                .build();
        ApartmentTo criteriaTestApartment1 = apartmentService.addNewApartment(apartmentTo1);

        ApartmentTo apartmentTo2 = new ApartmentTo.ApartmentToBuilder()
                .withArea(90.0)
                .withRoomsAmount(5)
                .withBalconiesAmount(4)
                .withFloorNumber(2)
                .withAddress("Test address")
                .withStatus(booked)
                .withBuildingId(buildingWithoutApartment.getId())
                .withPrice(320000D)
                .build();
        ApartmentTo criteriaTestApartment2 = apartmentService.addNewApartment(apartmentTo2);

        ApartmentTo apartmentTo3 = new ApartmentTo.ApartmentToBuilder()
                .withArea(60.0)
                .withRoomsAmount(3)
                .withBalconiesAmount(2)
                .withFloorNumber(2)
                .withAddress("Test address")
                .withStatus(booked)
                .withBuildingId(buildingWithoutApartment.getId())
                .withPrice(320000D)
                .build();
        ApartmentTo criteriaTestApartment3 = apartmentService.addNewApartment(apartmentTo3);

        ApartmentTo apartmentTo4 = new ApartmentTo.ApartmentToBuilder()
                .withArea(10.0)
                .withRoomsAmount(1)
                .withBalconiesAmount(0)
                .withFloorNumber(0)
                .withAddress("Test address")
                .withStatus(booked)
                .withBuildingId(buildingWithoutApartment.getId())
                .withPrice(320000D)
                .build();
        ApartmentTo criteriaTestApartment4 = apartmentService.addNewApartment(apartmentTo4);

        building = buildingService.updateBuilding(buildingService.findBuildingById(buildingWithoutApartment.getId()));

        ArrayList<Long> apartmentIds = new ArrayList<>();
        apartmentIds.add(apartment.getId());
        apartmentIds.add(criteriaTestApartment1.getId());
        apartmentIds.add(criteriaTestApartment2.getId());
        apartmentIds.add(criteriaTestApartment3.getId());
        apartmentIds.add(criteriaTestApartment4.getId());

        CustomerTo customerTo1 = new CustomerTo.CustomerToBuilder()
                .withFirstName("jan")
                .withLastName("kowalski")
                .withPhone(77777777L)
                .withApartmentIds(apartmentIds)
                .build();
        CustomerTo customer = customerService.addNewCustomer(customerTo1);

        BuildingTo buildingTo2 = new BuildingTo.BuildingToBuilder()
                .withDescription("Test description")
                .withLocation("Test location")
                .withFloorsAmount(2)
                .withElevator(true)
                .withApartmentsAmount(12)
                .withApartmentIds(new ArrayList<>())
                .build();
        BuildingTo buildingWithoutApartment2 = buildingService.addNewBuilding(buildingTo2);

        ApartmentTo apartmentTo5 = new ApartmentTo.ApartmentToBuilder()
                .withArea(42.0)
                .withRoomsAmount(2)
                .withBalconiesAmount(1)
                .withFloorNumber(2)
                .withAddress("Test address")
                .withStatus(bought)
                .withBuildingId(buildingWithoutApartment2.getId())
                .withPrice(120000D)
                .build();
        apartmentService.addNewApartment(apartmentTo5);
    }

    @Test
    @Transactional
    public void shouldFindApartmentById() throws ApartmentNotFoundException {
        //When
        ApartmentTo apartmentById = apartmentService.findApartmentById(apartment.getId());

        //Then
        assertEquals(apartment.getAddress(), apartmentById.getAddress());
    }

    @Test(expected = ApartmentNotFoundException.class)
    @Transactional
    public void shouldThrowExceptionWhenCouldNotFindApartmentById() throws ApartmentNotFoundException {
        //When
        ApartmentTo apartmentById = apartmentService.findApartmentById(100003L);
    }

    @Test
    @Transactional
    public void shouldUpdateApartment() throws ApartmentNotFoundException {
        //When
        apartment.setStatus(booked);
        ApartmentTo updatedApartment = apartmentService.updateApartment(apartment);

        //Then
        assertEquals(booked, updatedApartment.getStatus());
    }

    @Test(expected = ApartmentNotFoundException.class)
    @Transactional
    public void shouldDeleteApartment() throws BuildingNotFoundException, ApartmentNotFoundException {
        //When
        Long apartmentId = apartment.getId();
        Long buildingId = apartment.getBuildingId();
        apartmentService.deleteApartment(apartment);

        //Then
        apartmentService.findApartmentById(apartmentId);
    }

    @Test
    @Transactional
    public void shouldAddNewApartment() throws BuildingNotFoundException, ApartmentNotFoundException {
        //Given
        ApartmentTo testApartment = new ApartmentTo.ApartmentToBuilder()
                .withArea(42.0)
                .withRoomsAmount(2)
                .withBalconiesAmount(1)
                .withFloorNumber(2)
                .withAddress("Test address")
                .withStatus(bought)
                .withBuildingId(building.getId())
                .build();

        //When
        ApartmentTo addedApartment = apartmentService.addNewApartment(testApartment);
        ApartmentTo apartmentById = apartmentService.findApartmentById(addedApartment.getId());
        BuildingTo buildingById = buildingService.findBuildingById(building.getId());

        //Then
        assertEquals(addedApartment.getAddress(), apartmentById.getAddress());
        assertTrue(buildingById.getApartmentIds().contains(apartmentById.getId()));
    }

    @Test(expected = OptimisticLockingFailureException.class)
    @Transactional
    public void shouldTestOptimisticLookingException() throws ApartmentNotFoundException {
        //When
        apartment.setStatus("Successful update");
        apartmentService.updateApartment(apartment);
        apartment.setStatus("Optimistic failure exception");
        apartmentService.updateApartment(apartment);
    }


    @Test(expected = OptimisticLockingFailureException.class)
    @Transactional
    public void shouldTestOptimisticLookingException2() throws ApartmentNotFoundException {
        //When
        ApartmentTo apartmentTo1 = apartmentService.findApartmentById(apartment.getId());
        ApartmentTo apartmentTo2 = apartmentService.findApartmentById(apartment.getId());
        apartmentTo1.setStatus("Successful update");
        apartmentService.updateApartment(apartmentTo1);
        apartmentTo2.setStatus("Optimistic failure exception");
        apartmentService.updateApartment(apartmentTo2);
    }

    @Test
    @Transactional
    public void shouldFindApartmentsByAllParams() {
        //Given
        CriteriaApartmentTo criteriaApartmentTo = new CriteriaApartmentTo.CriteriaApartmentToBuilder()
                .withMinArea(40.0)
                .withMaxArea(80.0)
                .withMinRoomsAmount(2)
                .withMaxRoomsAmount(4)
                .withMinBalconiesAmount(1)
                .withMaxBalconiesAmount(3)
                .build();
        int resultListSize = 2;

        //When
        List<ApartmentTo> apartmentsByCriteria = apartmentService.findApartmentsByCriteria(criteriaApartmentTo);

        //Then
        assertEquals(resultListSize, apartmentsByCriteria.size());
    }

    @Test
    @Transactional
    public void shouldFindApartmentsByNoParams() {
        //Given
        CriteriaApartmentTo criteriaApartmentTo = new CriteriaApartmentTo();
        int resultListSize = 5;

        //When
        List<ApartmentTo> apartmentsByCriteria = apartmentService.findApartmentsByCriteria(criteriaApartmentTo);

        //Then
        assertEquals(resultListSize, apartmentsByCriteria.size());
    }

    @Test
    @Transactional
    public void shouldFindApartmentsByMinParams() {
        //Given
        CriteriaApartmentTo criteriaApartmentTo = new CriteriaApartmentTo.CriteriaApartmentToBuilder()
                .withMinArea(40.0)
                .withMinRoomsAmount(2)
                .withMinBalconiesAmount(1)
                .build();
        int resultListSize = 3;

        //When
        List<ApartmentTo> apartmentsByCriteria = apartmentService.findApartmentsByCriteria(criteriaApartmentTo);

        //Then
        assertEquals(resultListSize, apartmentsByCriteria.size());
    }

    @Test
    @Transactional
    public void shouldFindApartmentsByMaxRoomParam() {
        //Given
        CriteriaApartmentTo criteriaApartmentTo = new CriteriaApartmentTo.CriteriaApartmentToBuilder()
                .withMaxRoomsAmount(2)
                .build();
        int resultListSize = 3;

        //When
        List<ApartmentTo> apartmentsByCriteria = apartmentService.findApartmentsByCriteria(criteriaApartmentTo);

        //Then
        assertEquals(resultListSize, apartmentsByCriteria.size());
    }

    @Test
    @Transactional
    public void shouldFindAllApartmentsInBuildingWithElevatorOrOnGroundFloor() {
        //Given
        int resultListSize = 2;

        //When
        List<ApartmentTo> apartments = apartmentService.findAllApartmentsInBuildingWithElevatorOrOnGroundFloor();

        //Then
        assertEquals(resultListSize, apartments.size());
    }
}
