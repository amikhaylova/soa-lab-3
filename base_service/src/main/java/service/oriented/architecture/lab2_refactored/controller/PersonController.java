package service.oriented.architecture.lab2_refactored.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import service.oriented.architecture.lab2_refactored.dto.PersonDTO;
import service.oriented.architecture.lab2_refactored.dto.dtoList.PersonDTOList;
import service.oriented.architecture.lab2_refactored.entity.Person;
import service.oriented.architecture.lab2_refactored.mapper.PersonMapper;
import service.oriented.architecture.lab2_refactored.service.PersonService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/persons", produces = MediaType.APPLICATION_XML_VALUE)
@Validated
@CrossOrigin
public class PersonController {

    private final PersonService personService;
    private final PersonMapper personMapper;

    public PersonController(PersonService personService,
                            PersonMapper personMapper) {
        this.personService = personService;
        this.personMapper = personMapper;
    }

    @GetMapping(value = "/{id}")
    public PersonDTOList getPerson(@PathVariable(name = "id") Integer id) {
        Person person = personService.getPersonById(id);
        PersonDTOList dto = new PersonDTOList(new ArrayList<>());
        List<PersonDTO> dtoList = dto.getPersonsList();
        dtoList.add(personMapper.mapPersonToPersonDTO(person));
        return dto;
    }

    @GetMapping
    public PersonDTOList getPersons() {
        List<Person> personList = personService.getPerson();
        PersonDTOList dto = new PersonDTOList(new ArrayList<>());
        dto.setPersonsList(personMapper.mapPersonListToPersonDTOList(personList));
        return dto;
    }


    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity createPerson(@RequestBody PersonDTOList personDTOList) {
        Person personToPersist = personMapper.mapPersonDTOToPerson(personDTOList.getPersonsList().get(0));
        personService.createPerson(personToPersist);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping(consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity updatePerson(@RequestBody PersonDTOList personDTOList) {
        Person personToUpdate = personMapper.mapPersonDTOToPerson(personDTOList.getPersonsList().get(0));
        personService.updatePerson(personToUpdate);
        return new ResponseEntity(HttpStatus.OK);
    }
}
