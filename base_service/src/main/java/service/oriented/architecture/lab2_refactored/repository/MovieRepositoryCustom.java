package service.oriented.architecture.lab2_refactored.repository;

import service.oriented.architecture.lab2_refactored.dto.PagedMovieList;

public interface MovieRepositoryCustom {
    PagedMovieList findAll(Integer perPage, Integer curPage, String sortBy, String filterBy);
}
