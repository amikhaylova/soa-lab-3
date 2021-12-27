package service.oriented.architecture.lab2_refactored.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.oriented.architecture.lab2_refactored.dto.PagedMovieList;
import service.oriented.architecture.lab2_refactored.entity.Movie;
import service.oriented.architecture.lab2_refactored.enums.MovieGenre;
import service.oriented.architecture.lab2_refactored.repository.MovieRepository;
import service.oriented.architecture.lab2_refactored.validation.EntityValidator;

import javax.persistence.NoResultException;
import java.time.LocalDate;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final EntityValidator entityValidator;

    public MovieService(MovieRepository movieRepository, EntityValidator entityValidator) {
        this.movieRepository = movieRepository;
        this.entityValidator = entityValidator;
    }

    public Movie getMovieById(Integer id) {
        return movieRepository.findById(id).orElseThrow(NoResultException::new);
    }

    public PagedMovieList getMovies(Integer perPage, Integer curPage, String sortBy, String filterBy) {
        return movieRepository.findAll(perPage, curPage, sortBy, filterBy);
    }

    @Transactional
    public void deleteMovie(Integer id) {
        movieRepository.deleteMovieById(id);
    }

    @Transactional
    public void updateMovie(Movie movieToUpdate) {
        Movie existingMovie = movieRepository.findById(movieToUpdate.getId())
                .orElseThrow(() -> new NoResultException("Movie with id " + movieToUpdate.getId() + " does not exist"));
        movieToUpdate.setCreationDate(existingMovie.getCreationDate());
        entityValidator.validateMovie(movieToUpdate);
        movieRepository.save(movieToUpdate);
    }

    @Transactional
    public void createMovie(Movie movieToPersist) {
        movieToPersist.setCreationDate(LocalDate.now());
        entityValidator.validateMovie(movieToPersist);
        movieRepository.save(movieToPersist);
    }

    public Long countMoviesWithGenreEqualTo(MovieGenre genre) {
        return movieRepository.countAllByGenreEquals(genre);
    }

    public Long countMoviesWithDurationGreaterThan(Long duration) {
        return movieRepository.countAllByDurationGreaterThan(duration);
    }
}
