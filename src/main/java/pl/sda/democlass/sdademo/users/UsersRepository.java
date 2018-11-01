package pl.sda.democlass.sdademo.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where upper(u.username) = upper(:username) ")
    Optional<User> findUserByUsername(String username);
}
