package capgemini.service;

import capgemini.dto.ApartmentTo;
import capgemini.dto.BuildingTo;
import capgemini.dto.CriteriaApartmentTo;
import capgemini.exception.ApartmentNotFoundException;
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
    private BuildingService buildingService;

    private ApartmentTo apartment;
    private ApartmentTo criteriaTestApartment1;
    private ApartmentTo criteriaTestApartment2;
    private ApartmentTo criteriaTestApartment3;
    private BuildingTo building;

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

        ApartmentTo apartmentTo1 = new ApartmentTo.ApartmentToBuilder()
                .withArea(30.0)
                .withRoomsNumber(1)
                .withBalconiesNumber(0)
                .withfloor(2)
                .withAddress("Test address")
                .withStatus("Bought")
                .withBuildingId(buildingWithoutApartment.getId())
                .build();
        criteriaTestApartment1 = apartmentService.addNewApartment(apartmentTo1);

        ApartmentTo apartmentTo2 = new ApartmentTo.ApartmentToBuilder()
                .withArea(90.0)
                .withRoomsNumber(5)
                .withBalconiesNumber(4)
                .withfloor(2)
                .withAddress("Test address")
                .withStatus("Bought")
                .withBuildingId(buildingWithoutApartment.getId())
                .build();
        criteriaTestApartment2 = apartmentService.addNewApartment(apartmentTo2);

        ApartmentTo apartmentTo3 = new ApartmentTo.ApartmentToBuilder()
                .withArea(60.0)
                .withRoomsNumber(3)
                .withBalconiesNumber(2)
                .withfloor(2)
                .withAddress("Test address")
                .withStatus("Bought")
                .withBuildingId(buildingWithoutApartment.getId())
                .build();
        criteriaTestApartment3 = apartmentService.addNewApartment(apartmentTo3);

        building = buildingService.updateBuilding(buildingService.findBuildingById(buildingWithoutApartment.getId()));
    }

    @Test
    @Transactional
    public void shouldFindApartmentById() {
        //When
        ApartmentTo apartmentById = apartmentService.findApartmentById(apartment.getId());

        //Then
        assertEquals(apartment.getAddress(), apartmentById.getAddress());
    }

    @Test
    @Transactional
    public void shouldUpdateApartment() {
        //When
        apartment.setStatus("Booked");
        ApartmentTo updatedApartment = apartmentService.updateApartment(apartment);

        //Then
        assertEquals("Booked", updatedApartment.getStatus());
    }

    @Test(expected = ApartmentNotFoundException.class)
    @Transactional
    public void shouldDeleteApartment() {
        //When
        Long apartmentId = apartment.getId();
        Long buildingId = apartment.getBuildingId();
        apartmentService.deleteApartment(apartment);
        BuildingTo buildingById = buildingService.findBuildingById(buildingId);

        //Then
        apartmentService.findApartmentById(apartmentId);
    }

    @Test
    @Transactional
    public void shouldAddNewApartment() {
        //Given
        ApartmentTo testApartment = new ApartmentTo.ApartmentToBuilder()
                .withArea(42.0)
                .withRoomsNumber(2)
                .withBalconiesNumber(1)
                .withfloor(2)
                .withAddress("Test address")
                .withStatus("Bought")
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
    public void shouldTestOptimisticLookingException() {
        //When
        apartment.setStatus("Successful update");
        apartmentService.updateApartment(apartment);
        apartment.setStatus("Optimistic failure exception");
        apartmentService.updateApartment(apartment);
    }

    @Test
    @Transactional
    public void shouldFindApartmentsByAllParams() {
        //Given
        CriteriaApartmentTo criteriaApartmentTo = new CriteriaApartmentTo.CriteriaApartmentToBuilder()
                .withMinArea(40.0)
                .withMaxArea(80.0)
                .withMinRoomsNumber(2)
                .withMaxRoomsNumber(4)
                .withMinBalconiesNumber(1)
                .withMaxBalconiesNumber(3)
                .build();

        //When
        List<ApartmentTo> apartmentsByCriteria = apartmentService.findApartmentsByCriteria(criteriaApartmentTo);

        //Then
        assertEquals(apartmentsByCriteria.size(), 2);
    }

    @Test
    @Transactional
    public void shouldFindApartmentsByMinParams() {
        //Given
        CriteriaApartmentTo criteriaApartmentTo = new CriteriaApartmentTo.CriteriaApartmentToBuilder()
                .withMinArea(40.0)
                .withMinRoomsNumber(2)
                .withMinBalconiesNumber(1)
                .build();

        //When
        List<ApartmentTo> apartmentsByCriteria = apartmentService.findApartmentsByCriteria(criteriaApartmentTo);

        //Then
        assertEquals(apartmentsByCriteria.size(), 3);
    }

    @Test
    @Transactional
    public void shouldFindApartmentsByMaxRoomParam() {
        //Given
        CriteriaApartmentTo criteriaApartmentTo = new CriteriaApartmentTo.CriteriaApartmentToBuilder()
                .withMaxRoomsNumber(2)
                .build();

        //When
        List<ApartmentTo> apartmentsByCriteria = apartmentService.findApartmentsByCriteria(criteriaApartmentTo);

        //Then
        assertEquals(apartmentsByCriteria.size(), 2);
    }

}
