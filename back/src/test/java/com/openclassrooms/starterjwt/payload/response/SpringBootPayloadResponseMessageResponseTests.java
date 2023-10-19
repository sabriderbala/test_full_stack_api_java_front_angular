package com.openclassrooms.starterjwt.payload.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SpringBootPayloadResponseMessageResponseTests {

    private MessageResponse messageResponse;

    @BeforeEach
    public void setUp() {
        messageResponse = new MessageResponse("Initial message");
    }

    @Test
    public void testCanCreateMessageResponse() {
        // On vérifie que l'objet n'est pas null, ce qui prouve qu'il a été créé.
        assertNotNull(messageResponse);
    }

    @Test
    public void testGetMessage() {
        // On teste le getter pour s'assurer qu'il renvoie la bonne valeur initiale.
        assertEquals("Initial message", messageResponse.getMessage());
    }

    @Test
    public void testSetMessage() {
        // On teste le setter pour vérifier s'il met bien à jour la valeur comme prévu.
        messageResponse.setMessage("New message");

        // Puis on utilise le getter pour confirmer que la mise à jour a eu lieu.
        assertEquals("New message", messageResponse.getMessage());
    }
}
