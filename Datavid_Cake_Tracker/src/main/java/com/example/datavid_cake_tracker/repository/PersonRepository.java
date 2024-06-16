package com.example.datavid_cake_tracker.repository;

import com.example.datavid_cake_tracker.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {
    boolean existsByFirstNameAndLastNameAndCountryAndCity(String firstName, String lastName, String country, String city);
}
