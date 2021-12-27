package com.soa.service;

import com.soa.enums.MovieGenre;

import javax.ejb.Remote;

@Remote
public interface OscarService {
    void reallocateOscars(MovieGenre fromGenre, MovieGenre toGenre);

    void increaseOscars(Long duration, Integer oscars);

    String ping();
}
