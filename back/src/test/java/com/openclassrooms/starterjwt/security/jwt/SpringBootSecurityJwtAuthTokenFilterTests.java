package com.openclassrooms.starterjwt.security.jwt;

import com.openclassrooms.starterjwt.security.services.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SpringBootSecurityJwtAuthTokenFilterTests {

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @InjectMocks
    private AuthTokenFilter authTokenFilter;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    private String sampleToken;
    private String userName;

    @BeforeEach
    public void setUp() {
        sampleToken = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
        userName = "test";

        // Reset the context for each test
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @Test
    public void parseJwt_NoAuthorizationHeader_ReturnsNull() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(null);

        authTokenFilter.doFilterInternal(request, response, filterChain);

        // Verify the method didn't set any authentication because it didn't find a token
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    public void parseJwt_InvalidAuthorizationHeader_ReturnsNull() throws ServletException, IOException {
        // This string is not a valid bearer token
        when(request.getHeader("Authorization")).thenReturn("Invalid token");

        authTokenFilter.doFilterInternal(request, response, filterChain);

        // Verify the method didn't set any authentication because it couldn't parse a token
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    public void doFilterInternal_ExceptionThrown_DoesNotSetAuthentication() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(sampleToken);
        when(jwtUtils.validateJwtToken(anyString())).thenReturn(true);
        when(jwtUtils.getUserNameFromJwtToken(anyString())).thenThrow(RuntimeException.class);

        authTokenFilter.doFilterInternal(request, response, filterChain);

        // Verify that the filter chain progresses even when an exception occurs
        verify(filterChain).doFilter(request, response);

        // Assert that no authentication object was set in the context due to the exception
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    public void doFilterInternal_JwtTokenInvalid_DoesNotSetAuthentication() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn("Bearer InvalidToken");
        // Simulate the token being invalid
        when(jwtUtils.validateJwtToken("InvalidToken")).thenReturn(false);

        authTokenFilter.doFilterInternal(request, response, filterChain);

        // Verify the method didn't set any authentication because the token was invalid
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    public void doFilterInternal_ValidJwtToken_SetsAuthentication() throws ServletException, IOException {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn(sampleToken);
        when(jwtUtils.validateJwtToken(anyString())).thenReturn(true);
        when(jwtUtils.getUserNameFromJwtToken(anyString())).thenReturn(userName);

        UserDetails userDetails = new User(userName, "", Collections.emptyList());
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(userDetails);

        // Act
        authTokenFilter.doFilterInternal(request, response, filterChain);

        // Assert
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals(userName, SecurityContextHolder.getContext().getAuthentication().getName());
        verify(filterChain).doFilter(request, response);  // Ensures filter chain proceeded
    }

    @Test
    public void doFilterInternal_ValidationException_DoesNotSetAuthentication() throws ServletException, IOException {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn(sampleToken);
        when(jwtUtils.validateJwtToken(anyString())).thenThrow(new RuntimeException("Token validation error"));

        // Act
        authTokenFilter.doFilterInternal(request, response, filterChain);

        // Assert
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);  // Ensures filter chain proceeded
    }

    @Test
    public void doFilterInternal_NoBearerToken_DoesNotSetAuthentication() throws ServletException, IOException {
        // Arrange: The Authorization header doesn't start with "Bearer "
        when(request.getHeader("Authorization")).thenReturn("Basic some_base64_encoded_credentials");

        // Act
        authTokenFilter.doFilterInternal(request, response, filterChain);

        // Assert
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);  // Ensures filter chain proceeded
    }



}
