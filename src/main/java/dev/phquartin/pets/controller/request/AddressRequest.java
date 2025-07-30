package dev.phquartin.pets.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

@Builder
public record AddressRequest(
        @Length(min = 5, message = "City must have at least 5 characters")
        @Length(max = 255, message = "City must have less than 255 characters")
        @NotBlank(message = "City cannot be empty")
        String city,
        @Length(min = 3, message = "Street must have at least 3 characters")
        @Length(max = 255, message = "Street must have less than 255 characters")
        @NotBlank(message = "Street cannot be empty")
        String street,
        String number
) {
}
