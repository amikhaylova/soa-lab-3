package service.oriented.architecture.lab2_refactored.repository;

import service.oriented.architecture.lab2_refactored.dto.PagedMovieList;
import service.oriented.architecture.lab2_refactored.entity.Movie;
import service.oriented.architecture.lab2_refactored.enums.MovieGenre;
import service.oriented.architecture.lab2_refactored.enums.MpaaRating;
import service.oriented.architecture.lab2_refactored.exceptions.EntityIsNotValidException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MovieRepositoryImpl implements MovieRepositoryCustom {
    @PersistenceContext
    EntityManager em;


    @Override
    public PagedMovieList findAll(Integer perPage, Integer curPage, String sortBy, String filterBy) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Movie> criteriaQuery = criteriaBuilder.createQuery(Movie.class);
        Root<Movie> from = criteriaQuery.from(Movie.class);
        CriteriaQuery<Movie> select = criteriaQuery.select(from);

        List<Order> orderList = getOrderList(sortBy, criteriaBuilder, from);
        if (!orderList.isEmpty())
            criteriaQuery.orderBy(orderList);

        ArrayList<Predicate> predicates = getPredicatesList(filterBy, criteriaBuilder, from);
        if (!predicates.isEmpty()){
            select.where(predicates.toArray(new Predicate[]{}));
        }


        PagedMovieList pagedMovieList = new PagedMovieList();

        pagedMovieList.setCount(getOverallCount(criteriaBuilder, predicates));
        pagedMovieList.setMovieList(findAll(curPage, perPage, select));

        return pagedMovieList;
    }

    private List<Movie> findAll(CriteriaQuery<Movie> select) {
        TypedQuery<Movie> typedQuery = em.createQuery(select);
        return typedQuery.getResultList();
    }

    private List<Movie> findAll(Integer pageNumber, Integer pageSize, CriteriaQuery<Movie> select) {
        if (pageNumber != null && pageSize != null) {
            TypedQuery<Movie> typedQuery = em.createQuery(select);
            typedQuery.setFirstResult((pageNumber - 1) * pageSize);
            typedQuery.setMaxResults(pageSize);
            return typedQuery.getResultList();
        } else
            return findAll(select);
    }

    private Long getOverallCount(CriteriaBuilder criteriaBuilder, ArrayList<Predicate> predicates) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(Movie.class)));
        em.createQuery(countQuery);
        if (predicates.size() > 0)
            countQuery.where(predicates.toArray(new Predicate[]{}));
        return em.createQuery(countQuery).getSingleResult();

    }

    private ArrayList<Predicate> getPredicatesList(String filterBy, CriteriaBuilder criteriaBuilder, Root<Movie> from) {
        ArrayList<Predicate> predicates = new ArrayList<>();
        if (filterBy != null) {
            List<String> notParsedFilters = new ArrayList<>(Arrays.asList(filterBy.split(";")));
            for (String filterString : notParsedFilters) {
                List<String> filter = new ArrayList<>(Arrays.asList(filterString.split(",")));
                switch (filter.get(0)) {
                    case ("id"):
                        if (filter.size() < 3)
                            throw new EntityIsNotValidException("number of arguments less than required");
                        predicates.add(criteriaBuilder.greaterThanOrEqualTo(from.get("id"), Integer.parseInt(filter.get(1))));
                        predicates.add(criteriaBuilder.lessThanOrEqualTo(from.get("id"), Integer.parseInt(filter.get(2))));
                        break;
                    case ("name"):
                        if (filter.size() < 2)
                            throw new EntityIsNotValidException("number of arguments less than required");
                        predicates.add(criteriaBuilder.like(criteriaBuilder.upper(from.get("name")),
                                filter.get(1).toUpperCase() + "%"));
                        break;
                    case ("oscar"):
                        if (filter.size() < 3)
                            throw new EntityIsNotValidException("number of arguments less than required");
                        predicates.add(criteriaBuilder.greaterThanOrEqualTo(from.get("oscarsCount"), Long.parseLong(filter.get(1))));
                        predicates.add(criteriaBuilder.lessThanOrEqualTo(from.get("oscarsCount"), Long.parseLong(filter.get(2))));
                        break;
                    case ("duration"):
                        if (filter.size() < 3)
                            throw new EntityIsNotValidException("number of arguments less than required");
                        predicates.add(criteriaBuilder.greaterThanOrEqualTo(from.get("duration"), Long.parseLong(filter.get(1))));
                        predicates.add(criteriaBuilder.lessThanOrEqualTo(from.get("duration"), Long.parseLong(filter.get(2))));
                        break;
                    case ("genre"):
                        if (filter.size() < 2)
                            throw new EntityIsNotValidException("number of arguments less than required");
                        predicates.add(criteriaBuilder.equal(from.get("genre"), MovieGenre.valueOf(filter.get(1))));
                        break;
                    case ("rating"):
                        if (filter.size() < 2)
                            throw new EntityIsNotValidException("number of arguments less than required");
                        predicates.add(criteriaBuilder.equal(from.get("mpaaRating"), MpaaRating.valueOf(filter.get(1))));
                        break;
                    case ("date"):
                        if (filter.size() < 3)
                            throw new EntityIsNotValidException("number of arguments less than required");
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/y");
                        predicates.add(criteriaBuilder.greaterThanOrEqualTo(from.get("creationDate"), LocalDate.parse(filter.get(1), formatter)));
                        predicates.add(criteriaBuilder.lessThanOrEqualTo(from.get("creationDate"), LocalDate.parse(filter.get(2), formatter)));
                        break;
                    case ("coordinate"):
                        if (filter.size() < 5)
                            throw new EntityIsNotValidException("number of arguments less than required");
                        Predicate x = criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(from.get("coordinates").get("x"), Double.parseDouble(filter.get(1))),
                                criteriaBuilder.lessThanOrEqualTo(from.get("coordinates").get("x"), Double.parseDouble(filter.get(2))));
                        Predicate y = criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(from.get("coordinates").get("y"), Double.parseDouble(filter.get(3))),
                                criteriaBuilder.lessThanOrEqualTo(from.get("coordinates").get("y"), Double.parseDouble(filter.get(4))));
                        predicates.add(criteriaBuilder.and(x, y));
                        break;
                    case ("screenWriter"):
                        predicates.add(criteriaBuilder.like(criteriaBuilder.upper(from.get("screenWriter").get("name")),
                                filter.get(1).toUpperCase() + "%"));
                        break;
                }
            }
        }

        return predicates;
    }

    private List<Order> getOrderList(String sortBy, CriteriaBuilder criteriaBuilder, Root<Movie> from) {
        List<Order> orderList = new ArrayList();
        if (sortBy != null) {
            List<String> criteria = new ArrayList<>(Arrays.asList(sortBy.split(";")));
            for (String criterion : criteria) {
                switch (criterion) {
                    case ("id"):
                        orderList.add(criteriaBuilder.asc(from.get("id")));
                        break;
                    case ("name"):
                        orderList.add(criteriaBuilder.asc(from.get("name")));
                        break;
                    case ("duration"):
                        orderList.add(criteriaBuilder.asc(from.get("duration")));
                        break;
                    case ("oscar"):
                        orderList.add(criteriaBuilder.asc(from.get("oscarsCount")));
                        break;
                    case ("genre"):
                        orderList.add(criteriaBuilder.asc(from.get("genre")));
                        break;
                    case ("rating"):
                        orderList.add(criteriaBuilder.asc(from.get("mpaaRating")));
                        break;
                    case ("date"):
                        orderList.add(criteriaBuilder.asc(from.get("creationDate")));
                        break;
                    case ("coordinate"):
                        orderList.add(criteriaBuilder.asc(from.get("coordinates").get("x")));
                        break;
                    case ("screenWriter"):
                        orderList.add(criteriaBuilder.asc(from.get("screenWriter").get("name")));
                        break;
                }
            }
        }
        return orderList;
    }


}
