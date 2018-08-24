package capgemini.service;

import capgemini.dto.ApartmentTo;
import capgemini.dto.BuildingTo;
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
@SpringBootTest(properties = "spring.profiles.active=mysql")
public class BuildingServiceTest {

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private BuildingService buildingService;

    private ApartmentTo apartment;
    private BuildingTo building1;
    private BuildingTo building2;
    private BuildingTo building3;

    @Before
    public void init() {
        BuildingTo buildingTo1 = new BuildingTo.BuildingToBuilder()
                .withDescription("Test description")
                .withLocation("Test location")
                .withFloorsAmount(2)
                .withElevator(false)
                .withApartmentsAmount(12)
                .withApartmentIds(new ArrayList<>())
                .build();
        BuildingTo buildingWithoutApartment1 = buildingService.addNewBuilding(buildingTo1);

        ApartmentTo apartmentTo = new ApartmentTo.ApartmentToBuilder()
                .withArea(42.0)
                .withRoomsAmount(2)
                .withBalconiesAmount(1)
                .withFloorNumber(2)
                .withAddress("Test address")
                .withStatus("Available")
                .withBuildingId(buildingWithoutApartment1.getId())
                .withPrice(100000.0D)
                .build();
        apartment = apartmentService.addNewApartment(apartmentTo);

        ApartmentTo apartmentTo2 = new ApartmentTo.ApartmentToBuilder()
                .withArea(42.0)
                .withRoomsAmount(2)
                .withBalconiesAmount(1)
                .withFloorNumber(2)
                .withAddress("Test address")
                .withStatus("Available")
                .withBuildingId(buildingWithoutApartment1.getId())
                .withPrice(200000.0D)
                .build();
        apartmentService.addNewApartment(apartmentTo2);
        building1 = buildingService.updateBuilding(buildingService.findBuildingById(buildingWithoutApartment1.getId()));

        BuildingTo buildingTo2 = new BuildingTo.BuildingToBuilder()
                .withDescription("Test description")
                .withLocation("Test location")
                .withFloorsAmount(2)
                .withElevator(false)
                .withApartmentsAmount(12)
                .withApartmentIds(new ArrayList<>())
                .build();
        BuildingTo buildingWithoutApartment2 = buildingService.addNewBuilding(buildingTo2);

        ApartmentTo apartmentTo3 = new ApartmentTo.ApartmentToBuilder()
                .withArea(42.0)
                .withRoomsAmount(2)
                .withBalconiesAmount(1)
                .withFloorNumber(2)
                .withAddress("Test address")
                .withStatus("Available")
                .withBuildingId(buildingWithoutApartment2.getId())
                .withPrice(100000.0D)
                .build();
        apartmentService.addNewApartment(apartmentTo3);

        ApartmentTo apartmentTo4 = new ApartmentTo.ApartmentToBuilder()
                .withArea(42.0)
                .withRoomsAmount(2)
                .withBalconiesAmount(1)
                .withFloorNumber(2)
                .withAddress("Test address")
                .withStatus("Available")
                .withBuildingId(buildingWithoutApartment2.getId())
                .withPrice(200000.0D)
                .build();
        apartmentService.addNewApartment(apartmentTo4);
        building2 = buildingService.updateBuilding(buildingService.findBuildingById(buildingWithoutApartment2.getId()));

        BuildingTo buildingTo3 = new BuildingTo.BuildingToBuilder()
                .withDescription("Test description")
                .withLocation("Test location")
                .withFloorsAmount(2)
                .withElevator(false)
                .withApartmentsAmount(12)
                .withApartmentIds(new ArrayList<>())
                .build();
        BuildingTo buildingWithoutApartment3 = buildingService.addNewBuilding(buildingTo3);

        ApartmentTo apartmentTo5 = new ApartmentTo.ApartmentToBuilder()
                .withArea(42.0)
                .withRoomsAmount(2)
                .withBalconiesAmount(1)
                .withFloorNumber(2)
                .withAddress("Test address")
                .withStatus("Available")
                .withBuildingId(buildingWithoutApartment3.getId())
                .withPrice(100000.0D)
                .build();
        apartmentService.addNewApartment(apartmentTo5);

        ApartmentTo apartmentTo6 = new ApartmentTo.ApartmentToBuilder()
                .withArea(42.0)
                .withRoomsAmount(2)
                .withBalconiesAmount(1)
                .withFloorNumber(2)
                .withAddress("Test address")
                .withStatus("Booked")
                .withBuildingId(buildingWithoutApartment3.getId())
                .withPrice(200000.0D)
                .build();
        apartmentService.addNewApartment(apartmentTo6);
        building3 = buildingService.updateBuilding(buildingService.findBuildingById(buildingWithoutApartment3.getId()));
    }

    @Test
    @Transactional
    public void shouldUpdateBuilding() {
        //When
        ApartmentTo apartmentTo = new ApartmentTo.ApartmentToBuilder()
                .withArea(42.0)
                .withRoomsAmount(2)
                .withBalconiesAmount(1)
                .withFloorNumber(2)
                .withAddress("Test address")
                .withStatus("Bought")
                .withBuildingId(building1.getId())
                .build();
        apartment = apartmentService.addNewApartment(apartmentTo);

        BuildingTo buildingById = buildingService.findBuildingById(building1.getId());
        buildingById.setDescription("Updated description");
        BuildingTo updatedBuilding = buildingService.updateBuilding(buildingById);

        //Then
        assertEquals(apartment.getId(), updatedBuilding.getApartmentIds().get(2));
        assertEquals("Updated description", updatedBuilding.getDescription());
    }

    @Test
    @Transactional
    public void shouldAddNewBuilding() {
        //Given
        BuildingTo buildingTo = new BuildingTo.BuildingToBuilder()
                .withDescription("Test description")
                .withLocation("Test location")
                .withFloorsAmount(2)
                .withElevator(false)
                .withApartmentsAmount(12)
                .withApartmentIds(new ArrayList<>())
                .build();

        //When
        BuildingTo testBuilding = buildingService.addNewBuilding(buildingTo);
        BuildingTo buildingById = buildingService.findBuildingById(testBuilding.getId());

        //Then
        assertEquals(testBuilding.getId(), buildingById.getId());
    }

    @Test(expected = RuntimeException.class)
    @Transactional
    public void shouldDeleteBuildingWithApartment() {
        //When
        Long buildingId = building1.getId();
        Long apartmentId = building1.getApartmentIds().get(0);
        buildingService.deleteBuilding(building1);

        //Then
        buildingService.findBuildingById(buildingId);
        apartmentService.findApartmentById(apartmentId);
    }

    @Test
    @Transactional
    public void shouldFindBuildingById() {
        //When
        BuildingTo buildingById = buildingService.findBuildingById(building1.getId());

        //Then
        assertEquals(buildingById.getDescription(), building1.getDescription());
    }

    @Test(expected = OptimisticLockingFailureException.class)
    @Transactional
    public void shouldTestOptimisticLookingException() {
        //When
        building1.setDescription("Successful update");
        buildingService.updateBuilding(building1);
        building1.setDescription("Optimistic failure exception");
        buildingService.updateBuilding(building1);
    }

    @Test
    @Transactional
    public void shouldCalculateAvgApartmentPriceOfBuilding() {
        //When
        Double price = buildingService.calculateAvgApartmentPriceOfBuilding(building1);

        //Then
        assertEquals(Double.valueOf(150000.0D), price);
    }

    @Test
    @Transactional
    public void shouldCountApartmentsWithSpecifiedStatusInSpecifiedBuilding() {
        //When
        Long count = buildingService
                .countApartmentsWithSpecifiedStatusInSpecifiedBuilding("Available", building1);

        //Then
        assertEquals(Long.valueOf(2), count);
    }

    @Test
    @Transactional
    public void shouldFindBuildingWithLargestAmountOfAvailableApartments(){
        //When
        List<BuildingTo> buildings = buildingService.findBuildingWithLargestAmountOfAvailableApartments();

        //Then
        assertEquals(2, buildings.size());
    }
}
