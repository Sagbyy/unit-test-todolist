package fr.salah;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    private Item item;

    @BeforeEach
    void setUp() {
        this.item = new Item("name", "content");
    }

    @Test
    void nameIsValid() {
        assertTrue(this.item.nameIsValid(), "Right name");

        this.item.setName(null);
        assertFalse(this.item.nameIsValid(), "Null name");
    }

    @Test
    void contentIsValid() {
        assertTrue(this.item.contentIsValid(), "Right content");

        this.item.setContent(null);
        assertFalse(this.item.contentIsValid(), "Null content");

        this.item.setContent("");
        assertFalse(this.item.contentIsValid(), "Empty content");

        this.item.setContent("a".repeat(1001));
        assertFalse(this.item.contentIsValid(), "Content too long");
    }

    @Test
    void isValid() {
        assertTrue(this.item.isValid(), "Right item");

        this.item.setName(null);
        assertFalse(this.item.isValid(), "Null name");

        this.item.setName("name");
        this.item.setContent(null);
        assertFalse(this.item.isValid(), "Null content");

        this.item.setContent("content");
        assertTrue(this.item.isValid(), "Right item");
    }
}
