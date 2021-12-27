package service.oriented.architecture.lab2_refactored.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import service.oriented.architecture.lab2_refactored.entity.Movie;
import service.oriented.architecture.lab2_refactored.enums.MovieGenre;

import java.util.Optional;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Integer>, MovieRepositoryCustom {
    Optional<Movie> findById(Integer id);

    void deleteMovieById(Integer id);

    Long countAllByDurationGreaterThan(Long duration);

    Long countAllByGenreEquals(MovieGenre genre);

}
