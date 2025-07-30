package dev.phquartin.pets.controller.request;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

@Builder
public record PetRequest(

        @NotBlank(message = "Firstname cannot be empty")
        @Length(min = 3, message = "Firstname must have at least 3 characters")
        @Length(max = 100, message = "Firstname must have less than 100 characters")
        String firstname,

        @NotBlank(message = "Lastname cannot be empty")
        @Length(min = 3, message = "Lastname must have at least 3 characters")
        @Length(max = 100, message = "Lastname must have less than 100 characters")
        String lastname,

        @NotBlank(message = "Gender cannot be empty")
        String gender,

        @NotBlank(message = "Type cannot be empty")
        String type,

        String breed,
        Double age,
        Double weight,

        @NotNull(message = "Address cannot be null")
        AddressRequest address
) {
}
