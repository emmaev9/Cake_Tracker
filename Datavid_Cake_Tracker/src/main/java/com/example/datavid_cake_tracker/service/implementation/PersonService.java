    package com.example.datavid_cake_tracker.service.implementation;

    import com.example.datavid_cake_tracker.dto.InsertPersonDTO;
    import com.example.datavid_cake_tracker.entity.Person;
    import com.example.datavid_cake_tracker.exception.ResourceAlreadyExistsException;
    import com.example.datavid_cake_tracker.exception.ResourceNotFoundException;
    import com.example.datavid_cake_tracker.exception.UnderagePersonException;
    import com.example.datavid_cake_tracker.repository.PersonRepository;
    import com.example.datavid_cake_tracker.service.IPersonService;
    import lombok.AllArgsConstructor;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.stereotype.Service;

    import java.time.LocalDate;
    import java.time.temporal.ChronoUnit;
    import java.util.Comparator;
    import java.util.List;
    import java.util.Optional;
    import java.util.UUID;
    import java.util.stream.Collectors;

    @Service
    @AllArgsConstructor
    public class PersonService implements IPersonService {
        private final PersonRepository personRepository;
        private final static Logger LOGGER = LoggerFactory.getLogger(PersonService.class);

        public Person addPerson(InsertPersonDTO personDTO){

            //checking if the person is unique
            boolean existsPerson = personRepository.existsByFirstNameAndLastNameAndCountryAndCity(
                    personDTO.getFirstName(),
                    personDTO.getLastName(),
                    personDTO.getCountry(),
                    personDTO.getCity()
            );
            if(existsPerson){
                LOGGER.error("A person with this details already exists in the DB!");
                throw new ResourceAlreadyExistsException("A person with this details already exists!");
            }

            //checking if the person has at least 18 years old
            int personAge = calculateAge(personDTO.getBirthDate());
            if(personAge < 18){
                LOGGER.error("The person should have at least 18 years old!");
                throw new UnderagePersonException("The person should have at least 18 years old!");
            }
            Person newPerson = new Person();
            newPerson.setFirstName(personDTO.getFirstName());
            newPerson.setLastName(personDTO.getLastName());
            newPerson.setBirthDate(personDTO.getBirthDate().plusDays(1));
            newPerson.setCountry(personDTO.getCountry());
            newPerson.setCity(personDTO.getCity());
            newPerson = personRepository.save(newPerson);

            return newPerson;
        }

        public String removePerson(UUID personId){
            Optional<Person> personOptional = personRepository.findById(personId);
            if(!personOptional.isPresent()){
                LOGGER.error("Person with id " + personId + " was not found in the database!");
                throw new ResourceNotFoundException(Person.class.getSimpleName() + " with id: " + personId);
            }
            personRepository.delete(personOptional.get());
            return "Person was successfully deleted!";
        }

        public List<Person> findEveryone() {
            List<Person> personList = personRepository.findAll();
            if (personList.isEmpty()) {
                LOGGER.error("There are no people in the database!");
                //throw new ResourceNotFoundException(Person.class.getSimpleName());
            }

            personList =  personList.stream()
                    .sorted(Comparator.comparingInt(this::calculateDaysUntilNextBirthday))
                    .collect(Collectors.toList());
            for(Person person: personList){
                System.out.println("Date: " + person.getBirthDate() + ", Days until next birthday: " + calculateDaysUntilNextBirthday(person));
            }
            return personList;
        }

        private int calculateDaysUntilNextBirthday(Person person) {
            LocalDate today = LocalDate.now();
            LocalDate birthDate = person.getBirthDate();
            LocalDate nextBirthday = birthDate.withYear(today.getYear());

            if (nextBirthday.isBefore(today)) {
                nextBirthday = nextBirthday.plusYears(1);
            }

            return (int) ChronoUnit.DAYS.between(today, nextBirthday);
        }

        public int calculateAge(LocalDate birthDate){
            LocalDate todayDate = LocalDate.now();
            int age = todayDate.getYear() - birthDate.getYear();
            if(todayDate.getDayOfYear() < birthDate.getDayOfYear()){
                age--;
            }
            return age;
        }
    }
