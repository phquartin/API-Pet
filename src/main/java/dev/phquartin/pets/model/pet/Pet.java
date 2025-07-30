package dev.phquartin.pets.model.pet;

import dev.phquartin.pets.model.pet.address.Address;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@Table(name = "pet")

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String firstname;

    @Column(nullable = false, length = 100)
    private String lastname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PetType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PetGender gender;

    @Max(value = 20)
    @Min(value = 0)
    private Double age;

    @Max(value = 60)
    @DecimalMin(value = "0.5")
    private Double weight;

    @Column(length = 100)
    private String breed;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

}
