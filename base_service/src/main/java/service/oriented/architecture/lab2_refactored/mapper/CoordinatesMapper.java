package service.oriented.architecture.lab2_refactored.mapper;

import org.springframework.stereotype.Service;
import service.oriented.architecture.lab2_refactored.dto.CoordinatesDTO;
import service.oriented.architecture.lab2_refactored.entity.Coordinates;
import service.oriented.architecture.lab2_refactored.utils.FieldValidationUtil;

import java.util.ArrayList;
import java.util.List;

@Service
public class CoordinatesMapper {

    public Coordinates mapCoordinatesDTOToCoordinates(CoordinatesDTO coordinatesDTO) {
        Coordinates coordinates = new Coordinates();
        coordinates.setId(FieldValidationUtil.getIntegerFieldValue(coordinatesDTO.getId()));
        coordinates.setX(FieldValidationUtil.getDoubleFieldValue(coordinatesDTO.getX()));
        coordinates.setY(FieldValidationUtil.getFloatFieldValue(coordinatesDTO.getY()));
        return coordinates;
    }

    public CoordinatesDTO mapCoordinatesToCoordinatesDTO(Coordinates coordinates) {
        CoordinatesDTO coordinatesDTO = new CoordinatesDTO();
        coordinatesDTO.setId(String.valueOf(coordinates.getId()));
        coordinatesDTO.setX(String.valueOf(coordinates.getX()));
        coordinatesDTO.setY(String.valueOf(coordinates.getY()));
        return coordinatesDTO;
    }

    public List<CoordinatesDTO> mapCoordinatesListToCoordinatesDTOList(List<Coordinates> coordinatesList) {
        ArrayList<CoordinatesDTO> coordinatesDTOList = new ArrayList<>();
        for (Coordinates coordinates : coordinatesList) {
            coordinatesDTOList.add(mapCoordinatesToCoordinatesDTO(coordinates));
        }
        return coordinatesDTOList;
    }
}
