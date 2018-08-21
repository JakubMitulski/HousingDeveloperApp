package capgemini.repository;

import capgemini.entities.ApartmentEntity;
import org.springframework.data.repository.CrudRepository;

public interface ApartmentRepository extends CrudRepository<ApartmentEntity, Long> {

    ApartmentEntity findByAddress(String address);

}
