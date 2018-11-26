package pl.sda.democlass.sdademo.todos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TodosRepository extends JpaRepository<Todo,Long> {

    List<Todo> findByCompletedAndUserEmail(boolean completed, String userEmail);

    @Query("select t from Todo t where t.todoValue = ?1 and t.userEmail = ?2")
    Todo findByName(String name, String userEmail);
}
