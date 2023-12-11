package com.movies.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Movie {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;
    private String director;
    private String country;
}
