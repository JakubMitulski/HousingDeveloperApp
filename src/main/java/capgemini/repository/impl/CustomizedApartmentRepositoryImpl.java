package capgemini.repository.impl;

import capgemini.dto.CriteriaApartmentTo;
import capgemini.entities.ApartmentEntity;
import capgemini.repository.CustomizedApartmentRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CustomizedApartmentRepositoryImpl extends AbstractRepository<ApartmentEntity, Long> implements CustomizedApartmentRepository {

    @Override
    public List<ApartmentEntity> findApartmentsByCriteria(CriteriaApartmentTo criteriaApartmentTo) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("select a from ApartmentEntity a where a.status not like 'Bought' ");

        if (criteriaApartmentTo.getMinArea() != null) {
            queryBuilder.append("and a.area >= :minArea ");
        }
        if (criteriaApartmentTo.getMaxArea() != null) {
            queryBuilder.append("and a.area <= :maxArea ");
        }
        if (criteriaApartmentTo.getMinRoomsAmount() != null) {
            queryBuilder.append("and a.roomsAmount >= :minRoomsAmount ");
        }
        if (criteriaApartmentTo.getMaxRoomsAmount() != null) {
            queryBuilder.append("and a.roomsAmount <= :maxRoomsAmount ");
        }
        if (criteriaApartmentTo.getMinBalconiesAmount() != null) {
            queryBuilder.append("and a.balconiesAmount >= :minBalconiesAmount ");
        }
        if (criteriaApartmentTo.getMaxBalconiesAmount() != null) {
            queryBuilder.append("and a.balconiesAmount <= :maxBalconiesAmount");
        }

        TypedQuery<ApartmentEntity> query = entityManager.createQuery(queryBuilder.toString(), ApartmentEntity.class);

        if (criteriaApartmentTo.getMinArea() != null) {
            query.setParameter("minArea", criteriaApartmentTo.getMinArea());
        }
        if (criteriaApartmentTo.getMaxArea() != null) {
            query.setParameter("maxArea", criteriaApartmentTo.getMaxArea());
        }
        if (criteriaApartmentTo.getMinRoomsAmount() != null) {
            query.setParameter("minRoomsAmount", criteriaApartmentTo.getMinRoomsAmount());
        }
        if (criteriaApartmentTo.getMaxRoomsAmount() != null) {
            query.setParameter("maxRoomsAmount", criteriaApartmentTo.getMaxRoomsAmount());
        }
        if (criteriaApartmentTo.getMinBalconiesAmount() != null) {
            query.setParameter("minBalconiesAmount", criteriaApartmentTo.getMinBalconiesAmount());
        }
        if (criteriaApartmentTo.getMaxBalconiesAmount() != null) {
            query.setParameter("maxBalconiesAmount", criteriaApartmentTo.getMaxBalconiesAmount());
        }

        return query.getResultList();
    }

    @Override
    public List<ApartmentEntity> findAllApartmentsInBuildingWithElevatorOrOnGroundFloor() {
        TypedQuery<ApartmentEntity> query = entityManager.createQuery(
                "select a from ApartmentEntity a " +
                        "join a.building " +
                        "where a.building.hasElevator = 'true' " +
                        "or a.floorNumber = 0", ApartmentEntity.class);
        return query.getResultList();
    }
}