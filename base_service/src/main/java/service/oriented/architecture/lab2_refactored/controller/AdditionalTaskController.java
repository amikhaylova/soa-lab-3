package service.oriented.architecture.lab2_refactored.controller;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import service.oriented.architecture.lab2_refactored.dto.CountDTO;
import service.oriented.architecture.lab2_refactored.dto.dtoList.PersonDTOList;
import service.oriented.architecture.lab2_refactored.entity.Person;
import service.oriented.architecture.lab2_refactored.enums.MovieGenre;
import service.oriented.architecture.lab2_refactored.mapper.PersonMapper;
import service.oriented.architecture.lab2_refactored.service.MovieService;
import service.oriented.architecture.lab2_refactored.service.PersonService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/additional", produces = MediaType.APPLICATION_XML_VALUE)
@Validated
public class AdditionalTaskController {

    private final MovieService movieService;
    private final PersonService personService;
    private final PersonMapper personMapper;

    public AdditionalTaskController(MovieService movieService,
                                    PersonService personService,
                                    PersonMapper personMapper) {
        this.movieService = movieService;
        this.personService = personService;

        this.personMapper = personMapper;
    }

    @GetMapping
    public CountDTO getCountWithGenreEqualsTo(@RequestParam(name = "genre") MovieGenre genre) {
        Long countResult = movieService.countMoviesWithGenreEqualTo(genre);
        CountDTO countDTO = new CountDTO();
        countDTO.setCount(countResult);
        return countDTO;
    }

    @GetMapping(params = {"length"})
    public CountDTO getCountWithDurationGreaterThan(@RequestParam(name = "length") Long duration) {
        Long countResult = movieService.countMoviesWithDurationGreaterThan(duration);
        CountDTO countDTO = new CountDTO();
        countDTO.setCount(countResult);
        return countDTO;
    }

    @GetMapping(params = {"screenwriter"})
    public PersonDTOList getPersonsListLessThan(@RequestParam(name = "screenwriter") Integer id) {
        List<Person> personList = personService.getPersonsLessThan(id);
        PersonDTOList dto = new PersonDTOList(new ArrayList<>());
        dto.setPersonsList(personMapper.mapPersonListToPersonDTOList(personList));
        return dto;
    }
}
