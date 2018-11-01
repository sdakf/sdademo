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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "userType", discriminatorType = DiscriminatorType.STRING)
public class User extends BaseEntity {
    private String firstName;
    private String surName;
    private String username;
    private String pesel;
    private String passwordHash;
    private UserAddress userAddress;
    private String birthDate;

    @OneToMany(mappedBy = "todoUser")
    private Set<Todo> todos;

    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinTable(name = "user_role")
    private Set<Role> roles;
}
