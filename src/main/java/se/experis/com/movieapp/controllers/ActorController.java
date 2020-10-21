package se.experis.com.movieapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.experis.com.movieapp.models.CommonResponse;
import se.experis.com.movieapp.repositories.ActorRepository;

@RestController
@RequestMapping("api/v1/actors")
public class ActorController {

    @Autowired
    private ActorRepository actorRepository;

    public ResponseEntity<CommonResponse> getAllActors() {
        var commonResponse = new CommonResponse();
        commonResponse.data = actorRepository.findAll();
        commonResponse.message = "All actors";

        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }
}

