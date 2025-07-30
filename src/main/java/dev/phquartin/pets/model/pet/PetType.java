package dev.phquartin.pets.model.pet;

public enum PetType {
    DOG("Dog"),
    CAT("Cat");

    private final String name;
    PetType(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return name;
    }

}
