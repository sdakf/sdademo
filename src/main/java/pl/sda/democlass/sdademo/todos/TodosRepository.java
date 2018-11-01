package pl.sda.democlass.sdademo.todos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TodosRepository extends JpaRepository<Todo,Long> {

    List<Todo> findByCompletedAndTodoUserId(boolean completed, Long Id);

    @Query("select t from Todo t join User u on t.todoUser = u where t.todoValue = ?1 and u.id = ?2")
    Todo findByName(String name, Long currentUserId);
}
