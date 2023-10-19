package com.openclassrooms.starterjwt.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class SpringBootModelsTeacherModelTests {

    @Test
    public void testToString() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("John");
        teacher.setLastName("Doe");

        String teacherAsString = teacher.toString();

        assertTrue(teacherAsString.contains("id=1"));
        assertTrue(teacherAsString.contains("firstName=John"));
        assertTrue(teacherAsString.contains("lastName=Doe"));
    }

    @Test
    public void testEquals() {
        Teacher teacher1 = new Teacher();
        teacher1.setId(1L);
        teacher1.setFirstName("John");
        teacher1.setLastName("Doe");

        Teacher teacher2 = new Teacher();
        teacher2.setId(1L);
        teacher2.setFirstName("John");
        teacher2.setLastName("Doe");

        assertEquals(teacher1, teacher2);
    }

    @Test
    public void testHashCode() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("John");
        teacher.setLastName("Doe");

        int expectedHashCode = teacher.hashCode();

        Teacher sameTeacher = new Teacher();
        sameTeacher.setId(1L);
        sameTeacher.setFirstName("John");
        sameTeacher.setLastName("Doe");

        assertEquals(expectedHashCode, sameTeacher.hashCode());
    }

    @Test
    public void testFullConstructor() {
        LocalDateTime now = LocalDateTime.now();

        Teacher teacher = new Teacher(1L, "Doe", "John", now, now);

        assertNotNull(teacher);
        assertEquals(1L, teacher.getId());
        assertEquals("Doe", teacher.getLastName());
        assertEquals("John", teacher.getFirstName());
        assertEquals(now, teacher.getCreatedAt()); // testant le 'createdAt' via le constructeur
        assertEquals(now, teacher.getUpdatedAt()); // testant le 'updatedAt' via le constructeur
    }

    @Test
    public void testBuilder() {
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        Teacher teacher = Teacher.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();

        assertNotNull(teacher);
        assertEquals(1L, teacher.getId());
        assertEquals("John", teacher.getFirstName());
        assertEquals("Doe", teacher.getLastName());
        assertEquals(createdAt, teacher.getCreatedAt());
        assertEquals(updatedAt, teacher.getUpdatedAt());
    }

    @Test
    public void testTeacherBuilderToString() {
        LocalDateTime now = LocalDateTime.now();

        Teacher.TeacherBuilder builder = Teacher.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .createdAt(now)
                .updatedAt(now);

                String expectedString = String.format(
                  "Teacher.TeacherBuilder(id=%s, lastName=%s, firstName=%s, createdAt=%s, updatedAt=%s)",
                  1L, "Doe", "John", now.toString(), now.toString()
          );


        assertEquals(expectedString, builder.toString());
    }
}
