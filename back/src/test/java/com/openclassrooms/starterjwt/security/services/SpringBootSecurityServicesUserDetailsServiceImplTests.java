package com.openclassrooms.starterjwt.security.services;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // Set up a Mockito JUnit 5 environment
public class SpringBootSecurityServicesUserDetailsServiceImplTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks // Automatically inject the mock objects
    private UserDetailsServiceImpl userDetailsService;

    private User testUser;

    @BeforeEach
    public void setUp() {
        // Set up the test user
        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@example.com");
        testUser.setFirstName("Test");
        testUser.setLastName("User");
        testUser.setPassword("password");
    }

    @Test
    public void loadUserByUsername_UserExists_ShouldReturnUserDetails() {
        // Mock the interaction with the database
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(testUser));

        // Call the method under test
        UserDetails userDetails = userDetailsService.loadUserByUsername(testUser.getEmail());

        // Perform assertions
        assertNotNull(userDetails, "UserDetails should not be null");
        assertEquals(testUser.getEmail(), userDetails.getUsername(), "Email should match");
        assertEquals(testUser.getPassword(), userDetails.getPassword(), "Password should match");
    }

    @Test
    public void loadUserByUsername_UserNotFound_ShouldThrowUsernameNotFoundException() {
        // Mock the interaction with the database to simulate user not found
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // Assert that an exception is thrown when a user is not found
        Exception exception = assertThrows(
                UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername("unknown@example.com")
        );

        // Assert the exception message is as expected
        assertTrue(exception.getMessage().contains("User Not Found with email: unknown@example.com"));
    }
}
