package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.response.JwtResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:reset_db.sql")
public class SpringBootControllersSessionControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;
    private HttpHeaders headers;

    @BeforeEach
    void before_all() {
        headers = new HttpHeaders();
        headers.set("Authorization", obtainJwtToken("yoga@studio.com", "test!1234"));
    }

    private String obtainJwtToken(String email, String password) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);

        ResponseEntity<JwtResponse> loginResponse = restTemplate.postForEntity("/api/auth/login", loginRequest, JwtResponse.class);
        return "Bearer " + loginResponse.getBody().getToken();
    }

    @Test
    void shouldFindById() {
        ResponseEntity<SessionDto> response = getSessionResponseEntity(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void shouldFindByAllSessions() {
        ResponseEntity<List> response = restTemplate.exchange("/api/session", HttpMethod.GET, new HttpEntity<>(headers), List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void shouldCreate() {
        SessionDto newSession = createSampleSession();

        ResponseEntity<SessionDto> response = restTemplate.postForEntity("/api/session", new HttpEntity<>(newSession, headers), SessionDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
    }

    @Test
    void shouldUpdate() {
        Long sessionId = 1L;
        SessionDto updatedSession = createSampleSession();
        updatedSession.setId(sessionId);

        restTemplate.put("/api/session/" + sessionId, new HttpEntity<>(updatedSession, headers), SessionDto.class);
        ResponseEntity<SessionDto> response = getSessionResponseEntity(sessionId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedSession.getName(), response.getBody().getName());
    }

    private ResponseEntity<SessionDto> getSessionResponseEntity(Long sessionId) {
        return restTemplate.exchange("/api/session/" + sessionId, HttpMethod.GET, new HttpEntity<>(headers), SessionDto.class);
    }

    private SessionDto createSampleSession() {
        SessionDto sessionDto = new SessionDto();
        sessionDto.setName("Test Session " + UUID.randomUUID());
        sessionDto.setDescription("Test Session Description");
        sessionDto.setTeacher_id(1L);
        sessionDto.setDate(new Date());
        return sessionDto;
    }
}
