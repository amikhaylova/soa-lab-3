package service.oriented.architecture.lab2_refactored.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import service.oriented.architecture.lab2_refactored.entity.Movie;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PagedMovieList {
    private List<Movie> movieList;
    private Long count;
}
