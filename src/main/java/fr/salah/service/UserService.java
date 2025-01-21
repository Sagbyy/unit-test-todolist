package fr.salah.service;

import fr.salah.User;
import fr.salah.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserService {

    private final ItemRepository itemRepository;

    public UserService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public User createUser(String email, String firstName, String lastName, LocalDate birthDate, String password) {
        return new User(email, firstName, lastName, birthDate, password, itemRepository);
    }
}
