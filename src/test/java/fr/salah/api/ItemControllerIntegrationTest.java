package fr.salah.api;

import fr.salah.entity.Item;
import fr.salah.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ItemRepository itemRepository;

    @BeforeEach
    public void setUp() {
        itemRepository.deleteAll();
    }

    @Test
    public void testAddItem() {
        String jsonPayload = "{ \"name\": \"Mon item\", \"content\": \"Mon contenu\" }";

        webTestClient.post()
                .uri("/api/item")
                .header("Content-Type", "application/json")
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK);

        Item item = itemRepository.findAll().getFirst();
        assertNotNull(item);
        assertEquals("Mon item", item.getName());
        assertEquals("Mon contenu", item.getContent());
    }
}
