package capgemini.repository.impl;

import capgemini.entities.ApartmentEntity;
import capgemini.entities.BuildingEntity;
import capgemini.entities.QApartmentEntity;
import capgemini.entities.QBuildingEntity;
import capgemini.repository.CustomizedBuildingRepository;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CustomizedBuildingRepositoryImpl extends AbstractRepository<ApartmentEntity, Long> implements CustomizedBuildingRepository {

    @PersistenceContext
    private EntityManager em;

    /**
     * Method calculates the apartment's average price at the specified building.
     *
     * @param buildingId
     * @return price
     */
    @Override
    public Double calculateAvgApartmentPriceOfBuilding(Long buildingId) {
        TypedQuery<Double> query = entityManager.createQuery(
                "select avg(apar.price) from BuildingEntity b " +
                        "join b.apartments apar on b.id = :buildingId", Double.class);
        query.setParameter("buildingId", buildingId);
        return query.getSingleResult();
    }

    /**
     * Method counts apartments with the given status at the given building.
     *
     * @param status
     * @param buildingId
     * @return counting result
     */
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

    /**
     * Method searches for a building with the largest amount of apartments with the 'available' status.
     *
     * @return list with one (or more) building(s) with largest amount of available apartments.
     */
    @Override
    public List<BuildingEntity> findBuildingWithLargestAmountOfAvailableApartments() {
        JPAQuery<BuildingEntity> query1 = new JPAQuery<>(em);
        JPAQuery<BuildingEntity> query2 = new JPAQuery<>(em);
        QBuildingEntity building = QBuildingEntity.buildingEntity;
        QApartmentEntity apartment = QApartmentEntity.apartmentEntity;

        Long largestAmount = query1
                .select(building.id.count())
                .from(building)
                .join(building.apartments, apartment)
                .where(apartment.status.like("Available"))
                .groupBy(building.id)
                .orderBy(building.id.count().desc())
                .fetchFirst();

        if (largestAmount == null) {
            largestAmount = 0L;
        }

        List<BuildingEntity> buildingEntities = query2
                .select(building)
                .from(building)
                .join(building.apartments, apartment)
                .where(apartment.status.like("Available"))
                .groupBy(building.id)
                .having(building.id.count().eq(largestAmount))
                .fetch();

        return buildingEntities;
    }
}