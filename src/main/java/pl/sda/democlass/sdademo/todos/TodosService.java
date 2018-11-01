package pl.sda.democlass.sdademo.todos;

import org.springframework.ui.Model;

public interface TodosService {

    void addTodos(String name);

    Todo findByName(String name);

    void updateCompleted(String name);

    void deleteTodos(String name);

    void findByCompletedAndUserId(Model model);
}
