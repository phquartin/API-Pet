package dev.phquartin.pets.controller.response;

import lombok.Builder;

@Builder
public record AddressResponse(Long id, String city, String street, String number) {
}
