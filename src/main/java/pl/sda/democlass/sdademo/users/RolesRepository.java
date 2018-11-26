package pl.sda.democlass.sdademo.users;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Role,Long> {

    Role findByRoleName(String roleName);
}
