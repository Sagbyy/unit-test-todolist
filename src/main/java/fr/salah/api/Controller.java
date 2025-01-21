package fr.salah.api;

import fr.salah.entity.Item;
import fr.salah.service.TodoList;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class Controller {
    private final TodoList todoList;

    public Controller(TodoList todoList) {
        this.todoList = todoList;
    }

    @GetMapping
    public String home() {
        return "Welcome to the Todo List API";
    }

    @PostMapping("/item")
    public void addItem(@RequestBody Item item) {
        todoList.addItem(item);
    }

    @GetMapping("/item")
    public List<Item> getItems() {
        return todoList.getItems();
    }
}
