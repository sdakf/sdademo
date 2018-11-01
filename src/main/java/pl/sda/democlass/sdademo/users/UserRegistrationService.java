package pl.sda.democlass.sdademo.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserRegistrationService {

    private PasswordEncoder passwordEncoder;
    private final UsersDao usersDao;

    @Autowired
    public UserRegistrationService(PasswordEncoder passwordEncoder, UsersDao usersDao) {
        this.passwordEncoder = passwordEncoder;
        this.usersDao = usersDao;
    }

    public void registerUser(CustomerRegistrationDto customerRegistrationDto) {
        if (usersDao.getUserByUsername(customerRegistrationDto.getEmail().trim()).isPresent()) {
            throw new UserExistsException("User with username " + customerRegistrationDto.getEmail() + "already exists in database");
        } else {
            User user = UserRegistrationDtoToUserBuilder.rewriteToUser(customerRegistrationDto, passwordEncoder);
            usersDao.addUser(user);
        }
    }
}