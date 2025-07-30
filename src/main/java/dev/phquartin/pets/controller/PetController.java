package dev.phquartin.pets.controller;

import dev.phquartin.pets.controller.request.PetMultipleRequest;
import dev.phquartin.pets.controller.request.PetRequest;
import dev.phquartin.pets.controller.response.PetResponse;
import dev.phquartin.pets.service.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Pets", description = "API for managing pets")
@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetService service;

    public PetController(PetService service) {
        this.service = service;
    }

    @Operation(summary = "Creates a new pet",
            description = "Registers a new pet in the system with the provided data.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Pet object to be created",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PetRequest.class), // Schema do Request Body
                            examples = {
                                    @ExampleObject(
                                            name = "Example Pet Creation",
                                            summary = "Basic example for creating a pet",
                                            value = "{\"firstname\": \"Whiskers\", \"lastname\": \"Fluffy\", \"gender\": \"Female\", \"type\": \"Cat\", \"breed\": \"Siamese\", \"age\": 2.0, \"weight\": 3.5, \"address\": {\"city\": \"Springfield\", \"street\": \"Evergreen Terrace\", \"number\": \"742\"}}"
                                    ),
                                    @ExampleObject(
                                            name = "Example Pet with Nullable Fields",
                                            summary = "Example showing nullable fields (breed, age, weight, number)",
                                            value = "{\"firstname\": \"Shadow\", \"lastname\": \"Miau\", \"gender\": \"Male\", \"type\": \"Cat\", \"address\": {\"city\": \"Shelbyville\", \"street\": \"Main Street\", \"number\": null}}"
                                    )
                            }
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Pet created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PetResponse.class),
                                    examples = @ExampleObject(name = "Successful Creation Example",
                                            summary = "Successful pet creation response",
                                            value = "{\"id\": 1, \"firstname\": \"Buddy\", \"lastname\": \"Smith\", \"gender\": \"Male\", \"type\": \"Dog\", \"breed\": \"Golden Retriever\", \"age\": 3.0, \"weight\": 25.5, \"address\": {\"city\": \"New York\", \"street\": \"Main St\", \"number\": \"123\"}}"))),
                    @ApiResponse(responseCode = "400", description = "Invalid request (missing or malformed data)",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(name = "Validation Error Example",
                                            summary = "Request validation error",
                                            value = "{\"timestamp\": \"2025-07-30T18:00:00.000+00:00\", \"status\": 400, \"error\": \"Bad Request\", \"path\": \"/pet\", \"message\": \"Validation failed: Firstname cannot be empty\"}")))
            })
    @PostMapping
    public ResponseEntity<PetResponse> create(@Valid @RequestBody PetRequest request) {
        PetResponse petResponse = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(petResponse);
    }

    @Operation(summary = "Lists all pets",
            description = "Returns a list of all pets registered in the system.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of pets returned successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PetResponse.class),
                                    examples = @ExampleObject(name = "List of Pets Example",
                                            summary = "List of pets returned",
                                            value = "[{\"id\": 1, \"firstname\": \"Buddy\", \"lastname\": \"Smith\", \"gender\": \"Male\", \"type\": \"Dog\", \"breed\": \"Golden Retriever\", \"age\": 3.0, \"weight\": 25.5, \"address\": {\"city\": \"New York\", \"street\": \"Main St\", \"number\": \"123\"}}, {\"id\": 2, \"firstname\": \"Whiskers\", \"lastname\": \"Jones\", \"gender\": \"Female\", \"type\": \"Cat\", \"breed\": \"Siamese\", \"age\": 1.5, \"weight\": 4.2, \"address\": {\"city\": \"Los Angeles\", \"street\": \"Oak Ave\", \"number\": \"456\"}}]"))),
                    @ApiResponse(responseCode = "204", description = "No pets found",
                            content = @Content(mediaType = "application/json")) // Example: might have no content or return empty list
            })
    @GetMapping
    public ResponseEntity<List<PetResponse>> list() {
        return ResponseEntity.ok(service.getAllPets());
    }

    @Operation(summary = "Retrieves a pet by ID",
            description = "Fetches and returns the details of a specific pet using its ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pet found successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PetResponse.class),
                                    examples = @ExampleObject(name = "Found Pet Example",
                                            summary = "Response for pet found by ID",
                                            value = "{\"id\": 1, \"firstname\": \"Buddy\", \"lastname\": \"Smith\", \"gender\": \"Male\", \"type\": \"Dog\", \"breed\": \"Golden Retriever\", \"age\": 3.0, \"weight\": 25.5, \"address\": {\"city\": \"New York\", \"street\": \"Main St\", \"number\": \"123\"}}"))),
                    @ApiResponse(responseCode = "404", description = "Pet not found",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(name = "Pet Not Found Example",
                                            summary = "Pet not found error",
                                            value = "{\"timestamp\": \"2025-07-30T18:00:00.000+00:00\", \"status\": 404, \"error\": \"Not Found\", \"path\": \"/pet/999\", \"message\": \"Pet with ID 999 not found.\" }")))
            })
    @GetMapping("/{id}")
    public ResponseEntity<PetResponse> getPetById(@Parameter(description = "ID of the pet to retrieve", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(service.getPetById(id));
    }

    @Operation(summary = "Searches for pets by multiple attributes",
            description = "Allows searching for pets by applying filters on multiple attributes (e.g., type, gender, city).",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Object with attributes for searching",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PetMultipleRequest.class),
                            examples = {
                                    @ExampleObject(name = "Search by Type and Gender",
                                            summary = "Request to search pets by type and gender",
                                            value = "{\"type\": \"Dog\", \"gender\": \"Male\"}"),
                                    @ExampleObject(name = "Search by City",
                                            summary = "Request to search pets by address city",
                                            value = "{\"address\": {\"city\": \"New York\"}}")
                            }
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of pets found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PetResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid search request",
                            content = @Content(mediaType = "application/json"))
            })
    @PostMapping("/search")
    public ResponseEntity<List<PetResponse>> getPetsByAttribute(@Valid @RequestBody PetMultipleRequest request) {
        return ResponseEntity.ok(service.getPetsByAttributes(request));
    }


    @Operation(summary = "Updates a pet by ID",
            description = "Updates the data of an existing pet in the system.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Pet object with updated details",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PetRequest.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Full Pet Update Example",
                                            summary = "Example for updating all pet fields",
                                            value = "{\"firstname\": \"Max\", \"lastname\": \"Power\", \"gender\": \"Male\", \"type\": \"Dog\", \"breed\": \"German Shepherd\", \"age\": 5.0, \"weight\": 30.0, \"address\": {\"city\": \"Springfield\", \"street\": \"Elm Street\", \"number\": \"10\"}}"
                                    ),
                                    @ExampleObject(
                                            name = "Partial Pet Update Example (only name and age)",
                                            summary = "Example for updating only selected pet fields",
                                            value = "{\"firstname\": \"Fido\", \"age\": 4.5, \"address\": {\"city\": \"Anytown\", \"street\": \"Any Street\", \"number\": \"1\"}}"
                                    )
                            }
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pet updated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PetResponse.class),
                                    examples = @ExampleObject(name = "Successful Update Example",
                                            summary = "Successful pet update response",
                                            value = "{\"id\": 1, \"firstname\": \"Buddy\", \"lastname\": \"New Smith\", \"gender\": \"Male\", \"type\": \"Dog\", \"breed\": \"Golden Retriever\", \"age\": 4.0, \"weight\": 26.0, \"address\": {\"city\": \"New York\", \"street\": \"Park Ave\", \"number\": \"456\"}}"))),
                    @ApiResponse(responseCode = "400", description = "Invalid request (missing or malformed data)",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Pet not found",
                            content = @Content(mediaType = "application/json"))
            })
    @PutMapping("/{id}")
    public ResponseEntity<PetResponse> updatePetById(@Parameter(description = "ID of the pet to update", example = "1") @PathVariable Long id,
                                                     @Valid @RequestBody PetRequest request) {
        return ResponseEntity.ok(service.updatePetById(id, request));
    }

    @Operation(summary = "Deletes a pet by ID",
            description = "Removes a pet from the system using its ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Pet deleted successfully (no content)",
                            content = @Content()),
                    @ApiResponse(responseCode = "404", description = "Pet not found",
                            content = @Content(mediaType = "application/json"))
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePetById(@Parameter(description = "ID of the pet to delete", example = "1") @PathVariable Long id) {
        service.deletePetById(id);
        return ResponseEntity.noContent().build();
    }
}