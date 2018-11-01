package pl.sda.democlass.sdademo.users;

import lombok.Getter;
import lombok.Setter;
import pl.sda.democlass.sdademo.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Role extends BaseEntity {

    private String roleName;
}