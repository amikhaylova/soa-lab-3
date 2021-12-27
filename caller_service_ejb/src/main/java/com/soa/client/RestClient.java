package com.soa.client;

import com.soa.dto.MovieDTO;
import com.soa.dto.dtoList.MovieDTOList;
import org.glassfish.jersey.SslConfigurator;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

public class RestClient {
    private static final String REST_URI
            = "https://localhost:8081/movies";

    private SSLContext sslContext = SslConfigurator.newInstance()
            .trustStorePassword("callertruststorepwd")
            .keyPassword("base_service")
            .createSSLContext();

    private Client client = ClientBuilder.newBuilder()
            .sslContext(sslContext)
            .hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String s, SSLSession sslSession) {
                    return s.equals("localhost");
                }
            })
            .build();


    public MovieDTOList getXmlMoviesByGenre(String genre) {
        return client
                .target(REST_URI)
                .queryParam("filterBy", "genre," + genre)
                .request(MediaType.APPLICATION_XML)
                .get(MovieDTOList.class);
    }

    public MovieDTOList getXmlMoviesByDurationGreaterThan(Long duration) {
        return client
                .target(REST_URI)
                .queryParam("filterBy", "duration," + duration + "," + Long.MAX_VALUE)
                .request(MediaType.APPLICATION_XML)
                .get(MovieDTOList.class);
    }


    public Response updateMovie(MovieDTO movieDTO) {
        MovieDTOList movieDTOList = new MovieDTOList();
        movieDTOList.setMovieList(new ArrayList<MovieDTO>());
        movieDTOList.getMovieList().add(movieDTO);
        return client
                .target(REST_URI)
                .request(MediaType.APPLICATION_XML)
                .put(Entity.entity(movieDTOList, MediaType.APPLICATION_XML));
    }

}
