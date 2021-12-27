package service.oriented.architecture.lab2_refactored.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import service.oriented.architecture.lab2_refactored.dto.dtoList.LocationDTOList;
import service.oriented.architecture.lab2_refactored.entity.Location;
import service.oriented.architecture.lab2_refactored.mapper.LocationMapper;
import service.oriented.architecture.lab2_refactored.service.LocationService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/locations", produces = MediaType.APPLICATION_XML_VALUE)
@Validated
@CrossOrigin
public class LocationController {

    private final LocationMapper locationMapper;
    private final LocationService locationService;

    public LocationController(LocationMapper locationMapper,
                              LocationService locationService) {
        this.locationService = locationService;
        this.locationMapper = locationMapper;
    }

    @GetMapping(value = "/{id}")
    public LocationDTOList getLocation(@PathVariable(name = "id") Integer id) {
        Location location = locationService.getLocationById(id);
        LocationDTOList dto = new LocationDTOList(new ArrayList<>());
        dto.getLocationsList().add(locationMapper.mapLocationToLocationDTO(location));
        return dto;
    }

    @GetMapping
    public LocationDTOList getLocations() {
        List<Location> locations = locationService.getLocation();
        LocationDTOList dto = new LocationDTOList(new ArrayList<>());
        dto.setLocationsList(locationMapper.mapLocationListToLocationDTOList(locations));
        return dto;
    }

    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity createLocation(@RequestBody LocationDTOList locationDTOList) {
        Location locationToPersist = locationMapper.mapLocationDTOToLocation(locationDTOList.getLocationsList().get(0));
        locationService.createLocation(locationToPersist);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping(consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity updateLocation(@RequestBody LocationDTOList locationDTOList) {
        Location locationToUpdate = locationMapper.mapLocationDTOToLocation(locationDTOList.getLocationsList().get(0));
        locationService.updateLocation(locationToUpdate);
        return new ResponseEntity(HttpStatus.OK);
    }

}
