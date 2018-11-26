package pl.sda.democlass.sdademo.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {

    private PasswordEncoder passwordEncoder;
    private UsersService usersService;

    @Autowired
    public UserRegistrationService(PasswordEncoder passwordEncoder, UsersService usersService) {
        this.passwordEncoder = passwordEncoder;
        this.usersService = usersService;
    }

    public void registerUser(CustomerRegistrationDto customerRegistrationDto) {
        if (usersService.getUserByEmail(customerRegistrationDto.getEmail().trim()).isPresent()) {
            throw new UserExistsException("User with email " + customerRegistrationDto.getEmail() + "already exists in database");
        } else {
            User user = UserRegistrationDtoToUserBuilder.rewriteToUser(customerRegistrationDto, passwordEncoder);
            usersService.addUser(user);
        }
    }
}