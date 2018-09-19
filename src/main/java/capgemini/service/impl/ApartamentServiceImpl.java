package capgemini.service.impl;

import capgemini.dto.ApartmentTo;
import capgemini.dto.CriteriaApartmentTo;
import capgemini.entities.ApartmentEntity;
import capgemini.entities.BuildingEntity;
import capgemini.exception.ApartmentNotFoundException;
import capgemini.exception.BuildingNotFoundException;
import capgemini.mappers.ApartmentMapper;
import capgemini.repository.ApartmentRepository;
import capgemini.repository.BuildingRepository;
import capgemini.repository.CustomerRepository;
import capgemini.service.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ApartamentServiceImpl implements ApartmentService {

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public ApartmentTo addNewApartment(ApartmentTo apartmentTo) throws BuildingNotFoundException {
        if (apartmentTo.getBuildingId() == null || !buildingRepository.existsById(apartmentTo.getBuildingId())) {
            throw new BuildingNotFoundException();
        }
        ApartmentEntity apartmentEntity = ApartmentMapper.toApartmentEntity(apartmentTo);

        BuildingEntity buildingEntity = buildingRepository.findById(apartmentTo.getBuildingId()).get();
        apartmentEntity.setBuilding(buildingEntity);

        ApartmentEntity savedApartment = apartmentRepository.save(apartmentEntity);

        buildingEntity.getApartments().add(apartmentEntity);

        return ApartmentMapper.toApartmentTo(savedApartment);
    }

    @Override
    public ApartmentTo findApartmentById(Long id) throws ApartmentNotFoundException {
        ApartmentEntity apartmentEntity = apartmentRepository
                .findById(id).orElseThrow(ApartmentNotFoundException::new);
        return ApartmentMapper.toApartmentTo(apartmentEntity);
    }

    @Override
    public ApartmentTo findApartmentByAddress(String address) throws ApartmentNotFoundException {
        ApartmentEntity apartmentEntity = apartmentRepository.findByAddress(address);
        if (apartmentEntity == null) {
            throw new ApartmentNotFoundException();
        }
        return ApartmentMapper.toApartmentTo(apartmentEntity);
    }

    @Override
    public ApartmentTo updateApartment(ApartmentTo apartmentTo) throws ApartmentNotFoundException {
        if (apartmentTo.getId() == null || !apartmentRepository.existsById(apartmentTo.getId())) {
            throw new ApartmentNotFoundException();
        }

        ApartmentEntity apartmentEntity = ApartmentMapper.toApartmentEntity(apartmentTo);

        BuildingEntity buildingEntity = buildingRepository.findById(apartmentTo.getBuildingId()).get();
        apartmentEntity.setBuilding(buildingEntity);

        ApartmentEntity updatedApartment = apartmentRepository.save(apartmentEntity);
        return ApartmentMapper.toApartmentTo(updatedApartment);
    }

    @Override
    public void deleteApartment(ApartmentTo apartmentTo) throws ApartmentNotFoundException {
        if (apartmentTo.getId() == null || !apartmentRepository.existsById(apartmentTo.getId())) {
            throw new ApartmentNotFoundException();
        }
        ApartmentEntity apartmentEntity = ApartmentMapper.toApartmentEntity(apartmentTo);

        BuildingEntity buildingEntity = buildingRepository.findById(apartmentTo.getBuildingId()).get();
        buildingEntity.getApartments().remove(apartmentEntity);
        buildingRepository.save(buildingEntity);

        apartmentRepository.deleteById(apartmentTo.getId());
    }

    /**
     * Method for searching apartments by given criteria: min/max area, min/max rooms amount
     * and min/max balconies amount.
     *
     * @param criteriaApartmentTo is a storage object for criteria.
     * @return apartment DTO
     */
    @Override
    public List<ApartmentTo> findApartmentsByCriteria(CriteriaApartmentTo criteriaApartmentTo) {
        List<ApartmentEntity> apartmentEntities = apartmentRepository
                .findApartmentsByCriteria(criteriaApartmentTo);
        return ApartmentMapper.map2Tos(apartmentEntities);
    }

    /**
     * Method for searching apartments for people with disabilities, that means in a building
     * with elevator or at the ground floor.
     *
     * @return apartment DTO
     */
    @Override
    public List<ApartmentTo> findAllApartmentsInBuildingWithElevatorOrOnGroundFloor() {
        List<ApartmentEntity> apartmentEntities = apartmentRepository
                .findAllApartmentsInBuildingWithElevatorOrOnGroundFloor();
        return ApartmentMapper.map2Tos(apartmentEntities);
    }
}
