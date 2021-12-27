package service.oriented.architecture.lab2_refactored.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import service.oriented.architecture.lab2_refactored.entity.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long>, PersonRepositoryCustom {
    Optional<Person> findById(Integer id);

    List<Person> findAll();
}
