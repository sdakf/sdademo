package pl.sda.democlass.sdademo.users;

import org.springframework.security.crypto.password.PasswordEncoder;

public class UserRegistrationDtoToUserBuilder {

    public static User rewriteToUser(CustomerRegistrationDto registrationData, PasswordEncoder passwordEncoder) {
        User user = null; //todo 4 należy utworzyć nowy obiekt użytkownika
        user.setFirstName(registrationData.getFirstName().trim());
        user.setSurName(registrationData.getLastName().trim());
        user.setEmail(registrationData.getEmail().trim());
        user.setPasswordHash(passwordEncoder.encode(registrationData.getPassword().trim()));
        //todo 5 zamienić nulle na poprawne wartości
        UserAddress userAddress = null;
        userAddress.setCity(null);
        userAddress.setCountry(null);
        userAddress.setStreet(null);
        userAddress.setZipCode(null);
        user.setUserAddress(null);

        return user;
    }
}
