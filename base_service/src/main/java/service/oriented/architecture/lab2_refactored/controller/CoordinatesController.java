package service.oriented.architecture.lab2_refactored.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import service.oriented.architecture.lab2_refactored.dto.CoordinatesDTO;
import service.oriented.architecture.lab2_refactored.dto.dtoList.CoordinatesDTOList;
import service.oriented.architecture.lab2_refactored.entity.Coordinates;
import service.oriented.architecture.lab2_refactored.mapper.CoordinatesMapper;
import service.oriented.architecture.lab2_refactored.service.CoordinatesService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/coordinates", produces = MediaType.APPLICATION_XML_VALUE)
@Validated
@CrossOrigin
public class CoordinatesController {
    private final CoordinatesService coordinatesService;
    private final CoordinatesMapper coordinatesMapper;


    public CoordinatesController(CoordinatesMapper coordinatesMapper,
                                 CoordinatesService coordinatesService) {
        this.coordinatesService = coordinatesService;
        this.coordinatesMapper = coordinatesMapper;
    }

    @GetMapping
    public CoordinatesDTOList getCoordinates() {
        List<Coordinates> coordinates = coordinatesService.getCoordinates();
        CoordinatesDTOList dto = new CoordinatesDTOList(new ArrayList<>());
        dto.setCoordinatesList(coordinatesMapper.mapCoordinatesListToCoordinatesDTOList(coordinates));
        return dto;
    }

    @GetMapping(value = "/{id}")
    public CoordinatesDTOList getCoordinate(@PathVariable(name = "id") Integer id) {
        Coordinates coordinates = coordinatesService.getCoordinatesById(id);
        CoordinatesDTOList dto = new CoordinatesDTOList(new ArrayList<>());
        List<CoordinatesDTO> dtoList = new ArrayList<>();
        dtoList.add(coordinatesMapper.mapCoordinatesToCoordinatesDTO(coordinates));
        dto.setCoordinatesList(dtoList);
        return dto;
    }


    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity createCoordinate(@RequestBody CoordinatesDTOList coordinatesDTOList) {
        Coordinates coordinatesToPersist = coordinatesMapper.mapCoordinatesDTOToCoordinates(coordinatesDTOList.getCoordinatesList().get(0));
        coordinatesService.createCoordinates(coordinatesToPersist);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping(consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity updateCoordinate(@RequestBody CoordinatesDTOList coordinatesDTOList) {
        Coordinates coordinatesToUpdate = coordinatesMapper.mapCoordinatesDTOToCoordinates(coordinatesDTOList.getCoordinatesList().get(0));
        coordinatesService.updateCoordinates(coordinatesToUpdate);
        return new ResponseEntity(HttpStatus.OK);
    }
}
