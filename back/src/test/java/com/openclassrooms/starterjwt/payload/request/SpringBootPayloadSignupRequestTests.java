package com.openclassrooms.starterjwt.payload.request;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.*;

public class SpringBootPayloadSignupRequestTests {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private SignupRequest createSignupRequest(String email, String firstName, String lastName, String password) {
        SignupRequest request = new SignupRequest();
        request.setEmail(email);
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setPassword(password);
        return request;
    }

    @Test
    public void testEqualityAndInequalityOfSignupRequests() {
        SignupRequest request1 = createSignupRequest("test@example.com", "First", "Last", "password");
        SignupRequest request2 = createSignupRequest("test@example.com", "First", "Last", "password");
        SignupRequest request3 = createSignupRequest("different@example.com", "First", "Last", "password");

        assertEquals(request1, request1, "A SignupRequest object should be equal to itself");
        assertEquals(request1, request2, "Equivalent SignupRequest objects should be equal");
        assertNotEquals(request1, request3, "SignupRequest objects with different properties should not be equal");
        assertNotEquals(request1, new Object(), "SignupRequest should not be equal to an object of a different type");
    }

    @Test
    public void testToStringRepresentation() {
        SignupRequest request = createSignupRequest("test@example.com", "First", "Last", "password");
        String expectedString = "SignupRequest(email=test@example.com, firstName=First, lastName=Last, password=password)";
        assertEquals(expectedString, request.toString(), "toString should return the correct representation");
    }

    @Test
    public void testCanEqualMethod() {
        SignupRequest request1 = new SignupRequest();
        SignupRequest request2 = new SignupRequest();

        assertTrue(request1.canEqual(request2), "canEqual should return true for objects of the same type");
        assertFalse(request1.canEqual(new Object()), "canEqual should return false for objects of different types");
    }

    @Test
    public void givenSameContentDifferentObjects_whenTestingEquality_thenTrue() {
        SignupRequest request1 = createSignupRequest("test@example.com", "First", "Last", "password");
        SignupRequest request2 = createSignupRequest("test@example.com", "First", "Last", "password");

        assertEquals(request1, request2, "Objects with the same content should be equal");
        assertEquals(request1.hashCode(), request2.hashCode(), "Hash codes of objects with the same content should be equal");
    }


}
