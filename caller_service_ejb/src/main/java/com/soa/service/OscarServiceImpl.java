package com.soa.service;

import com.soa.client.RestClient;
import com.soa.dto.MovieDTO;
import com.soa.enums.MovieGenre;

import javax.ejb.Stateless;
import java.util.List;


@Stateless
public class OscarServiceImpl implements OscarService {

    private RestClient restClient;

    public OscarServiceImpl() {
        restClient = new RestClient();
    }

    public String ping() {
        return "Hello!";
    }

    @Override
    public void reallocateOscars(MovieGenre fromGenre, MovieGenre toGenre) {
        List<MovieDTO> from = restClient.getXmlMoviesByGenre(fromGenre.name()).getMovieList();
        List<MovieDTO> to = restClient.getXmlMoviesByGenre(toGenre.name()).getMovieList();
        boolean[] ifUpdated = new boolean[to.size()];

        Long overAllFromOscars = 0L;

        for (MovieDTO dto : from) {
            overAllFromOscars = overAllFromOscars + dto.getOscarsCount();
            dto.setOscarsCount(0L);
            restClient.updateMovie(dto);
        }

        while (overAllFromOscars > 0) {
            for (int i = 0; i < to.size(); i++) {
                if (overAllFromOscars > 0) {
                    MovieDTO dto = to.get(i);
                    dto.setOscarsCount(dto.getOscarsCount() + 1);
                    ifUpdated[i] = true;
                    overAllFromOscars--;
                } else break;
            }
        }

        for (int i = 0; i < to.size(); i++) {
            MovieDTO dto = to.get(i);
            if (ifUpdated[i])
                restClient.updateMovie(dto);
        }
    }

    @Override
    public void increaseOscars(Long duration, Integer oscars) {
        List<MovieDTO> movies = restClient.getXmlMoviesByDurationGreaterThan(duration).getMovieList();
        for (MovieDTO dto : movies) {
            dto.setOscarsCount(dto.getOscarsCount() + oscars);
            restClient.updateMovie(dto);
        }
    }

}
