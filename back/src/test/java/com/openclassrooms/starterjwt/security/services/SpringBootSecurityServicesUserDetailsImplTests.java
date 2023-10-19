package com.openclassrooms.starterjwt.security.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class SpringBootSecurityServicesUserDetailsImplTests {

    private UserDetailsImpl userDetails;

    @BeforeEach
    public void setUp() {
        userDetails = createUserDetails(1L, "testUser", "Test", "User", true, "password");
    }

    @Test
    public void testGetAuthorities() {
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        assertNotNull(authorities);
        assertTrue(authorities.isEmpty());
    }

    @Test
    public void testAccountStatus() {
        assertTrue(userDetails.isAccountNonExpired());
        assertTrue(userDetails.isAccountNonLocked());
        assertTrue(userDetails.isCredentialsNonExpired());
        assertTrue(userDetails.isEnabled());
    }

    @Test
    public void testEquals() {
        UserDetailsImpl sameUserDetails = createUserDetails(1L, "testUser", "Test", "User", true, "password");
        UserDetailsImpl differentUserDetails = createUserDetails(2L, "testUser2", "Test", "User", true, "password");

        assertEquals(userDetails, sameUserDetails);
        assertNotEquals(userDetails, differentUserDetails);
        assertNotEquals(userDetails, new Object());
    }

    @Test
    public void testUserDetailsGetters() {
        assertUserDetails(userDetails, 1L, "testUser", "Test", "User", true, "password");
    }

    @Test
    public void testBuilderToString() {
        UserDetailsImpl.UserDetailsImplBuilder builder = UserDetailsImpl.builder()
                .id(1L)
                .username("testUser")
                .firstName("Test")
                .lastName("User")
                .admin(true)
                .password("password");

        String expectedString = "UserDetailsImpl.UserDetailsImplBuilder(id=1, username=testUser, firstName=Test, lastName=User, admin=true, password=password)";
        assertEquals(expectedString, builder.toString());
    }

    private UserDetailsImpl createUserDetails(Long id, String username, String firstName, String lastName, boolean isAdmin, String password) {
        return UserDetailsImpl.builder()
                .id(id)
                .username(username)
                .firstName(firstName)
                .lastName(lastName)
                .admin(isAdmin)
                .password(password)
                .build();
    }

    private void assertUserDetails(UserDetailsImpl userDetails, Long expectedId, String expectedUsername, String expectedFirstName, String expectedLastName, boolean expectedAdmin, String expectedPassword) {
        assertEquals(expectedId, userDetails.getId());
        assertEquals(expectedUsername, userDetails.getUsername());
        assertEquals(expectedFirstName, userDetails.getFirstName());
        assertEquals(expectedLastName, userDetails.getLastName());
        assertTrue(expectedAdmin == userDetails.getAdmin());
        assertEquals(expectedPassword, userDetails.getPassword());
    }
}
