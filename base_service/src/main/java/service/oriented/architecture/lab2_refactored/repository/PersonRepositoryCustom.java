package service.oriented.architecture.lab2_refactored.repository;

import service.oriented.architecture.lab2_refactored.entity.Person;

import java.util.List;

public interface PersonRepositoryCustom {
    List<Person> getPersonsLessThan(Integer id);
}
