package dev.phquartin.pets.service;

import dev.phquartin.pets.controller.request.AddressRequest;
import dev.phquartin.pets.controller.request.PetRequest;
import dev.phquartin.pets.controller.response.PetResponse;
import dev.phquartin.pets.exception.NoDataFoundException;
import dev.phquartin.pets.mapper.PetMapper;
import dev.phquartin.pets.model.pet.Pet;
import dev.phquartin.pets.model.pet.PetGender;
import dev.phquartin.pets.model.pet.PetType;
import dev.phquartin.pets.model.pet.address.Address;
import dev.phquartin.pets.repository.AddressRepository;
import dev.phquartin.pets.repository.PetRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final AddressRepository addressRepository;
    private final PetMapper petMapper;

    public PetResponse create(PetRequest request){

        // Salvar Endereco
        AddressRequest addressRequest = request.address();
        Address entityAddress = petMapper.toEntityAddress(addressRequest);
        Address addressEntity = addressRepository.save(entityAddress);

        // Salvar Pet
        Pet entityPet = Pet.builder()
                .firstname(request.firstname())
                .lastname(request.lastname())
                .type(PetType.valueOf(request.type().toUpperCase()))
                .gender(PetGender.valueOf(request.gender().toUpperCase()))
                .age(request.age())
                .weight(request.weight())
                .breed(request.breed())
                .address(addressEntity)
                .build();

        Pet saved = petRepository.save(entityPet);

        return petMapper.toResponsePet(saved);

    }

    public List<PetResponse> getAllPets(){
        List<Pet> pets = petRepository.findAll();
        return pets.stream().map(petMapper::toResponsePet).toList();
    }
    public PetResponse getPetById(Long id){
        Pet pet = petRepository.findById(id).orElseThrow(() -> new NoDataFoundException("Pet with id " + id + " does not exist"));
        return petMapper.toResponsePet(pet);
    }
    public void deletePetById(Long id){
        petRepository.findById(id).orElseThrow(() -> new NoDataFoundException("Pet with id " + id + " does not exist"));
        petRepository.deleteById(id);
    }

}
