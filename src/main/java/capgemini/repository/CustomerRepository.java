package capgemini.repository;

import capgemini.entities.CustomerEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends CrudRepository<CustomerEntity, Long>, CustomizedCustomerRepository {

    @Query("select c from CustomerEntity c join c.apartments apar on apar.id = :apartmentId")
    List<CustomerEntity> findCustomersWhoHasApartment(@Param("apartmentId") Long apartmentId);

}
