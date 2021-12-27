package service.oriented.architecture.lab2_refactored.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import service.oriented.architecture.lab2_refactored.dto.MovieDTO;
import service.oriented.architecture.lab2_refactored.dto.PagedMovieList;
import service.oriented.architecture.lab2_refactored.dto.dtoList.MovieDTOList;
import service.oriented.architecture.lab2_refactored.entity.Movie;
import service.oriented.architecture.lab2_refactored.mapper.MovieMapper;
import service.oriented.architecture.lab2_refactored.service.MovieService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/movies", produces = MediaType.APPLICATION_XML_VALUE)
@Validated
@CrossOrigin
public class MovieController {

    private final MovieService movieService;
    private final MovieMapper movieMapper;

    public MovieController(MovieService movieService,
                           MovieMapper movieMapper) {
        this.movieService = movieService;
        this.movieMapper = movieMapper;
    }

    @GetMapping
    public MovieDTOList getMovies(@RequestParam(name = "perPage", required = false) Integer perPage,
                                  @RequestParam(name = "curPage", required = false) Integer curPage,
                                  @RequestParam(name = "sortBy", required = false) String sortBy,
                                  @RequestParam(name = "filterBy", required = false) String filterBy) {
        System.out.println("FILTER BY" + filterBy);
        PagedMovieList pagedMovieList = movieService.getMovies(perPage, curPage, sortBy, filterBy);
        return new MovieDTOList((movieMapper.mapMovieListToMovieDTOList(pagedMovieList.getMovieList())), pagedMovieList.getCount());
    }

    @GetMapping(value = "/{id}")
    public MovieDTOList getMovie(@PathVariable(name = "id") Integer id) {
        Movie movie = movieService.getMovieById(id);
        MovieDTOList dto = new MovieDTOList(new ArrayList<>(), 1);
        List<MovieDTO> dtoList = dto.getMovieList();
        dtoList.add(movieMapper.mapMovieToMovieDTO(movie));
        return dto;
    }

    @PutMapping(consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity updateMovie(@RequestBody MovieDTOList movieDTOList) {
        System.out.println("UPDATE");
        Movie movieToUpdate = movieMapper.mapMovieDTOToMovie(movieDTOList.getMovieList().get(0));
        movieService.updateMovie(movieToUpdate);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createMovie(@RequestBody MovieDTOList movieDTOList) {
        Movie movieToPersist = movieMapper.mapMovieDTOToMovie(movieDTOList.getMovieList().get(0));
        movieService.createMovie(movieToPersist);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteMovie(@PathVariable(name = "id") Integer id) {
        movieService.deleteMovie(id);
        return new ResponseEntity(HttpStatus.OK);
    }


}
