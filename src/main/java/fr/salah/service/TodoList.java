package fr.salah.service;

import fr.salah.entity.Item;
import fr.salah.exceptions.TodoListException;
import fr.salah.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TodoList {
    private List<Item> items;
    private final ItemRepository itemRepository;

    public TodoList(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
        this.items = new ArrayList<>();
    }

    public void addItem(Item item) throws TodoListException {
        System.out.println("Adding item " + item.getName());
        if (item == null) {
            throw new TodoListException("Item is null, please provide a valid item like this : new Item(\"name\", \"content\")");
        }

        if (this.items.size() >= 10) {
            throw new TodoListException("Todo list is full");
        }

        if (this.items.stream().anyMatch(i -> i.getName().equals(item.getName()))) {
            throw new TodoListException("Item already exists");
        }

        if (!this.items.isEmpty()) {
            LocalDateTime lastItemCreationDate = this.items.getLast().getCreationDate();
            long minutesSinceLastItem = Duration.between(lastItemCreationDate, LocalDateTime.now()).toMinutes();

            if (minutesSinceLastItem < 30) {
                throw new TodoListException("You can't add more than one item in 30 minutes");
            }
        }

        items.add(item);
        itemRepository.save(item);
//        this.save(item);

//        if (items.size() == 8) {
//            this.emailSenderService.sendEmail();
//        }
    }

    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    public void save(Item item) throws TodoListException {
        // Save the todo list
        items.add(item);
        throw new TodoListException("Error while saving the todo list");
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
