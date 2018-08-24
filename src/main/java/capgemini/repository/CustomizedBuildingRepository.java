package capgemini.repository;

public interface CustomizedBuildingRepository {

    Double calculateAvgApartmentPriceOfBuilding(Long buildingId);

    Long countApartmentsWithSpecifiedStatusInSpecifiedBuilding(String status, Long buildingId);

}
