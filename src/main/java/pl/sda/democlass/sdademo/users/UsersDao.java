package pl.sda.democlass.sdademo.users;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class UsersDao {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void addUser(User user) {
        Role role = new Role();
        role.setRoleName("ROLE_USER");
        user.setRoles(Sets.newHashSet(role));
        usersRepository.save(user);
    }

    public Optional<User> getUserByUsername(String username) {
        return usersRepository.findUserByUsername(username);
    }

    @PostConstruct
    public void initialize() {
        registerNewUser("admin@admin.pl", "admin", "ROLE_ADMIN");
        registerNewUser("user@user.pl", "user", "ROLE_USER");
    }

    private void registerNewUser(String username, String pass, String roleName) {
        if (usersRepository.findUserByUsername(username).isPresent()) {
            return;
        }
        User user = new User();
        user.setUsername(username);
        user.setUserAddress(new UserAddress("A", "Lodz", "PL", "92-001"));
        Role role = new Role();
        role.setRoleName(roleName);
        user.setRoles(Sets.newHashSet(role));
        user.setPasswordHash(passwordEncoder.encode(pass));
        usersRepository.save(user);
    }

}
