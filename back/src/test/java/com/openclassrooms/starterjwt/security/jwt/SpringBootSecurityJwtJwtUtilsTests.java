package com.openclassrooms.starterjwt.security.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;

@SpringBootTest
public class SpringBootSecurityJwtJwtUtilsTests {

  @Autowired
  private JwtUtils jwtUtils;

  @MockBean
  private Logger logger;

  private Authentication authentication;

  @BeforeEach
  public void setup() {
    UserDetailsImpl userDetails = new UserDetailsImpl(1L, "test@email.com", "John", "Doe", false, "password");
    authentication = new UsernamePasswordAuthenticationToken(userDetails, null, new ArrayList<>());
  }

  @Test
  public void testGenerateJwtToken() {
    String jwt = jwtUtils.generateJwtToken(authentication);

    assertThat(jwt).isNotNull();
    assertFalse(jwt.isEmpty());
  }

  @Test
  public void testGetUserNameFromJwtToken() {
    String jwt = jwtUtils.generateJwtToken(authentication);

    String username = jwtUtils.getUserNameFromJwtToken(jwt);

    assertThat(username).isNotNull();
    assertThat(username).isEqualTo(authentication.getName());
  }

  @Test
  public void testValidateJwtToken() {
    String jwt = jwtUtils.generateJwtToken(authentication);

    boolean isValid = jwtUtils.validateJwtToken(jwt);


    assertTrue(isValid);
  }

  @Test
  public void testInvalidJwtToken() {
    String jwt = jwtUtils.generateJwtToken(authentication);

    boolean isValid = jwtUtils.validateJwtToken(jwt + "invalid");

    assertFalse(isValid);
  }

}
