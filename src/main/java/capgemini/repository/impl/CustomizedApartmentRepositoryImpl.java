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
        if (criteriaApartmentTo.getMinRoomsNumber() != null) {
            if (canAppendQueryByAnd) {
                queryBuilder.append(" and ");
            }
            queryBuilder.append("a.roomsNumber >= :minRoomsNumber");
            canAppendQueryByAnd = true;
        }
        if (criteriaApartmentTo.getMaxRoomsNumber() != null) {
            if (canAppendQueryByAnd) {
                queryBuilder.append(" and ");
            }
            queryBuilder.append("a.roomsNumber <= :maxRoomsNumber");
            canAppendQueryByAnd = true;
        }
        if (criteriaApartmentTo.getMinBalconiesNumber() != null) {
            if (canAppendQueryByAnd) {
                queryBuilder.append(" and ");
            }
            queryBuilder.append("a.balconiesNumber >= :minBalconiesNumber");
            canAppendQueryByAnd = true;
        }
        if (criteriaApartmentTo.getMaxBalconiesNumber() != null) {
            if (canAppendQueryByAnd) {
                queryBuilder.append(" and ");
            }
            queryBuilder.append("a.balconiesNumber <= :maxBalconiesNumber");
        }

        TypedQuery<ApartmentEntity> query = entityManager.createQuery(queryBuilder.toString(), ApartmentEntity.class);

        if (criteriaApartmentTo.getMinArea() != null) {
            query.setParameter("minArea", criteriaApartmentTo.getMinArea());
        }
        if (criteriaApartmentTo.getMaxArea() != null) {
            query.setParameter("maxArea", criteriaApartmentTo.getMaxArea());
        }
        if (criteriaApartmentTo.getMinRoomsNumber() != null) {
            query.setParameter("minRoomsNumber", criteriaApartmentTo.getMinRoomsNumber());
        }
        if (criteriaApartmentTo.getMaxRoomsNumber() != null) {
            query.setParameter("maxRoomsNumber", criteriaApartmentTo.getMaxRoomsNumber());
        }
        if (criteriaApartmentTo.getMinBalconiesNumber() != null) {
            query.setParameter("minBalconiesNumber", criteriaApartmentTo.getMinBalconiesNumber());
        }
        if (criteriaApartmentTo.getMaxBalconiesNumber() != null) {
            query.setParameter("maxBalconiesNumber", criteriaApartmentTo.getMaxBalconiesNumber());
        }

        return query.getResultList();
    }
}