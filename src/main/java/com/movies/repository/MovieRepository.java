package com.movies.repository;

import com.movies.entities.Movie;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class MovieRepository implements PanacheRepository<Movie> {

    public List<Movie> findByCountry(String country){
        return list("SELECT m FROM movie m WHERE m.country = ?1 ORDER BY id DESC", country);
    }
}
