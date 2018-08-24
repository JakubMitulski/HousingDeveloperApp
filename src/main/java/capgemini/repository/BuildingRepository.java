package capgemini.repository;

import capgemini.entities.BuildingEntity;
import org.springframework.data.repository.CrudRepository;

public interface BuildingRepository extends CrudRepository<BuildingEntity, Long>, CustomizedBuildingRepository {

    BuildingEntity findByLocation(String location);

}
