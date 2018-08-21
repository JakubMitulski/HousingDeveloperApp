package capgemini.service.impl;

import capgemini.dto.BuildingTo;
import capgemini.entities.BuildingEntity;
import capgemini.exception.BuildingNotFoundException;
import capgemini.mappers.BuildingMapper;
import capgemini.repository.BuildingRepository;
import capgemini.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuildingServiceImpl implements BuildingService {

    @Autowired
    private BuildingRepository buildingRepository;

    @Override
    public void addNewBuilding(BuildingTo buildingTo) {
        buildingRepository.save(BuildingMapper.toBuildingEntity(buildingTo));
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
    public void updateBuilding(BuildingTo buildingTo) {
        if (buildingTo.getId() == null || !buildingRepository.existsById(buildingTo.getId())) {
            throw new BuildingNotFoundException();
        }
        buildingRepository.save(BuildingMapper.toBuildingEntity(buildingTo));
    }

    @Override
    public void deleteBuilding(BuildingTo buildingTo) {
        if (buildingTo.getId() == null || !buildingRepository.existsById(buildingTo.getId())) {
            throw new BuildingNotFoundException();
        }
        buildingRepository.deleteById(buildingTo.getId());
    }
}
