package fr.salah;

import fr.salah.exceptions.TodoListException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TodoList {
    private List<Item> items;
    private EmailSenderService emailSenderService;

    public TodoList(EmailSenderService emailSenderService) {
        this.items = new ArrayList<>();
        this.emailSenderService = emailSenderService;
    }

    public void addItem(Item item) throws TodoListException {
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
        this.save(item);

        if (items.size() == 8) {
            this.emailSenderService.sendEmail();
        }
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
