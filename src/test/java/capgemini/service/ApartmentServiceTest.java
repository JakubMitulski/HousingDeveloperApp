package capgemini.service;

import capgemini.dto.ApartmentTo;
import capgemini.dto.BuildingTo;
import capgemini.exception.ApartmentNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

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
        assertTrue(buildingById.getApartmentIds().isEmpty());
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
}
