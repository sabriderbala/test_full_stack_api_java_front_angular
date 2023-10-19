package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class SpringBootModelsUserModelTests {
    private User user;
    private LocalDateTime testTime;

    @BeforeEach
    public void setUp() {
        user = new User();
        testTime = LocalDateTime.now();
    }

    @Test
    public void testUserConstructorsAndCommonMethods() {
        assertNotNull(user);

        User allArgsConstructorUser = new User(1L, "test@example.com", "Doe", "John", "password", true, testTime, testTime);
        User builderUser = User.builder()
                .id(1L)
                .email("builder@example.com")
                .lastName("Builder")
                .firstName("User")
                .password("buildpass")
                .admin(true)
                .createdAt(testTime)
                .updatedAt(testTime)
                .build();

        User userForEquals = new User("equal@example.com", "Equal", "Test", "equalpass", true);
        User sameAsUserForEquals = new User("equal@example.com", "Equal", "Test", "equalpass", true);

        assertEquals(userForEquals, sameAsUserForEquals);

        int expectedHashCode = userForEquals.hashCode();
        assertEquals(expectedHashCode, sameAsUserForEquals.hashCode());

        String expectedString = String.format(
                "User(id=%s, email=%s, lastName=%s, firstName=%s, password=%s, admin=%s, createdAt=%s, updatedAt=%s)",
                allArgsConstructorUser.getId(), allArgsConstructorUser.getEmail(), allArgsConstructorUser.getLastName(),
                allArgsConstructorUser.getFirstName(), allArgsConstructorUser.getPassword(), allArgsConstructorUser.isAdmin(),
                allArgsConstructorUser.getCreatedAt(), allArgsConstructorUser.getUpdatedAt());
        assertEquals(expectedString, allArgsConstructorUser.toString());

        assertFalse(user.equals("a string"));
        assertNotEquals(user, null);

        User user1 = new User("example@example.com", "Test", "User", "pass", true);
        assertEquals(user1, user1);

        User emptyUser = new User();
        emptyUser.setEmail("");
        assertEquals("", emptyUser.getEmail());

        assertThrows(NullPointerException.class, () -> emptyUser.setEmail(null));
    }

    @Test
    public void testUserSettersAndGetters() {
        user.setId(2L);
        user.setEmail("testset@example.com");
        user.setLastName("Setter");
        user.setFirstName("Test");
        user.setPassword("setpass");
        user.setAdmin(false);
        user.setCreatedAt(testTime);
        user.setUpdatedAt(testTime);

        assertEquals(2L, user.getId());
        assertEquals("testset@example.com", user.getEmail());
        assertEquals("Setter", user.getLastName());
        assertEquals("Test", user.getFirstName());
        assertEquals("setpass", user.getPassword());
        assertFalse(user.isAdmin());
        assertEquals(testTime, user.getCreatedAt());
        assertEquals(testTime, user.getUpdatedAt());
    }

    @Test
    public void testInvalidInputs() {
        assertThrows(NullPointerException.class, () -> new User(null, null, null, null, false));

        User newUser = new User();
        newUser.setLastName("");
        assertEquals("", newUser.getLastName());

        assertThrows(NullPointerException.class, () -> newUser.setFirstName(null));

        newUser.setPassword("");
        assertEquals("", newUser.getPassword());
    }

    @Test
    public void testUserBuilderToString() {
        LocalDateTime now = LocalDateTime.now();
        User.UserBuilder builder = User.builder()
                .id(1L)
                .email("builder@example.com")
                .lastName("Builder")
                .firstName("User")
                .password("buildpass")
                .admin(true)
                .createdAt(now)
                .updatedAt(now);

        String expectedString = String.format(
                "User.UserBuilder(id=%s, email=%s, lastName=%s, firstName=%s, password=%s, admin=%s, createdAt=%s, updatedAt=%s)",
                1L, "builder@example.com", "Builder", "User", "buildpass", true, now, now);

        assertEquals(expectedString, builder.toString());
    }
}
