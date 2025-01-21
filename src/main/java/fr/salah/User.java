package fr.salah;

import fr.salah.entity.Item;
import fr.salah.exceptions.UserNotValidException;
import fr.salah.repository.ItemRepository;
import fr.salah.service.TodoList;

import java.time.LocalDate;
import java.time.Period;

public class User {
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String password;
    private TodoList todoList;
    private final ItemRepository itemRepository;

    public User(String email, String firstName, String lastName, LocalDate birthDate, String password, ItemRepository itemRepository) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.password = password;
        this.itemRepository = itemRepository;
    }

    public User(String email, String firstName, String lastName, LocalDate birthDate, String password) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.password = password;
        this.itemRepository = null;
    }

    public boolean isValidEmail() {
        return email != null && email.contains("@") && email.contains(".") && email.length() > 5 && email.matches(".*\\.[a-z]+");
    }

    public boolean isValidFullname() {
        return firstName != null && lastName != null;
    }

    public boolean isValidBirthDate() {
        if (birthDate == null) {
            return false;
        }

        LocalDate currentDate = LocalDate.now();
        int age = Period.between(birthDate, currentDate).plusYears(1).getYears();

        return age >= 13 && age <= 120;
    }

    public boolean isValidPassword() {
        return password != null && password.length() >= 8 && password.length() <= 40 && password.matches(".*[A-Z].*") && password.matches(".*[a-z].*") && password.matches(".*[0-9].*");
    }

    public boolean isValid() {
        return isValidEmail() && isValidFullname() && isValidBirthDate() && isValidPassword();
    }

    public TodoList createTodoList() throws UserNotValidException {
        if (this.isValid()) {
//            this.todoList = new TodoList(new EmailSenderService());
            this.todoList = new TodoList(itemRepository);
            return this.todoList;
        } else throw new UserNotValidException();
    }

    public Item addItem(String name, String content) throws UserNotValidException {
        if (!this.isValid()) throw new UserNotValidException();

        Item item = new Item(name, content);
        this.todoList.addItem(item);

        return item;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
