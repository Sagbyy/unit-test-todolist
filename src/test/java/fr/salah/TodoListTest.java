package fr.salah;

import fr.salah.entity.Item;
import fr.salah.exceptions.TodoListException;
import fr.salah.repository.ItemRepository;
import fr.salah.service.TodoList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TodoListTest {

    private TodoList todoList;

    @BeforeEach
    void setUp() {
        EmailSenderService emailSenderService = mock(EmailSenderService.class);
        when(emailSenderService.sendEmail()).thenReturn(true);

        ItemRepository itemRepository = mock(ItemRepository.class);
        when(itemRepository.findAll()).thenReturn(new ArrayList<>());

        this.todoList = spy(new TodoList(itemRepository));

        doNothing().when(todoList).save(any());
    }

    @Test
    void addItem_RightItem() {
        Item item = new Item("name", "content");
        assertDoesNotThrow(() -> this.todoList.addItem(item), "Adding a valid item should not throw an exception.");
    }

    @Test
    void addItem_LessThirtyMinutes() {
        Item item = new Item("name", "content");
        assertDoesNotThrow(() -> this.todoList.addItem(item), "Adding a valid item should not throw an exception.");

        Item item2 = new Item("name2", "content2");
        assertThrows(TodoListException.class, () -> this.todoList.addItem(item2), "Adding an item in less than 30 minutes should throw a TodoListException.");
    }

    @Test
    void addItem_MoreThirtyMinutes() {
        Item item = new Item("name", "content");
        item.setCreationDate(item.getCreationDate().minusMinutes(31));

        assertDoesNotThrow(() -> this.todoList.addItem(item), "Adding a valid item should not throw an exception.");

        Item item2 = new Item("name2", "content2");
        assertDoesNotThrow(() -> this.todoList.addItem(item2), "Adding an item in more than 30 minutes should not throw an exception.");
    }

    @Test
    void addItem_ListFull() {
        this.todoList.setItems(new ArrayList<>(Arrays.asList(
                new Item("name1", "content1"),
                new Item("name2", "content2"),
                new Item("name3", "content3"),
                new Item("name4", "content4"),
                new Item("name5", "content5"),
                new Item("name6", "content6"),
                new Item("name7", "content7"),
                new Item("name8", "content8"),
                new Item("name9", "content9"),
                new Item("name10", "content10")
        )));

        Item item = new Item("name", "content");
        assertThrows(TodoListException.class, () -> this.todoList.addItem(item), "Adding an item to a full list should throw a TodoListException.");
    }

    @Test
    void addItem_ItemAlreadyExists() {
        Item item = new Item("name", "content");
        assertDoesNotThrow(() -> this.todoList.addItem(item), "Adding a valid item should not throw an exception.");

        Item item2 = new Item("name", "content");
        assertThrows(TodoListException.class, () -> this.todoList.addItem(item2), "Adding an item with the same name should throw a TodoListException.");
    }
}
