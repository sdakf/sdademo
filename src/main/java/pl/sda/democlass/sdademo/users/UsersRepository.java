package pl.sda.democlass.sdademo.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {

    @Query("") //todo 10 - należy napisać zapytanie sqlowe
    Optional<User> findUserByEmail(String email);
}
