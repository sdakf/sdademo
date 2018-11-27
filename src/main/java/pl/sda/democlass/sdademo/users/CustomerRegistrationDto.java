package pl.sda.democlass.sdademo.users;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CustomerRegistrationDto {

    @Pattern(regexp = "^[\\p{Lu}][\\p{Ll}]{2,}$", message = "Wymagane przynajmniej 3 znaki(pierwsza litera duża, reszta małe).")
    private String firstName;
    @Pattern(regexp = "^[\\p{Lu}][\\p{Ll}]{2,}(-[\\p{Lu}][\\p{Ll}]{2,})?$", message = "Wymagane przynajmniej 3 znaki(pierwsza litera duża, można podać także nazwisko dwuczłonowe).")
    private String lastName;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*", message = "Hasło jest wymagane. Musi zawierać od 10 do 20 znaków, jedną duża, jedna małą literę i cyfrę.")
    private String password;
    @Pattern(regexp = "^[\\w\\.]+@[\\w]+\\.[\\w]+(\\.[a-z]{2,3})?$", message = "Zły format adresu email")
    private String email;
    @Pattern(regexp = "" /*todo 2 - należy uzupełnić poprawny regex*/, message = "Zły format. Numer PESEL powinien składać się z 11 cyfr")
    private String pesel;
    @Pattern(regexp = "^(19|20)[0-9]{2}-(0[1-9]|1[0-2])-(0[1-9]|(1|2)[0-9]|3[0-1])$", message = "Zły format. Data urodzin powinna być podana w formacie RRRR-MM-DD")
    private String birthDate;
    @Pattern(regexp = "^(\\+\\d{1,3} )?(\\d{3}-?){2}\\d{3}$",message = "Numer telefonu może składać się z kierunkowego kraju i 9 cyfr bądź z samych 9 cyfr (dozwolone myślniki)")
    private String phone;
    @NotBlank(message = "Pole musi zostać wypełnione")
    private String street;
    @NotBlank(message = "Pole musi zostać wypełnione")
    private String city;
    @NotBlank(message = "Pole musi zostać wypełnione")
    private String country;
    @NotBlank(message = "Pole musi zostać wypełnione")
    private String zipCode;
    private boolean preferEmails;

}
