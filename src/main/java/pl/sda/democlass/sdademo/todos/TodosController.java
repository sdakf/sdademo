package pl.sda.democlass.sdademo.todos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

@Controller
@RequestMapping("/todos")
public class TodosController {

    @Autowired
    private TodosService todosService;

    @RequestMapping(method = RequestMethod.GET)
    public String todos(Model model) {
        todosService.findByCompletedAndUserId(model);
        return "todos";
    }

    @ResponseBody
    @RequestMapping(value = "/{todoValue}/{calendar}", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> addTodos(@PathVariable("todoValue") String todoValue,@PathVariable String calendar) throws ParseException {
        todosService.addTodos(todoValue,calendar);
        return ResponseEntity.ok("OK");
    }

    @ResponseBody
    @RequestMapping(value = "/{todoValue}", method = RequestMethod.PUT, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> updateCompleted(@PathVariable("todoValue") String todoValue) {
        todosService.updateCompleted(todoValue);
        return ResponseEntity.ok("OK");
    }

    @ResponseBody
    @RequestMapping(value = "/{todoValue}", method = RequestMethod.DELETE, produces = MediaType.TEXT_PLAIN_VALUE)
    public  ResponseEntity<String>  deleteTodos(@PathVariable("todoValue") String todoValue) {
        todosService.deleteTodos(todoValue);
        return ResponseEntity.ok("OK");
    }

}
