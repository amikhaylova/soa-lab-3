package service.oriented.architecture.lab2_refactored.mapper;

import org.springframework.stereotype.Service;
import service.oriented.architecture.lab2_refactored.dto.MovieDTO;
import service.oriented.architecture.lab2_refactored.entity.Movie;
import service.oriented.architecture.lab2_refactored.entity.Person;
import service.oriented.architecture.lab2_refactored.exceptions.EntityIsNotValidException;
import service.oriented.architecture.lab2_refactored.repository.CoordinatesRepository;
import service.oriented.architecture.lab2_refactored.repository.PersonRepository;
import service.oriented.architecture.lab2_refactored.utils.FieldValidationUtil;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieMapper {
    private final CoordinatesMapper coordinatesMapper;
    private final PersonMapper personMapper;
    private final CoordinatesRepository coordinatesRepository;
    private final PersonRepository personRepository;

    public MovieMapper(CoordinatesMapper coordinatesMapper, PersonMapper personMapper, CoordinatesRepository coordinatesRepository, PersonRepository personRepository) {
        this.coordinatesMapper = coordinatesMapper;
        this.personMapper = personMapper;
        this.coordinatesRepository = coordinatesRepository;
        this.personRepository = personRepository;
    }

    public Movie mapMovieDTOToMovie(MovieDTO movieDTO) {
        Movie movie = new Movie();
        movie.setId(FieldValidationUtil.getIntegerFieldValue(movieDTO.getId()));
        movie.setCoordinates(coordinatesMapper.mapCoordinatesDTOToCoordinates(movieDTO.getCoordinates()));
        if (movie.getCoordinates().getId() == null) throw new EntityIsNotValidException("coordinates must not be null");
        if (movie.getCoordinates() != null && !coordinatesRepository.existsById(movie.getCoordinates().getId()))
            throw new EntityIsNotValidException("coordinates with id = " + movie.getCoordinates().getId() + " does not exist");
        movie.setDuration(FieldValidationUtil.getLongFieldValue(movieDTO.getDuration()));
        movie.setGenre(FieldValidationUtil.getMovieGenreValue(movieDTO.getGenre()));
        movie.setMpaaRating(FieldValidationUtil.getMpaaRatingValue(movieDTO.getMpaaRating()));
        movie.setName(FieldValidationUtil.getStringValue(movieDTO.getName()));
        movie.setOscarsCount(FieldValidationUtil.getLongFieldValue(movieDTO.getOscarsCount()));
        Person screenwriter;
        if (!movieDTO.getScreenWriter().getId().equals("")) {
            screenwriter = personRepository.findById(Integer.parseInt(movieDTO.getScreenWriter().getId())).get();
        } else {
            throw new EntityIsNotValidException("screenwriter must not be null");
        }
        movie.setScreenWriter(screenwriter);
        return movie;
    }

    public MovieDTO mapMovieToMovieDTO(Movie movie) {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(String.valueOf(movie.getId()));
        movieDTO.setName(movie.getName());
        movieDTO.setCoordinates(coordinatesMapper.mapCoordinatesToCoordinatesDTO(movie.getCoordinates()));
        movieDTO.setDuration(String.valueOf(movie.getDuration()));
        movieDTO.setCreationDate(String.valueOf(movie.getCreationDate()));
        movieDTO.setScreenWriter(personMapper.mapPersonToPersonDTO(movie.getScreenWriter()));
        movieDTO.setMpaaRating(String.valueOf(movie.getMpaaRating()));
        movieDTO.setGenre(String.valueOf(movie.getGenre()));
        movieDTO.setOscarsCount(String.valueOf(movie.getOscarsCount()));
        return movieDTO;
    }

    public List<MovieDTO> mapMovieListToMovieDTOList(List<Movie> movieList) {
        ArrayList<MovieDTO> movieDTOArrayList = new ArrayList<>();
        for (Movie movie : movieList) {
            movieDTOArrayList.add(mapMovieToMovieDTO(movie));
        }
        return movieDTOArrayList;
    }


}
