package pl.sda.democlass.sdademo.users;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class CustomerRegistrationDto {

    @Size(min = 6, max = 20, message = "Imię nieprawidłowe. Podaj min {min} znaków a max {max}. Podałeś ${validatedValue}")
    private String firstName;
    private String surname;
    private String password;
    private String email;
    private String pesel;
    private String birthDate;
    private String phone;
    private String street;
    private String city;
    private String country;
    private String zipCode;
    private boolean preferEmails;

}
