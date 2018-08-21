package capgemini.service.impl;

import capgemini.dto.ApartmentTo;
import capgemini.entities.ApartmentEntity;
import capgemini.entities.BuildingEntity;
import capgemini.exception.ApartmentNotFoundException;
import capgemini.mappers.ApartmentMapper;
import capgemini.repository.ApartmentRepository;
import capgemini.repository.BuildingRepository;
import capgemini.service.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApartamentServiceImpl implements ApartmentService {

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    @Override
    public void addNewApartment(ApartmentTo apartmentTo) {
        ApartmentEntity apartmentEntity = ApartmentMapper.toApartmentEntity(apartmentTo);

        BuildingEntity buildingEntity = buildingRepository.findById(apartmentTo.getBuildingId()).get();
        apartmentEntity.setBuilding(buildingEntity);

        apartmentRepository.save(apartmentEntity);
    }

    @Override
    public ApartmentTo findApartmentById(Long id) {
        ApartmentEntity apartmentEntity = apartmentRepository.findById(id).orElseThrow(ApartmentNotFoundException::new);
        return ApartmentMapper.toApartmentTo(apartmentEntity);
    }

    @Override
    public ApartmentTo findApartmentByAddress(String address) {
        ApartmentEntity apartmentEntity = apartmentRepository.findByAddress(address);
        if (apartmentEntity == null) {
            throw new ApartmentNotFoundException();
        }
        return ApartmentMapper.toApartmentTo(apartmentEntity);
    }

    @Override
    public void updateApartment(ApartmentTo apartmentTo) {
        if (apartmentTo.getId() == null || !apartmentRepository.existsById(apartmentTo.getId())) {
            throw new ApartmentNotFoundException();
        }
        apartmentRepository.save(ApartmentMapper.toApartmentEntity(apartmentTo));
    }

    @Override
    public void deleteApartment(ApartmentTo apartmentTo) {
        if (apartmentTo.getId() == null || !apartmentRepository.existsById(apartmentTo.getId())) {
            throw new ApartmentNotFoundException();
        }
        apartmentRepository.deleteById(apartmentTo.getId());
    }
}
