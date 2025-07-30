package dev.phquartin.pets.controller.response;

import lombok.Builder;

@Builder
public record PetResponse (Long id, String firstname, String lastname, String gender, String type, String breed, Double age, Double weight, AddressResponse address){
}
