package capgemini.service;

import capgemini.dto.ApartmentTo;
import capgemini.dto.BuildingTo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
public class BuildingServiceTest {

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private BuildingService buildingService;

    private ApartmentTo apartment;
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
        building = buildingService.updateBuilding(buildingService.findBuildingById(buildingWithoutApartment.getId()));
    }

    @Test
    @Transactional
    public void shouldUpdateBuilding() {
        //When
        ApartmentTo apartmentTo = new ApartmentTo.ApartmentToBuilder()
                .withArea(42.0)
                .withRoomsNumber(2)
                .withBalconiesNumber(1)
                .withfloor(2)
                .withAddress("Test address")
                .withStatus("Bought")
                .withBuildingId(building.getId())
                .build();
        apartment = apartmentService.addNewApartment(apartmentTo);

        BuildingTo buildingById = buildingService.findBuildingById(building.getId());
        buildingById.setDescription("Updated description");
        BuildingTo updatedBuilding = buildingService.updateBuilding(buildingById);

        //Then
        assertEquals(apartment.getId(), updatedBuilding.getApartmentIds().get(1));
        assertEquals("Updated description", updatedBuilding.getDescription());
    }

    @Test
    @Transactional
    public void shouldAddNewBuilding() {
        //Given
        BuildingTo buildingTo = new BuildingTo.BuildingToBuilder()
                .withDescription("Test description")
                .withLocation("Test location")
                .withFloorsNumber(2)
                .withElevator(false)
                .withApartmentsNumber(12)
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
        Long buildingId = building.getId();
        Long apartmentId = building.getApartmentIds().get(0);
        buildingService.deleteBuilding(building);

        //Then
        buildingService.findBuildingById(buildingId);
        apartmentService.findApartmentById(apartmentId);
    }

    @Test
    @Transactional
    public void shouldFindBuildingById() {
        //When
        BuildingTo buildingById = buildingService.findBuildingById(building.getId());

        //Then
        assertEquals(buildingById.getDescription(), building.getDescription());
    }
}
