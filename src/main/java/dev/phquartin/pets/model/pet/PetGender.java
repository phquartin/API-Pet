package dev.phquartin.pets.model.pet;

public enum PetGender {
    MALE("Male"),
    FEMALE("Female");

    private final String name;
    PetGender(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return name;
    }

}
