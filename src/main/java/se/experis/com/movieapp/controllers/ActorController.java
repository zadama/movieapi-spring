package se.experis.com.movieapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import se.experis.com.movieapp.models.Actor;
import se.experis.com.movieapp.models.CommonResponse;
import se.experis.com.movieapp.repositories.ActorRepository;

@RestController
@RequestMapping("api/v1/actors")
public class ActorController {

    @Autowired
    private ActorRepository actorRepository;

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
        commonResponse.message = "New actor created with the id: " +actor.id;

        return new ResponseEntity<>(commonResponse,HttpStatus.CREATED);
    }
}

