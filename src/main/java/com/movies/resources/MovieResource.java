package com.movies.resources;

import com.movies.entities.Movie;
import com.movies.repository.MovieRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/movies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MovieResource {

    @Inject
    MovieRepository movieRepository;

    @GET
    public Response getAll(){
        List<Movie> movies = movieRepository.listAll();
        return Response.ok(movies).build();
    }

    @GET
    @Path("{id}")
    public Response getById(@PathParam("id") Long id){
        return movieRepository
                .findByIdOptional(id)
                .map(movie -> Response.ok(movie).build())
                .orElse(Response.status(NOT_FOUND).build());
    }
}
