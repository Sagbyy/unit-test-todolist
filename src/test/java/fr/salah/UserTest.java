package fr.salah;

import fr.salah.exceptions.UserNotValidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private User user;

    @BeforeEach
    void setup() {
        user = new User("salah@myges.fr", "Salah", "Eddine", LocalDate.parse("2009-10-22"), "Password123");
    }

    @Test
    void isValid_RightUser() {
        assertTrue(user.isValid(), "Right user");
    }

    @Test
    void isValidEmail() {
        assertTrue(user.isValidEmail(), "Right email");

        user.setEmail("salah");
        assertFalse(user.isValidEmail(), "Without @");

        user.setEmail("salah@");
        assertFalse(user.isValidEmail(), "Without domain");

        user.setEmail("salah@myges");
        assertFalse(user.isValidEmail(), "Without extension");

        user.setEmail("salah@myges.");
        assertFalse(user.isValidEmail(), "Without extension name");
    }

    @Test
    void isValidFullName() {
        assertTrue(user.isValidFullname(), "Right fullname");

        user.setFirstName(null);
        user.setLastName(null);
        assertFalse(user.isValidFullname(), "Null fullname");
    }

    @Test
    void isValidBirthdate() {
        assertTrue(user.isValidBirthDate(), "Right birthdate");

        user.setBirthDate(LocalDate.parse("2015-10-22"));
        assertFalse(user.isValidBirthDate(), "Age < 13");

        user.setBirthDate(LocalDate.parse("1900-10-22"));
        assertFalse(user.isValidBirthDate(), "Age > 120");

        user.setBirthDate(null);
        assertFalse(user.isValidBirthDate(), "Null birthdate");

        user.setBirthDate(LocalDate.now());
        assertFalse(user.isValidBirthDate(), "Age = 0");

        user.setBirthDate(LocalDate.now().minusYears(121));
        assertFalse(user.isValidBirthDate(), "Age > 120");
    }

    @Test
    void isValidPassword() {
        assertTrue(user.isValidPassword(), "Right password");

        user.setPassword("password123");
        assertFalse(user.isValidPassword(), "Without uppercase");

        user.setPassword("PASSWORD123");
        assertFalse(user.isValidPassword(), "Without lowercase");

        user.setPassword("Password");
        assertFalse(user.isValidPassword(), "Without number");

        user.setPassword("Password1234567890123456789012345678901234567890");
        assertFalse(user.isValidPassword(), "Length > 40");

        user.setPassword("Pass123");
        assertFalse(user.isValidPassword(), "Length < 8");
    }

    @Test
    void createTodoList_ValidUser() {
        user.createTodoList();
        assertNotNull(user.createTodoList(), "TodoList created");

        user.setEmail("salah");
        assertThrows(UserNotValidException.class, () -> user.createTodoList(), "UserNotValid exception");
    }
}
