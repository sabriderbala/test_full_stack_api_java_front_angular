package com.openclassrooms.starterjwt.payload.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpringBootPayloadResponseJwtResponseTests {

    private JwtResponse jwtResponse;
    private final String token = "Bearer token";
    private final Long id = 1L;
    private final String username = "testUser";
    private final String firstName = "Test";
    private final String lastName = "User";
    private final Boolean admin = true;

    @BeforeEach
    void setUp() {
        jwtResponse = new JwtResponse(token, id, username, firstName, lastName, admin);
    }

    @Test
    void getToken() {
        assertEquals(token, jwtResponse.getToken());
    }

    @Test
    void getType() {
        assertEquals("Bearer", jwtResponse.getType());
    }

    @Test
    void getId() {
        assertEquals(id, jwtResponse.getId());
    }

    @Test
    void getUsername() {
        assertEquals(username, jwtResponse.getUsername());
    }

    @Test
    void getFirstName() {
        assertEquals(firstName, jwtResponse.getFirstName());
    }

    @Test
    void getLastName() {
        assertEquals(lastName, jwtResponse.getLastName());
    }

    @Test
    void isAdmin() {
        assertEquals(admin, jwtResponse.getAdmin());
    }

    @Test
    void testSetters() {
        String newToken = "newToken";
        jwtResponse.setToken(newToken);
        assertEquals(newToken, jwtResponse.getToken());

        Long newId = 2L;
        jwtResponse.setId(newId);
        assertEquals(newId, jwtResponse.getId());

        String newUsername = "newUsername";
        jwtResponse.setUsername(newUsername);
        assertEquals(newUsername, jwtResponse.getUsername());

        String newFirstName = "NewFirst";
        jwtResponse.setFirstName(newFirstName);
        assertEquals(newFirstName, jwtResponse.getFirstName());

        String newLastName = "NewLast";
        jwtResponse.setLastName(newLastName);
        assertEquals(newLastName, jwtResponse.getLastName());

        Boolean newAdmin = false;
        jwtResponse.setAdmin(newAdmin);
        assertEquals(newAdmin, jwtResponse.getAdmin());
    }
}
