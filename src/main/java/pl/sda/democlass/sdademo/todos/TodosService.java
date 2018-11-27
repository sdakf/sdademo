package pl.sda.democlass.sdademo.todos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import pl.sda.democlass.sdademo.users.User;
import pl.sda.democlass.sdademo.users.UserContextHolder;
import pl.sda.democlass.sdademo.users.UsersRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TodosService {
    @Autowired
    private UserContextHolder userContextHolder;
    @Autowired
    private UsersRepository userRepository;
    @Autowired
    private TodosRepository todosRepository;


    public void addTodos(String todoValue, String calendarValue) throws ParseException {
        if (todosRepository.findByName(todoValue, getCurrentUser().getEmail()) != null) {
            throw new RuntimeException("TODO istnieje");
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedTime = format.parse(calendarValue);
        Todo todo = new Todo(todoValue, parsedTime, getCurrentUser().getEmail(), false);
        todosRepository.save(todo);
    }

    public Todo findByName(String name) {
        return todosRepository.findByName(name, getCurrentUser().getEmail());
    }

    public void updateCompleted(String name) {
        Todo todos = findByName(name);
        todos.setCompleted(true);
        todosRepository.save(todos);
    }

    public void deleteTodos(String name) {
        Todo todos = findByName(name);
        todosRepository.delete(todos);
    }

    public void findByCompletedAndUserId(Model model) {
        User currentUser = getCurrentUser();
        List<Todo> allTodos = todosRepository.findAll();
        //todo 13 - należy uzupełnić dla aktualnego użytkownika listy zadań do zrobienia oraz zrobionych
        List<Todo> doneList = new ArrayList<>();
        List<Todo> todoList = new ArrayList<>();
        for (Todo todo : allTodos) {
            if (!todo.getUserEmail().equalsIgnoreCase(currentUser.getEmail())) {
                continue;
            }
            if (todo.isCompleted()) {
                doneList.add(todo);
            } else {
                todoList.add(todo);
            }
        }
        model.addAttribute("todosList", todoList);
        model.addAttribute("todosDoneList", doneList);
    }

    public User getCurrentUser() {
        String auth = userContextHolder.getUserLoggedIn();
        return userRepository.findUserByEmail(auth).orElse(null);
    }
}
