package se.experis.com.movieapp.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.experis.com.movieapp.models.Actor;
import se.experis.com.movieapp.models.CommonResponse;
import se.experis.com.movieapp.models.Movie;
import se.experis.com.movieapp.repositories.ActorRepository;
import se.experis.com.movieapp.repositories.MovieRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/movies")
public class MoviesController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ActorRepository actorRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse> getAllMovies() {
        var commonResponse = new CommonResponse();
        commonResponse.data = movieRepository.findAll();
        commonResponse.message = "All movies";

        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse> addMovie(@RequestBody Movie movie) {

        // System.out.println(movie.director.id);

        movie = movieRepository.save(movie);

        var commonResponse = new CommonResponse();
        commonResponse.data = movie;
        commonResponse.message = "added movie: " + movie.getId();


        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/actors", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse> addMovieWithActors(@RequestBody Movie movie) {

        movie = movieRepository.save(movie);

        var commonResponse = new CommonResponse();
        commonResponse.data = movie;
        commonResponse.message = "added movies, together with actors: " + movie.getId();

        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<CommonResponse> updateMovies(@RequestBody Movie newMovie, @PathVariable Integer id) {

        CommonResponse cr = new CommonResponse();
        HttpStatus resp;

        if (movieRepository.existsById(id)) {
            Optional<Movie> movieRepo = movieRepository.findById(id);

            Movie movie = movieRepo.get();

            movie.setTitle(newMovie.getTitle());
            movie.setImdb_rating(newMovie.getImdb_rating());
            if (newMovie.getDirector() != null) {
                movie.setDirector(newMovie.getDirector());
            }
            if (newMovie.getGenre() != null) {
                movie.setGenre(newMovie.getGenre());
            }

            if (newMovie.getActors() != null) {
                movie.setActors(new ArrayList<>());

                for (Actor actor : newMovie.getActors()) {
                    movie.getActors().add(actor);
                }
            }

            movieRepository.save(movie);

            cr.data = movie;
            cr.message = "Replaced movie with id: " + movie.getId();
            resp = HttpStatus.OK;
        } else {
            cr.message = "Movie not found with id: " + id;
            resp = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(cr, resp);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse> getMovie(@PathVariable Integer id) {
        //process
        CommonResponse cr = new CommonResponse();
        HttpStatus resp;


        if (movieRepository.existsById(id)) {
            Optional<Movie> movieRepo = movieRepository.findById(id);
            Movie movie = movieRepo.get();
            cr.data = movie;
            cr.message = "Movie with id: " + id;
            resp = HttpStatus.OK;
        } else {
            cr.data = null;
            cr.message = "Movie not found";
            resp = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(cr, resp);
    }

    @RequestMapping(value = "/{id}/actors", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse> getActorsByMovieID(@PathVariable Integer id) {
        CommonResponse cr = new CommonResponse();
        HttpStatus resp;

        if (movieRepository.existsById(id)) {
            Optional<Movie> movieRepo = movieRepository.findById(id);
            Movie movie = movieRepo.get();
            cr.data = movie.getActors();
            cr.message = "Actors in Movie with id: " + id;
            resp = HttpStatus.OK;
        } else {
            cr.data = null;
            cr.message = "Movie not found";
            resp = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(cr, resp);
    }

    @RequestMapping(value = "/{movieID}/actors/{actorID}", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse> getActorsByMovieID(@PathVariable Integer actorID, @PathVariable Integer movieID) {
        //process
        CommonResponse cr = new CommonResponse();
        HttpStatus resp;

        if (movieRepository.existsById(movieID)) {
            Optional<Movie> movieRepo = movieRepository.findById(movieID);
            Movie movie = movieRepo.get();

            if (actorRepository.existsById(actorID)) {

                Optional<Actor> actorRepo = actorRepository.findById(actorID);
                Actor actor = actorRepo.get();

                movie.getActors().add(actor);

                movieRepository.save(movie);

                cr.data = actor;
                cr.message = "Actor with id: " + actorID + " added to movie with id " + movieID;
                resp = HttpStatus.OK;

            } else {

                cr.data = null;
                cr.message = "Actor with id " + actorID + "not found";
                resp = HttpStatus.NOT_FOUND;
            }


        } else {
            cr.data = null;
            cr.message = "Movie with id " + movieID + "not found";
            resp = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(cr, resp);
    }

    /**
     * We check first if the movie row exists, and then the actor.
     * Thereafter, we remove the the actor attached to the movie.
     * The actor will exists in the actor table.
     * If we want to delete an actor linked to particular movie,
     * we simply have to set cascade = CascadeType.ALL. That will
     * delete the actor also, when deleting the movie.
     */

    @RequestMapping(value = "/{movieID}/actors/{actorID}", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse> deleteActorInMovie(@PathVariable Integer movieID, @PathVariable Integer actorID) {
        //process
        CommonResponse cr = new CommonResponse();
        HttpStatus resp;

        if (movieRepository.existsById(movieID)) {
            Optional<Movie> movieRepo = movieRepository.findById(movieID);
            Movie movie = movieRepo.get();

            if (actorRepository.existsById(actorID)) {
                for (int i = 0; i < movie.getActors().size(); i++) {
                    if (movie.getActors().get(i).getId() == actorID) {
                        movie.getActors().remove(movie.getActors().get(i));
                    }
                }

                movieRepository.save(movie);

                cr.data = movie;
                cr.message = "Actor with id: " + actorID + " deleted from movie with id " + movieID;
                resp = HttpStatus.OK;
            } else {
                cr.message = "Actor not found with id: " + actorID;
                resp = HttpStatus.NOT_FOUND;
            }
        } else {
            cr.message = "Movie not found with id: " + movieID;
            resp = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(cr, resp);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse> deleteMovie(@PathVariable Integer id) {
        CommonResponse cr = new CommonResponse();
        HttpStatus resp;

        if (movieRepository.existsById(id)) {
            movieRepository.deleteById(id);
            cr.message = "Deleted movie with id: " + id;
            resp = HttpStatus.OK;
        } else {
            cr.message = "Movie not found with id: " + id;
            resp = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(cr, resp);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse> getMovieBySearch(@RequestParam String title) {

        String searchTitle = title.toUpperCase();
        CommonResponse cr = new CommonResponse();
        var results = movieRepository.getMovieByTitleContaining(searchTitle);

        cr.data = results;
        cr.message = results.size() > 0 ? "Results of movie search for: " + title : "No movies were found containing : " + title;

        HttpStatus resp = HttpStatus.OK;

        return new ResponseEntity<>(cr, resp);

    }

}
