package se.experis.com.movieapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.experis.com.movieapp.models.CommonResponse;
import se.experis.com.movieapp.models.Genre;
import se.experis.com.movieapp.models.Movie;
import se.experis.com.movieapp.repositories.GenreRepository;
import se.experis.com.movieapp.repositories.MovieRepository;

import java.util.List;

@RestController
@RequestMapping("api/v1/genre")
public class GenreController {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private MovieRepository movieRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse> getAllGenre() {

        var commonResponse = new CommonResponse();
        commonResponse.data = genreRepository.findAll();
        commonResponse.message = "All genre";

        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse> addGenre(@RequestBody Genre genre) {

        genre = genreRepository.save(genre);

        var commonResponse = new CommonResponse();
        commonResponse.data = genre;
        commonResponse.message = "added genre: " + genre.getGenreName();

        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse> deleteGenre(@PathVariable Integer id) {
        {
            CommonResponse cr = new CommonResponse();
            HttpStatus resp;

            if (genreRepository.existsById(id)) {

                List<Movie> movieList = movieRepository.findAll();
                Movie toBeUpdated = null;

                for (int i = 0; i < movieList.size(); i++) {
                    if (movieList.get(i).getGenre() != null && movieList.get(i).getGenre().getId() == id) {
                        toBeUpdated = movieList.get(i);
                        toBeUpdated.setGenre( null);
                    }
                }

                movieRepository.save(toBeUpdated);
                genreRepository.deleteById(id);

                cr.message = "Deleted Genre with id: " + id;
                resp = HttpStatus.OK;
            } else {
                cr.message = "Genre not found with id: " + id;
                resp = HttpStatus.NOT_FOUND;
            }

            return new ResponseEntity<>(cr, resp);
        }
    }

}
