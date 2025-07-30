package dev.phquartin.pets.mapper;

import dev.phquartin.pets.controller.request.AddressRequest;
import dev.phquartin.pets.controller.request.PetRequest;
import dev.phquartin.pets.controller.response.AddressResponse;
import dev.phquartin.pets.controller.response.PetResponse;
import dev.phquartin.pets.model.pet.Pet;
import dev.phquartin.pets.model.pet.address.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PetMapper {

    Pet toEntityPet(PetRequest request);
    PetResponse toResponsePet(Pet pet);

    Address toEntityAddress(AddressRequest request);
    AddressResponse toResponseAddress(Address address);

}
