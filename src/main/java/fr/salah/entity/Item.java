package fr.salah.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String content;
    private LocalDateTime creationDate;

    protected Item() { }

    public Item(String name, String content) {
        this.name = name;
        this.content = content;
        this.creationDate = LocalDateTime.now();
    }

    public boolean nameIsValid() {
        return name != null && !name.isEmpty();
    }

    public boolean contentIsValid() {
        return content != null && !content.isEmpty() && content.length() <= 1000;
    }

    public boolean isValid() {
        return this.nameIsValid() && this.contentIsValid();
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
