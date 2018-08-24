package capgemini.repository.impl;

import capgemini.entities.ApartmentEntity;
import capgemini.entities.BuildingEntity;
import capgemini.repository.CustomizedBuildingRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;

@Repository
public class CustomizedBuildingRepositoryImpl extends AbstractRepository<ApartmentEntity, Long> implements CustomizedBuildingRepository {

    @Override
    public Double calculateAvgApartmentPriceOfBuilding(Long buildingId) {
        TypedQuery<Double> query = entityManager.createQuery(
                "select avg(apar.price) from BuildingEntity b " +
                        "join b.apartments apar on b.id = :buildingId", Double.class);
        query.setParameter("buildingId", buildingId);
        return query.getSingleResult();
    }

    @Override
    public Long countApartmentsWithSpecifiedStatusInSpecifiedBuilding(String status, Long buildingId) {
        TypedQuery<Long> query = entityManager.createQuery(
                "select count(*) from ApartmentEntity a " +
                        "where a.building.id = :buildingId " +
                        "and a.status = :status", Long.class);
        query.setParameter("buildingId", buildingId);
        query.setParameter("status", status);
        return query.getSingleResult();
    }

    @Override
    public BuildingEntity findBuildingWithLargestAmountOfAvailableApartments() {
        return null;
    }
}