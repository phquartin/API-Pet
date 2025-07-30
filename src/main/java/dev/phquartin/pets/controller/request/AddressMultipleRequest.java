package dev.phquartin.pets.controller.request;

import lombok.Builder;

@Builder
public record AddressMultipleRequest(Long id, String city, String street, String number) {
}
