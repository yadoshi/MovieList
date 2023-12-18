package com.movies.resources;

import com.movies.entities.Movie;
import com.movies.repository.MovieRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@QuarkusTest
public class MovieResourceTest {

    @InjectMock
    MovieRepository movieRepository;

    @Inject
    MovieResource movieResource;

    private Movie movie;

    @BeforeEach
    void setUp(){
        movie = new Movie();
        movie.setTitle("Shrek");
        movie.setDescription("ShrekDesc");
        movie.setCountry("Pantano");
        movie.setDirector("Gato de botas");
        movie.setId(1L);
    }

    @Test
    void getAll(){
        List<Movie> movies = new ArrayList<>();
        movies.add(movie);
        when(movieRepository.listAll()).thenReturn(movies);
        Response response = movieResource.getAll();
        List<Movie> entity = (List<Movie>) response.getEntity();

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        assertFalse(entity.isEmpty());
        assertEquals("Shrek", entity.get(0).getTitle());
        assertEquals(1L, entity.get(0).getId());
        assertEquals("Pantano", entity.get(0).getCountry());
        assertEquals("ShrekDesc", entity.get(0).getDescription());
        assertEquals("Gato de botas", entity.get(0).getDirector());
    }

    @Test
    void getById(){
        when(movieRepository.findByIdOptional(1L)).thenReturn(Optional.of(movie));

        Response response = movieResource.getById(1L);
        Movie entity = (Movie) response.getEntity();

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        assertEquals("Shrek", entity.getTitle());
        assertEquals(1L, entity.getId());
        assertEquals("Pantano", entity.getCountry());
        assertEquals("ShrekDesc", entity.getDescription());
        assertEquals("Gato de botas", entity.getDirector());
    }

    @Test
    void getByIdKO(){
        when(movieRepository.findByIdOptional(1L)).thenReturn(Optional.empty());
        Response response = movieResource.getById(1L);
        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        assertNull(response.getEntity());
    }

    @Test
    void getByTitle(){

        PanacheQuery<Movie> query = Mockito.mock(PanacheQuery.class);
        when(query.page(Mockito.any())).thenReturn(query);
        when(query.singleResultOptional()).thenReturn(Optional.of(movie));

        when(movieRepository.find("title", "Shrek")).thenReturn(query);

        Response response = movieResource.getByTitle("Shrek");

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        Movie entity = (Movie) response.getEntity();
        assertEquals("Shrek", entity.getTitle());
        assertEquals(1L, entity.getId());
        assertEquals("Pantano", entity.getCountry());
        assertEquals("ShrekDesc", entity.getDescription());
        assertEquals("Gato de botas", entity.getDirector());
    }

    @Test
    void getByTitleKO(){
        PanacheQuery<Movie> query = mock(PanacheQuery.class);
        when(query.page(Mockito.any())).thenReturn(query);
        when(query.singleResultOptional()).thenReturn(Optional.empty());

        when(movieRepository.find("title", "Shrek")).thenReturn(query);

        Response response = movieResource.getByTitle("Shrek");
        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        assertNull(response.getEntity());
    }
}
