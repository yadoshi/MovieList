package com.movies.resources;

import com.movies.entities.Movie;
import com.movies.repository.MovieRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/movies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MovieResource {

    @Inject
    MovieRepository movieRepository;
    public static List<Movie> movies = new ArrayList<>();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(){
        var movieList = movieRepository.listAll();
        return Response.ok(movies).build();
    }

    @GET
    @Path("{id}")
    public Response getById(@PathParam("id") Long id){
        return movieRepository
                .findByIdOptional(id)
                .map(movie -> Response.ok(movies).build())
                .orElse(Response.status(NOT_FOUND).build());
    }

    @GET
    @Path("title/{title}")
    public Response getByTitle(@PathParam("title") String title){
        return movieRepository
                .find("title", title)
                .singleResultOptional()
                .map(movie -> Response.ok(movie).build())
                .orElse(Response.status(NOT_FOUND).build());
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/size")
    public Integer countMovies(){
        return movies.size();
    }

    @GET
    @Path("country/{country}")
    public Response getByCountry(@PathParam("country") String country){
        List<Movie> movies = movieRepository.findByCountry(country);
        return Response.ok(movies).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createMovie(Movie newMovie){
        movies.add(newMovie);
        return Response.ok(movies).build();
    }

    @PUT
    @Path("{id}/{title}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateMovie(
            @PathParam("id") Long id,
            @PathParam("title") String title){
        movies = movies.stream().map(movie -> {
            if (movie.getId().equals(id)){
                movie.setTitle(title);
            }
            return movie;
        }).collect(Collectors.toList());
        return Response.ok(movies).build();
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response deleteMovie(
            @PathParam("id") Long id){
        Optional<Movie> movieToDelete = movies.stream().filter(movie -> movie.getId().equals(id))
                .findFirst();
        boolean removed = false;
        if (movieToDelete.isPresent()){
            removed = movies.remove(movieToDelete.get());
        }
        if (removed){
            return Response.noContent().build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

}
