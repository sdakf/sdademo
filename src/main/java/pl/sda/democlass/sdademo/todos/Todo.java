package pl.sda.democlass.sdademo.todos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.sda.democlass.sdademo.BaseEntity;
import pl.sda.democlass.sdademo.users.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Todo extends BaseEntity {

    @Column(unique = true)
    private String todoValue;

    @Temporal(TemporalType.TIMESTAMP)
    private Date deadline;

    private String userEmail;

    private boolean completed;
}
