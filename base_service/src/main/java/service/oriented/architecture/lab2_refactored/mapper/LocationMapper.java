package service.oriented.architecture.lab2_refactored.mapper;

import org.springframework.stereotype.Service;
import service.oriented.architecture.lab2_refactored.dto.LocationDTO;
import service.oriented.architecture.lab2_refactored.entity.Location;
import service.oriented.architecture.lab2_refactored.utils.FieldValidationUtil;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocationMapper {

    public Location mapLocationDTOToLocation(LocationDTO locationDTO) {
        Location location = new Location();
        location.setId(FieldValidationUtil.getIntegerFieldValue(locationDTO.getId()));
        location.setX(FieldValidationUtil.getDoubleFieldValue(locationDTO.getX()));
        location.setY(FieldValidationUtil.getIntegerFieldValue(locationDTO.getY()));
        location.setName(FieldValidationUtil.getStringValue(locationDTO.getName()));
        return location;
    }

    public LocationDTO mapLocationToLocationDTO(Location location) {
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setId(String.valueOf(location.getId()));
        locationDTO.setName(location.getName());
        locationDTO.setX(String.valueOf(location.getX()));
        locationDTO.setY(String.valueOf(location.getY()));
        return locationDTO;
    }

    public List<LocationDTO> mapLocationListToLocationDTOList(List<Location> locationList) {
        ArrayList<LocationDTO> locationDTOArrayList = new ArrayList<>();
        for (Location location : locationList) {
            locationDTOArrayList.add(mapLocationToLocationDTO(location));
        }
        return locationDTOArrayList;
    }
}
