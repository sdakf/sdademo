package pl.sda.democlass.sdademo.todos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import pl.sda.democlass.sdademo.users.User;
import pl.sda.democlass.sdademo.users.UserContextHolder;
import pl.sda.democlass.sdademo.users.UsersRepository;

@Service
public class TodosServiceImpl implements TodosService {

    private final UserContextHolder userContextHolder;

    private final UsersRepository userRepository;

    private final TodosRepository todosRepository;

    @Autowired
    public TodosServiceImpl(UserContextHolder userContextHolder, UsersRepository userRepository, TodosRepository todosRepository) {
        this.userContextHolder = userContextHolder;
        this.userRepository = userRepository;
        this.todosRepository = todosRepository;
    }

    @Override
    public void addTodos(String todoValue) {
        Todo todo = new Todo(todoValue, getCurrentUser(), false);
        todosRepository.save(todo);
    }

    @Override
    public Todo findByName(String name) {
        return todosRepository.findByName(name,getCurrentUser().getId());
    }

    @Override
    public void updateCompleted(String name) {
        Todo todos = findByName(name);
        todos.setCompleted(true);
        todosRepository.save(todos);
    }

    @Override
    public void deleteTodos(String name) {
        Todo todos = findByName(name);
        todosRepository.delete(todos);
    }

    @Override
    public void findByCompletedAndUserId(Model model) {
        User currentUser = getCurrentUser();
        model.addAttribute("todosList", todosRepository.findByCompletedAndTodoUserId(false, currentUser.getId()));
        model.addAttribute("todosDoneList", todosRepository.findByCompletedAndTodoUserId(true, currentUser.getId()));
    }

    public User getCurrentUser() {
        String auth = userContextHolder.getUserLoggedIn();
        return userRepository.findUserByUsername(auth).orElse(null);
    }

}
