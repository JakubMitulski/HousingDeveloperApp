package capgemini.repository.impl;

import capgemini.dto.CriteriaApartmentTo;
import capgemini.entities.ApartmentEntity;
import capgemini.entities.CustomerEntity;
import capgemini.repository.CustomizedQueriesRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CustomizedQueriesRepositoryImpl extends AbstractRepository<ApartmentEntity, Long> implements CustomizedQueriesRepository {

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

    @Override
    public Double calculateApartmentsTotalPriceBoughtBySpecifiedCustomer(Long customerId) {
        TypedQuery<Double> query = entityManager.createQuery(
                "select sum(apar.price) from CustomerEntity c " +
                        "join c.apartments apar on c.id = :customerId " +
                        "where apar.status = :status", Double.class);
        query.setParameter("customerId", customerId);
        query.setParameter("status", "Bought");
        return query.getSingleResult();
    }

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
    public List<CustomerEntity> findCustomersWhoBoughtMoreThanOneApartment() {
        TypedQuery<CustomerEntity> query = entityManager.createQuery(
                "select c from CustomerEntity c " +
                        "join c.apartments apar " +
                        "where (select count(*) from apar where upper(apar.status) like 'BOUGHT') > 1 ",
                CustomerEntity.class);
        return query.getResultList();
    }
}