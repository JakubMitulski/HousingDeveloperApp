package capgemini.service.impl;

import capgemini.dto.BuildingTo;
import capgemini.entities.ApartmentEntity;
import capgemini.entities.BuildingEntity;
import capgemini.exception.BuildingNotFoundException;
import capgemini.mappers.BuildingMapper;
import capgemini.repository.ApartmentRepository;
import capgemini.repository.BuildingRepository;
import capgemini.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class BuildingServiceImpl implements BuildingService {

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Override
    public BuildingTo addNewBuilding(BuildingTo buildingTo) {
        BuildingEntity savedBuilding = buildingRepository.save(BuildingMapper.toBuildingEntity(buildingTo));
        return BuildingMapper.toBuildingTo(savedBuilding);
    }

    @Override
    public BuildingTo findBuildingById(Long id) {
        BuildingEntity buildingEntity = buildingRepository.findById(id).orElseThrow(BuildingNotFoundException::new);
        return BuildingMapper.toBuildingTo(buildingEntity);

    }

    @Override
    public BuildingTo findBuildingByLocation(String location) {
        BuildingEntity buildingEntity = buildingRepository.findByLocation(location);
        if (buildingEntity == null) {
            throw new BuildingNotFoundException();
        }
        return BuildingMapper.toBuildingTo(buildingEntity);
    }

    @Override
    public BuildingTo updateBuilding(BuildingTo buildingTo) {
        if (buildingTo.getId() == null || !buildingRepository.existsById(buildingTo.getId())) {
            throw new BuildingNotFoundException();
        }
        BuildingEntity buildingEntity = BuildingMapper.toBuildingEntity(buildingTo);
        Collection<Long> apartmentIds = buildingTo.getApartmentIds();

        List<ApartmentEntity> apartments = new ArrayList<>();
        for (Long id : apartmentIds) {
            ApartmentEntity apartmentEntity = apartmentRepository.findById(id).get();
            apartments.add(apartmentEntity);
        }

        buildingEntity.setApartments(apartments);

        BuildingEntity savedBuilding = buildingRepository.save(buildingEntity);
        return BuildingMapper.toBuildingTo(savedBuilding);
    }

    @Override
    public void deleteBuilding(BuildingTo buildingTo) {
        if (buildingTo.getId() == null || !buildingRepository.existsById(buildingTo.getId())) {
            throw new BuildingNotFoundException();
        }
        buildingRepository.deleteById(buildingTo.getId());
    }

    @Override
    public Double calculateAvgApartmentPriceOfBuilding(BuildingTo buildingTo) {
        return buildingRepository.calculateAvgApartmentPriceOfBuilding(buildingTo.getId());
    }

    @Override
    public Long countApartmentsWithSpecifiedStatusInSpecifiedBuilding(String status, BuildingTo buildingTo) {
        return buildingRepository
                .countApartmentsWithSpecifiedStatusInSpecifiedBuilding(status, buildingTo.getId());
    }

    @Override
    public List<BuildingTo> findBuildingWithLargestAmountOfAvailableApartments() {
        List<BuildingEntity> buildingEntities = buildingRepository
                .findBuildingWithLargestAmountOfAvailableApartments();
        return BuildingMapper.map2Tos(buildingEntities);
    }
}
