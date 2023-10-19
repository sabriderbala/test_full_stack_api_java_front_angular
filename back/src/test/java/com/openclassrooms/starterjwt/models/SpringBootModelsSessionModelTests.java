package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SpringBootModelsSessionModelTests {
    private Session session;

    @BeforeEach
    public void setUp() {
        session = new Session();
    }

    @Test
    public void testSetters() {
        Date now = new Date();
        LocalDateTime localDateTime = LocalDateTime.now();
        Teacher teacher = new Teacher();
        List<User> users = Arrays.asList(new User(), new User());

        session.setId(1L);
        session.setName("Test Name");
        session.setDate(now);
        session.setDescription("Test Description");
        session.setTeacher(teacher);
        session.setUsers(users);
        session.setCreatedAt(localDateTime);
        session.setUpdatedAt(localDateTime);

        assertEquals(1L, session.getId());
        assertEquals("Test Name", session.getName());
        assertEquals(now, session.getDate());
        assertEquals("Test Description", session.getDescription());
        assertEquals(teacher, session.getTeacher());
        assertEquals(users, session.getUsers());
        assertEquals(localDateTime, session.getCreatedAt());
        assertEquals(localDateTime, session.getUpdatedAt());
    }

    @Test
    public void testConstructors() {
        assertNotNull(new Session());

        Date now = new Date();
        LocalDateTime localDateTime = LocalDateTime.now();
        Teacher teacher = new Teacher();
        List<User> users = Arrays.asList(new User(), new User());

        Session fullSession = new Session(1L, "Test Name", now, "Test Description", teacher, users, localDateTime, localDateTime);

        assertNotNull(fullSession);
        assertEquals(1L, fullSession.getId());
        assertEquals("Test Name", fullSession.getName());
        assertEquals(now, fullSession.getDate());
        assertEquals("Test Description", fullSession.getDescription());
        assertEquals(teacher, fullSession.getTeacher());
        assertEquals(users, fullSession.getUsers());
        assertEquals(localDateTime, fullSession.getCreatedAt());
        assertEquals(localDateTime, fullSession.getUpdatedAt());
    }

    @Test
    public void testEqualsAndHashCode() {
        Session session1 = new Session();
        session1.setId(1L);
        session1.setName("Session 1");

        Session session2 = new Session();
        session2.setId(1L);
        session2.setName("Session 1");

        Session session3 = new Session();
        session3.setId(2L);
        session3.setName("Session 2");

        assertEquals(session1, session2);
        assertEquals(session1.hashCode(), session2.hashCode());

        assertNotEquals(session1, session3);
        assertNotEquals(session1.hashCode(), session3.hashCode());

        assertEquals(session1, session1);
        assertNotEquals(session1, null);
        assertNotEquals(session1, new Object());
    }

    @Test
    public void testBuilder() {
        Date date = new Date();
        LocalDateTime now = LocalDateTime.now();
        Teacher teacher = new Teacher();
        User user = new User();

        Session session = Session.builder()
                .id(1L)
                .name("Session Name")
                .date(date)
                .description("This is a description")
                .teacher(teacher)
                .users(Arrays.asList(user))
                .createdAt(now)
                .updatedAt(now)
                .build();

        assertNotNull(session);
        assertEquals(Long.valueOf(1L), session.getId());
        assertEquals("Session Name", session.getName());
        assertEquals(date, session.getDate());
        assertEquals("This is a description", session.getDescription());
        assertEquals(teacher, session.getTeacher());
        assertEquals(1, session.getUsers().size());
        assertEquals(now, session.getCreatedAt());
        assertEquals(now, session.getUpdatedAt());
    }

    @Test
    public void testToString() {
        Date now = new Date();
        Teacher teacher = new Teacher();
        teacher.setId(10L);
        teacher.setFirstName("John");
        teacher.setLastName("Doe");

        User user1 = new User();
        user1.setId(1L);
        user1.setFirstName("user1");

        User user2 = new User();
        user2.setId(2L);
        user2.setFirstName("user2");

        Session session = new Session();
        session.setId(1L);
        session.setName("Test Session");
        session.setDate(now);
        session.setDescription("This is a test session");
        session.setTeacher(teacher);
        session.setUsers(Arrays.asList(user1, user2));

        String sessionAsString = session.toString();

        assertTrue(sessionAsString.contains("id=1"));
        assertTrue(sessionAsString.contains("name=Test Session"));
        assertTrue(sessionAsString.contains("date=" + now.toString()));
        assertTrue(sessionAsString.contains("description=This is a test session"));
        assertTrue(sessionAsString.contains("teacher=Teacher"));
        assertTrue(sessionAsString.contains("users=[User"));
    }


    @Test
    public void testBuilderToString() {
        Date date = new Date();
        LocalDateTime now = LocalDateTime.now();
        Teacher teacher = new Teacher();
        User user = new User();

        Session.SessionBuilder builder = Session.builder()
                .id(1L)
                .name("Session Name")
                .date(date)
                .description("This is a description")
                .teacher(teacher)
                .users(Arrays.asList(user))
                .createdAt(now)
                .updatedAt(now);

        String expectedString = String.format(
                "Session.SessionBuilder(id=%s, name=%s, date=%s, description=%s, teacher=%s, users=%s, createdAt=%s, updatedAt=%s)",
                1L,
                "Session Name",
                date.toString(),
                "This is a description",
                teacher.toString(),
                Arrays.asList(user).toString(),
                now.toString(),
                now.toString()
        );

        assertEquals(expectedString, builder.toString());
    }


}
