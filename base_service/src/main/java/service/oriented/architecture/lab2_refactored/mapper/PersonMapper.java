package service.oriented.architecture.lab2_refactored.mapper;

import org.springframework.stereotype.Service;
import service.oriented.architecture.lab2_refactored.dto.PersonDTO;
import service.oriented.architecture.lab2_refactored.entity.Person;
import service.oriented.architecture.lab2_refactored.exceptions.EntityIsNotValidException;
import service.oriented.architecture.lab2_refactored.repository.LocationRepository;
import service.oriented.architecture.lab2_refactored.utils.FieldValidationUtil;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonMapper {

    private final LocationMapper locationMapper;
    private final LocationRepository locationRepository;

    public PersonMapper(LocationMapper locationMapper, LocationRepository locationRepository) {
        this.locationMapper = locationMapper;
        this.locationRepository = locationRepository;
    }


    public Person mapPersonDTOToPerson(PersonDTO personDTO) {
        Person person = new Person();
        person.setId(FieldValidationUtil.getIntegerFieldValue(personDTO.getId()));
        person.setEyeColor(FieldValidationUtil.getColorValue(personDTO.getEyeColor()));
        person.setName(FieldValidationUtil.getStringValue(personDTO.getName()));
        person.setWeight(FieldValidationUtil.getFloatFieldValue(personDTO.getWeight()));
        if (personDTO.getLocation().getId().equals(""))
            throw new EntityIsNotValidException("location must not be null");
        person.setLocation(locationMapper.mapLocationDTOToLocation(personDTO.getLocation()));
        if (person.getLocation().getId() != null) {
            if (!locationRepository.existsById(person.getLocation().getId()))
                throw new EntityIsNotValidException("location with id = " + person.getLocation().getId() + " does not exist");
        }

        return person;
    }

    public PersonDTO mapPersonToPersonDTO(Person person) {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(String.valueOf(person.getId()));
        personDTO.setName(person.getName());
        personDTO.setLocation(locationMapper.mapLocationToLocationDTO(person.getLocation()));
        personDTO.setWeight(String.valueOf(person.getWeight()));
        personDTO.setEyeColor(String.valueOf(person.getEyeColor()));
        return personDTO;
    }

    public List<PersonDTO> mapPersonListToPersonDTOList(List<Person> personList) {
        ArrayList<PersonDTO> personDTOArrayList = new ArrayList<>();
        for (Person person : personList) {
            personDTOArrayList.add(mapPersonToPersonDTO(person));
        }
        return personDTOArrayList;
    }
}
