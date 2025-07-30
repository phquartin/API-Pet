package dev.phquartin.pets.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record PetMultipleRequest(Long id, String firstname, String lastname, String gender, @NotBlank(message = "Animal type must be defined") String type, String breed, Double age, Double weight, AddressMultipleRequest address) {
}
