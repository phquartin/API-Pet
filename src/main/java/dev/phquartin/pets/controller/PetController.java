package dev.phquartin.pets.controller;

import dev.phquartin.pets.controller.request.PetMultipleRequest;
import dev.phquartin.pets.controller.request.PetRequest;
import dev.phquartin.pets.controller.response.PetResponse;
import dev.phquartin.pets.service.PetService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<PetResponse>> list(){
        return ResponseEntity.ok(service.getAllPets());
    }
    @GetMapping("/{id}")
    public ResponseEntity<PetResponse> getPetById(@PathVariable Long id){
        return ResponseEntity.ok(service.getPetById(id));
    }
    @PostMapping("/search")
    public ResponseEntity<List<PetResponse>> getPetsByAttribute(@Valid @RequestBody PetMultipleRequest request){
        return ResponseEntity.ok(service.getPetsByAttributes(request));
    }


    @PutMapping("/{id}")
    public ResponseEntity<PetResponse> updatePetById(@PathVariable Long id, @Valid @RequestBody PetRequest request){
        return ResponseEntity.ok(service.updatePetById(id, request));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePetById(@PathVariable Long id){
        service.deletePetById(id);
        return ResponseEntity.noContent().build();
    }

}
