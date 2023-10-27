package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.response.JwtResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:reset_db.sql")
class SpringBootControllersTeacherControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;
    private HttpHeaders authorizedHeaders;

    @BeforeEach
    void before_all() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("yoga@studio.com");
        loginRequest.setPassword("test!1234");
        ResponseEntity<JwtResponse> loginResponse = restTemplate.postForEntity("/api/auth/login", loginRequest, JwtResponse.class);
        String jwtToken = "Bearer " + loginResponse.getBody().getToken();
        authorizedHeaders = new HttpHeaders();
        authorizedHeaders.set("Authorization", jwtToken);
    }

    @Test
    void shouldFindTeacherWithId() {
        HttpEntity<String> entity = new HttpEntity<>(authorizedHeaders);
        ResponseEntity<TeacherDto> responseEntity = restTemplate.exchange("/api/teacher/1", HttpMethod.GET, entity, TeacherDto.class);
        assertAll(
            () -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode()),
            () -> assertEquals("Margot", responseEntity.getBody().getFirstName())
        );
    }

    @Test
    void shouldFindAllTeachers() {
        HttpEntity<String> entity = new HttpEntity<>(authorizedHeaders);
        ResponseEntity<TeacherDto[]> responseEntity = restTemplate.exchange("/api/teacher", HttpMethod.GET, entity, TeacherDto[].class);
        assertAll(
            () -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode()),
            () -> assertEquals(2, responseEntity.getBody().length)
        );
    }
}
