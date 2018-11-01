package pl.sda.democlass.sdademo.todos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.sda.democlass.sdademo.BaseEntity;
import pl.sda.democlass.sdademo.users.User;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Todo extends BaseEntity {

    @Column(unique = true)
    private String todoValue;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User todoUser;

    private boolean completed;

}
