package dev.phquartin.pets.controller;

import dev.phquartin.pets.controller.request.PetRequest;
import dev.phquartin.pets.controller.response.PetResponse;
import dev.phquartin.pets.service.PetService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetService service;
    public PetController(PetService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PetResponse> create(@Valid @RequestBody PetRequest request){

        PetResponse petResponse = service.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(petResponse);
    }

}
