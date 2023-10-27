package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import com.openclassrooms.starterjwt.payload.response.JwtResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:reset_db.sql")
public class SpringBootControllersAuthControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    private SignupRequest signupRequest;
    private LoginRequest loginRequest;

    @BeforeEach
    public void before_all() {
        signupRequest = new SignupRequest();
        signupRequest.setEmail("test@example.com");
        signupRequest.setPassword("password");
        signupRequest.setFirstName("FirstName");
        signupRequest.setLastName("LastName");

        loginRequest = new LoginRequest();
        loginRequest.setEmail("yoga@studio.com");
        loginRequest.setPassword("test!1234");
    }

    @Test
    public void shouldRegisterUser() {
        ResponseEntity<String> response = restTemplate.postForEntity("/api/auth/register", signupRequest, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldLoginUser() {
        ResponseEntity<JwtResponse> response = restTemplate.postForEntity("/api/auth/login", loginRequest, JwtResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        JwtResponse jwtResponse = response.getBody();
        assertThat(jwtResponse).isNotNull();
        assertThat(jwtResponse.getUsername()).isEqualTo(loginRequest.getEmail());
    }
}
