package pl.sda.democlass.sdademo.todos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import pl.sda.democlass.sdademo.users.User;
import pl.sda.democlass.sdademo.users.UserContextHolder;
import pl.sda.democlass.sdademo.users.UsersRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class TodosService {
    @Autowired
    private UserContextHolder userContextHolder;
    @Autowired
    private UsersRepository userRepository;
    @Autowired
    private TodosRepository todosRepository;


    public void addTodos(String todoValue, String calendarValue) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date parsedTime = format.parse(calendarValue);
        Todo todo = new Todo(todoValue,parsedTime , getCurrentUser().getEmail(), false);
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
        model.addAttribute("todosList", todosRepository.findByCompletedAndUserEmail(false, currentUser.getEmail()));
        model.addAttribute("todosDoneList", todosRepository.findByCompletedAndUserEmail(true, currentUser.getEmail()));
    }

    public User getCurrentUser() {
        String auth = userContextHolder.getUserLoggedIn();
        return userRepository.findUserByEmail(auth).orElse(null);
    }
}
