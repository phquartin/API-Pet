package dev.phquartin.pets.model.pet.specification;

import dev.phquartin.pets.controller.request.PetMultipleRequest;
import dev.phquartin.pets.model.pet.Pet;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PetSpecification implements Specification<Pet> {

    private final PetMultipleRequest request;

    public PetSpecification(PetMultipleRequest request) {
        this.request = request;
    }

    @Override
    public Predicate toPredicate(Root<Pet> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        // Pet fields

        predicates.add(criteriaBuilder.equal(root.get("type"), request.type()));

        if (Objects.nonNull(request.firstname()) && !request.firstname().isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("firstname"), "%" + request.firstname() + "%"));
        }
        if (Objects.nonNull(request.lastname()) && !request.lastname().isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("lastname"), "%" + request.lastname() + "%"));
        }
        if (Objects.nonNull(request.gender()) && !request.gender().isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get("gender"), request.gender()));
        }
        if (Objects.nonNull(request.breed()) && !request.breed().isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("breed"), "%" + request.breed() + "%"));
        }
        if (Objects.nonNull(request.age())) {
            predicates.add(criteriaBuilder.equal(root.get("age"), request.age()));
        }
        if (Objects.nonNull(request.weight())) {
            predicates.add(criteriaBuilder.equal(root.get("weight"), request.weight()));
        }

        if (Objects.nonNull(request.address())) {
            if (Objects.nonNull(request.address().city()) && !request.address().city().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("address").get("city"), "%" + request.address().city() + "%"));
            }
            if (Objects.nonNull(request.address().street()) && !request.address().street().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("address").get("street"), "%" + request.address().street() + "%"));
            }
            if (Objects.nonNull(request.address().number()) && !request.address().number().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("address").get("number"), request.address().number()));
            }
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}