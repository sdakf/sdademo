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

    public void registerUser(CustomerRegistrationDto dto) {
        boolean userExists = usersService.getUserByEmail(dto.getEmail().trim()).isPresent();
        //todo 9 - jeśli istnieje już taki użytkownik to należy zgłosić wyjątek (metoda showExceptionThatUserExists),
        //todo 9 natomiast jeśli takiego jeszcze nie ma to można go zarejestrować (metoda rewriteDataAndRegisterUser

    }

    private void rewriteDataAndRegisterUser(CustomerRegistrationDto dto) {
        User user = UserRegistrationDtoToUserBuilder.rewriteToUser(dto, passwordEncoder);
        usersService.addUser(user);
    }

    private void showExceptionThatUserExists(CustomerRegistrationDto dto) {
        throw new UserExistsException("User with email " + dto.getEmail() + "already exists in database");
    }
}