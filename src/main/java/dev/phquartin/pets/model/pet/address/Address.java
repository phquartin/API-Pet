package dev.phquartin.pets.model.pet.address;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.phquartin.pets.model.pet.Pet;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "address")

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String street;

    private String number;

    @OneToMany(mappedBy = "address")
    @JsonIgnore
    private List<Pet> pets;

}
