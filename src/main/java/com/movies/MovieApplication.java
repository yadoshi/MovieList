package com.movies;

import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@OpenAPIDefinition(
        info = @Info(
                title = "Movie APIs",
                description = "Movie Application",
                version = "1.0.0",
                license = @License(
                        name = "MIT",
                        url = "https://localhost:8080"
                )
        ),
        tags = {
                @Tag(name = "movies", description = "Movies")
        }
)
public class MovieApplication extends Application {
}