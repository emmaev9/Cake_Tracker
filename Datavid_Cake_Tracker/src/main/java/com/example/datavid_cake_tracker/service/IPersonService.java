package com.example.datavid_cake_tracker.service;

import com.example.datavid_cake_tracker.dto.InsertPersonDTO;
import com.example.datavid_cake_tracker.entity.Person;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface IPersonService {

    Person addPerson(InsertPersonDTO personDTO);
    String removePerson(UUID personId);
    List<Person> findEveryone();
    int calculateAge(LocalDate birthDate);
}
