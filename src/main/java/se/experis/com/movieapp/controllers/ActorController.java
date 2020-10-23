package se.experis.com.movieapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.experis.com.movieapp.models.Actor;
import se.experis.com.movieapp.models.CommonResponse;
import se.experis.com.movieapp.repositories.ActorRepository;
import se.experis.com.movieapp.repositories.MovieRepository;

import java.time.LocalDate;

@RestController
@RequestMapping("api/v1/actors")
public class ActorController {

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private MovieRepository movieRepository;


    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse> getAllActors() {

        var commonResponse = new CommonResponse();
        commonResponse.data = actorRepository.findAll();
        commonResponse.message = "All actors";

        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }


    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse> addActor(@RequestBody Actor actor) {
        var commonResponse = new CommonResponse();

        actor = actorRepository.save(actor);

        commonResponse.data = actor;
        commonResponse.message = "New actor created with the id: " + actor.getId();

        return new ResponseEntity<>(commonResponse, HttpStatus.CREATED);
    }

    /**
     * Method that returns actors that are born between two dates.
     * @param from date
     * @param to date
     */
    @RequestMapping(value = "/filter", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse> getActorsByDate(@RequestParam("from") String from, @RequestParam("to") String to) {

        var commonResponse = new CommonResponse();

        LocalDate fromDate = LocalDate.parse(from);
        LocalDate toDate = LocalDate.parse(to);

        commonResponse.data = actorRepository.filterByDate(fromDate, toDate);
        commonResponse.message = "Actors between " + fromDate + " - " + toDate.toString();

        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse> deleteActor(@PathVariable Integer id) {
        CommonResponse cr = new CommonResponse();
        HttpStatus resp;

        if (actorRepository.existsById(id)) {

            // Necessary since movie_actor has foreing key
            // to actor. Not required in movies(delete for movies) case because
            // movie is the owning table.
            movieRepository.deleteActor(id);
            actorRepository.deleteById(id);

            cr.message = "Deleted actor with id: " + id;
            resp = HttpStatus.OK;
        } else {
            cr.message = "Actor not found with id: " + id;
            resp = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(cr, resp);
    }
}

