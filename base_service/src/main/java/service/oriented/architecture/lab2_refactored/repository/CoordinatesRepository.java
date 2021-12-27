package service.oriented.architecture.lab2_refactored.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import service.oriented.architecture.lab2_refactored.entity.Coordinates;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoordinatesRepository extends CrudRepository<Coordinates, Integer> {
    Optional<Coordinates> findById(Integer id);

    List<Coordinates> findAll();
}
