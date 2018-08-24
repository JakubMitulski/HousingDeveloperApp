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
        queryBuilder.append("select a from ApartmentEntity a where ");
        boolean canAppendQueryByAnd = false;

        if (criteriaApartmentTo.getMinArea() != null) {
            queryBuilder.append("a.area >= :minArea");
            canAppendQueryByAnd = true;
        }
        if (criteriaApartmentTo.getMaxArea() != null) {
            if (canAppendQueryByAnd) {
                queryBuilder.append(" and ");
            }
            queryBuilder.append("a.area <= :maxArea");
            canAppendQueryByAnd = true;
        }
        if (criteriaApartmentTo.getMinRoomsAmount() != null) {
            if (canAppendQueryByAnd) {
                queryBuilder.append(" and ");
            }
            queryBuilder.append("a.roomsAmount >= :minRoomsAmount");
            canAppendQueryByAnd = true;
        }
        if (criteriaApartmentTo.getMaxRoomsAmount() != null) {
            if (canAppendQueryByAnd) {
                queryBuilder.append(" and ");
            }
            queryBuilder.append("a.roomsAmount <= :maxRoomsAmount");
            canAppendQueryByAnd = true;
        }
        if (criteriaApartmentTo.getMinBalconiesAmount() != null) {
            if (canAppendQueryByAnd) {
                queryBuilder.append(" and ");
            }
            queryBuilder.append("a.balconiesAmount >= :minBalconiesAmount");
            canAppendQueryByAnd = true;
        }
        if (criteriaApartmentTo.getMaxBalconiesAmount() != null) {
            if (canAppendQueryByAnd) {
                queryBuilder.append(" and ");
            }
            queryBuilder.append("a.balconiesAmount <= :maxBalconiesAmount");
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
}