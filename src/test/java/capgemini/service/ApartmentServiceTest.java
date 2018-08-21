package capgemini.service;

import capgemini.dto.ApartmentTo;
import capgemini.dto.BuildingTo;
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
public class ApartmentServiceTest {

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private BuildingService buildingService;

    @Test
    @Transactional
    public void shouldFindApartmentById() {
        //Given
        BuildingTo buildingTo = new BuildingTo.BuildingToBuilder()
                .withDescription("Test description")
                .withLocation("Test location")
                .withFloorsNumber(2)
                .withElevator(false)
                .withApartmentsNumber(12)
                .withApartmentIds(new ArrayList<>())
                .build();
        buildingService.addNewBuilding(buildingTo);
        BuildingTo buildingByLocation = buildingService.findBuildingByLocation(buildingTo.getLocation());

        ApartmentTo apartmentTo = new ApartmentTo.ApartmentToBuilder()
                .withArea(42.0)
                .withRoomsNumber(2)
                .withBalconiesNumber(1)
                .withfloor(2)
                .withAddress("Test address")
                .withStatus("Bought")
                .withBuildingId(buildingByLocation.getId())
                .build();
        apartmentService.addNewApartment(apartmentTo);
        ApartmentTo apartmentByAddress = apartmentService.findApartmentByAddress(apartmentTo.getAddress());

        //When
        ApartmentTo apartmentById = apartmentService.findApartmentById(apartmentByAddress.getId());

        //Then
        assertEquals(apartmentTo.getAddress(), apartmentById.getAddress());
    }
}
