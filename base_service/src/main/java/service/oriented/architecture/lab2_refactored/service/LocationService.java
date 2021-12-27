package service.oriented.architecture.lab2_refactored.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.oriented.architecture.lab2_refactored.entity.Location;
import service.oriented.architecture.lab2_refactored.repository.LocationRepository;
import service.oriented.architecture.lab2_refactored.validation.EntityValidator;

import javax.persistence.NoResultException;
import java.util.List;

@Service
public class LocationService {
    private final LocationRepository locationRepository;
    private final EntityValidator entityValidator;

    public LocationService(LocationRepository locationRepository, EntityValidator entityValidator) {
        this.locationRepository = locationRepository;
        this.entityValidator = entityValidator;
    }

    public Location getLocationById(Integer id) {
        return locationRepository.findById(id).orElseThrow(NoResultException::new);
    }

    public List<Location> getLocation() {
        return locationRepository.findAll();
    }

    @Transactional
    public void updateLocation(Location locationToUpdate) {
        locationRepository.findById(locationToUpdate.getId())
                .orElseThrow(() -> new NoResultException(" Location with id " + locationToUpdate.getId() + " does not exist"));
        entityValidator.validateLocation(locationToUpdate);
        locationRepository.save(locationToUpdate);
    }

    @Transactional
    public void createLocation(Location locationToPersist) {
        entityValidator.validateLocation(locationToPersist);
        locationRepository.save(locationToPersist);
    }
}
