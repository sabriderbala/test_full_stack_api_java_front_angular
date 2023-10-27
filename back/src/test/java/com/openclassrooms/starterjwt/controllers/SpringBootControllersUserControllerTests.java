package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.response.JwtResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:reset_db.sql")
class UserControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;
    private HttpHeaders authorizationHeaders;

    @BeforeEach
    void before_all() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("john@test.com");
        loginRequest.setPassword("test!1234");
        ResponseEntity<JwtResponse> loginResponse = restTemplate.postForEntity("/api/auth/login", loginRequest, JwtResponse.class);
        String jwtToken = "Bearer " + loginResponse.getBody().getToken();
        authorizationHeaders = new HttpHeaders();
        authorizationHeaders.set("Authorization", jwtToken);
    }

    @Test
    void shouldGetUserById () {
        HttpEntity<String> entity = new HttpEntity<>(authorizationHeaders);
        ResponseEntity<UserDto> response = restTemplate.exchange("/api/user/2", HttpMethod.GET, entity, UserDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("john@test.com", response.getBody().getEmail());
    }

    @Test
    void shouldDeleteUser() {
        HttpEntity<String> entity = new HttpEntity<>(authorizationHeaders);
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/api/user/2", HttpMethod.DELETE, entity, Void.class);

        assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());
    }
}
