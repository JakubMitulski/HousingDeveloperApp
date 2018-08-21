package capgemini.service.impl;

import capgemini.dto.ApartmentTo;
import capgemini.entities.ApartmentEntity;
import capgemini.exception.ApartmentNotFoundException;
import capgemini.mappers.ApartmentMapper;
import capgemini.repository.ApartmentRepository;
import capgemini.service.ApartmentService;

public class ApartamentServiceImpl implements ApartmentService {

    private final ApartmentRepository apartmentRepository;

    public ApartamentServiceImpl(ApartmentRepository apartmentRepository) {
        this.apartmentRepository = apartmentRepository;
    }

    @Override
    public void addNewApartment(ApartmentTo apartmentTo) {
        apartmentRepository.save(ApartmentMapper.toApartmentEntity(apartmentTo));
    }

    @Override
    public ApartmentEntity findApartmentById(Long id) {
        return apartmentRepository.findById(id).orElseThrow(ApartmentNotFoundException::new);
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
