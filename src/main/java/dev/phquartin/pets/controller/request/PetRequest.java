package dev.phquartin.pets.controller.request;

import jakarta.validation.constraints.*;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

@Builder
public record PetRequest(

        @NotBlank(message = "Firstname cannot be empty")
        @Length(min = 3, message = "Firstname must have at least 3 characters")
        @Length(max = 100, message = "Firstname must have less than 100 characters")
        @Pattern(regexp = "^\\p{L}+$", message = "Firstname can contain only letters")
        String firstname,

        @NotBlank(message = "Lastname cannot be empty")
        @Length(min = 3, message = "Lastname must have at least 3 characters")
        @Length(max = 100, message = "Lastname must have less than 100 characters")
        @Pattern(regexp = "^[\\p{L}\\s]+$", message = "Lastname can contain only letters and spaces")
        String lastname,

        @NotBlank(message = "Gender cannot be empty")
        String gender,

        @NotBlank(message = "Type cannot be empty")
        String type,

        @Pattern(regexp = "^[\\p{L}\\s]+$", message = "Breed can contain only letters and spaces")
        String breed,

        @Max(value = 20, message = "Age must be less than 20 years")
        @Min(value = 0, message = "Age must be more than 0")
        Double age,
        @Max(value = 60, message = "Weight must not be heavier than 60kg")
        @DecimalMin(value = "0.5", message = "Weight must be heavier than 0.5kg")
        Double weight,

        @NotNull(message = "Address cannot be null")
        AddressRequest address
) {
}
