package com.example.datavid_cake_tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class InsertPersonDTO {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String country;
    private String city;
}
