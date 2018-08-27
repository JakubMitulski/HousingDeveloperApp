package capgemini.repository.impl;

import capgemini.entities.ApartmentEntity;
import capgemini.entities.CustomerEntity;
import capgemini.repository.CustomizedCustomerRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CustomizedCustomerRepositoryImpl extends AbstractRepository<ApartmentEntity, Long> implements CustomizedCustomerRepository {

    /**
     * Method calculates a total price of the apartments purchased by the given customer.
     *
     * @param customerId
     * @return calculated price.
     */
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

    /**
     * Method returns a list of customers who has bought more than one apartment.
     *
     * @return result list of customers.
     */
    @Override
    public List<CustomerEntity> findCustomersWhoBoughtMoreThanOneApartment() {
        TypedQuery<CustomerEntity> query = entityManager.createQuery(
                "select c from CustomerEntity c " +
                        "join c.apartments apar " +
                        "where (select count(*) from apar where upper(apar.status) like 'BOUGHT') > 1 " +
                        "group by c.id",
                CustomerEntity.class);
        return query.getResultList();
    }
}