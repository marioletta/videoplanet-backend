package ee.mario.videoplanetbackend.controller;

import ee.mario.videoplanetbackend.entity.Movie;
import ee.mario.videoplanetbackend.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MovieController {

    @Autowired
    MovieRepository movieRepository;

    @GetMapping("movies")
    public ResponseEntity<List<Movie>> getMovies() {
        return ResponseEntity.ok().body(movieRepository.findAll());
    }

    @GetMapping("movies-available")
    public ResponseEntity<List<Movie>> getMoviesAvailableForRent( ) {
        return ResponseEntity.ok().body(movieRepository.findByRentedFalse());
    }

    @PostMapping("movies")
    public Movie addMovie(@RequestBody Movie movie) {
        if (movie.getId() != null) {
            throw new RuntimeException("Cannot add with ID!");
        }
        return movieRepository.save(movie);
    }

    @DeleteMapping("movies/{id}")
    public ResponseEntity<List<Movie>> deleteMovie(@PathVariable Long id) {
        movieRepository.deleteById(id);
        return ResponseEntity.ok().body(movieRepository.findAll());
    }

    @PutMapping("movie/edit")
    public ResponseEntity<Movie> editMovie(@RequestBody Movie movie) {
        if (movie.getId() == null) {
            throw new RuntimeException("Cannot edit without ID!");
        }
        movieRepository.save(movie);
        return ResponseEntity.ok().body(movieRepository.findById(movie.getId()).orElseThrow());
    }

    @PutMapping("movie/edit/type")
    public ResponseEntity<Movie> editMovieType(@RequestBody Movie movie) {
        if (movie.getId() == null) {
            throw new RuntimeException("Cannot edit without ID!");
        }
        if (movie.getType() == null) {
            throw new RuntimeException("Cannot edit without type!");
        }

        Movie existingMovie = movieRepository.findById(movie.getId()).orElseThrow(() -> new RuntimeException("Movie not found!"));
        existingMovie.setType(movie.getType());
        movieRepository.save(existingMovie);
        return ResponseEntity.ok().body(movieRepository.findById(movie.getId()).orElseThrow());
    }



}
