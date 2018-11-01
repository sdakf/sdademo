package pl.sda.democlass.sdademo.users;

import org.springframework.security.crypto.password.PasswordEncoder;

public class UserRegistrationDtoToUserBuilder {

    public static User rewriteToUser(CustomerRegistrationDto customerRegistrationDto, PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setFirstName(customerRegistrationDto.getFirstName().trim());
        user.setSurName(customerRegistrationDto.getSurname().trim());
        user.setUsername(customerRegistrationDto.getEmail().trim());
        user.setPasswordHash(passwordEncoder.encode(customerRegistrationDto.getPassword().trim()));
        user.setUserAddress(new UserAddress(customerRegistrationDto.getStreet().trim(), customerRegistrationDto.getCity().trim(), customerRegistrationDto.getCountry().trim(), customerRegistrationDto.getZipCode().trim()));
        return user;
    }
}
