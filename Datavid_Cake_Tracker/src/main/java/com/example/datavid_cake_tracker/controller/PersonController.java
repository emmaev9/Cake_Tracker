package com.example.datavid_cake_tracker.controller;

import com.example.datavid_cake_tracker.dto.ErrorResponse;
import com.example.datavid_cake_tracker.dto.InsertPersonDTO;
import com.example.datavid_cake_tracker.entity.Person;
import com.example.datavid_cake_tracker.exception.ResourceAlreadyExistsException;
import com.example.datavid_cake_tracker.exception.ResourceNotFoundException;
import com.example.datavid_cake_tracker.exception.UnderagePersonException;
import com.example.datavid_cake_tracker.service.implementation.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping(value = "/person")
public class PersonController {
    private final PersonService personService;

    @GetMapping ("/allPeople")
    public ResponseEntity<List<Person>> getPeople(){
        List<Person> people = personService.findEveryone();
        return new ResponseEntity<>(people, HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<Person> insertPerson(@RequestBody InsertPersonDTO personDTO){
        Person person = personService.addPerson(personDTO);
        return new ResponseEntity<>(person, HttpStatus.CREATED);
    }

    @ExceptionHandler(value = ResourceAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handlePersonAlreadyExistsException(ResourceAlreadyExistsException ex) {
        return new ErrorResponse(ex.getStatus().value(), ex.getMessage(), ex.getValidationErrors());
    }

    @ExceptionHandler(value = UnderagePersonException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleUnderagePersonException(UnderagePersonException ex){
        return new ErrorResponse(ex.getStatus().value(), ex.getMessage(), ex.getValidationErrors());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePerson(@PathVariable("id")UUID personToDeleteId){
        personService.removePerson(personToDeleteId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundPersonException(ResourceNotFoundException ex){
        return new ErrorResponse(ex.getStatus().value(), ex.getMessage(), ex.getValidationErrors());
    }


}
