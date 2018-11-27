package pl.sda.democlass.sdademo.users;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RolesRepository rolesRepository;

    public void addUser(User user) {
        user.setRoles(Sets.newHashSet(rolesRepository.findByRoleName("ROLE_USER")));
        usersRepository.save(user);
    }

    public Optional<User> getUserByEmail(String email) {
        return usersRepository.findUserByEmail(email);
    }

    @PostConstruct
    public void initialize() {
        registerNewMockUser("user@user.pl", "user", "ROLE_USER");
    }

    private void registerNewMockUser(String username, String pass, String roleName) {
        if (usersRepository.findUserByEmail(username).isPresent()) {
            return;
        }
        boolean userExists = false;
        List<User> users = usersRepository.findAll();
        //todo 11 - tworzymy testowego użytkownika tylko jeśli już taki nie istnieje
        for (User user : users) {
            if (username.equalsIgnoreCase(user.getEmail())) {
                userExists = true;
                break;
            }
        }
        if (!userExists) {
            User user = new User();
            user.setEmail(username);
            user.setUserAddress(new UserAddress("Kilinskiego", "Lodz", "PL", "92-001"));
            Role role = new Role();
            role.setRoleName(roleName);
            user.setRoles(Sets.newHashSet(role));
            user.setPasswordHash(passwordEncoder.encode(pass));
            usersRepository.save(user);
        }
    }
}
