package pl.sda.democlass.sdademo.users;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import pl.sda.democlass.sdademo.BaseEntity;
import pl.sda.democlass.sdademo.todos.Todo;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class User extends BaseEntity {
    //todo 1 - uzupełnić pola użytkownika

    private UserAddress userAddress;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "user_role")
    private Set<Role> roles;
}
