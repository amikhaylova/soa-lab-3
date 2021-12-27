package service.oriented.architecture.lab2_refactored.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.oriented.architecture.lab2_refactored.entity.Person;
import service.oriented.architecture.lab2_refactored.repository.PersonRepository;
import service.oriented.architecture.lab2_refactored.validation.EntityValidator;

import javax.persistence.NoResultException;
import java.util.List;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final EntityValidator entityValidator;

    public PersonService(PersonRepository personRepository, EntityValidator entityValidator) {
        this.personRepository = personRepository;
        this.entityValidator = entityValidator;
    }

    public Person getPersonById(Integer id) {
        return personRepository.findById(id).orElseThrow(NoResultException::new);
    }

    public List<Person> getPerson() {
        return personRepository.findAll();
    }

    @Transactional
    public void updatePerson(Person personToUpdate) {
        personRepository.findById(personToUpdate.getId())
                .orElseThrow(() -> new NoResultException(" Person with id " + personToUpdate.getId() + " does not exist"));
        entityValidator.validatePerson(personToUpdate);
        personRepository.save(personToUpdate);
    }

    @Transactional
    public void createPerson(Person personToPersist) {
        entityValidator.validatePerson(personToPersist);
        personRepository.save(personToPersist);
    }

    public List<Person> getPersonsLessThan(Integer id) {
        return personRepository.getPersonsLessThan(id);
    }
}
