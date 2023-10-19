package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import com.openclassrooms.starterjwt.services.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

public class SpringBootServicesTeacherServiceTests {

@Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private TeacherService teacherService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        Teacher teacher1 = new Teacher();
        teacher1.setId(1L);
        teacher1.setFirstName("John").setLastName("Test");

        Teacher teacher2 = new Teacher();
        teacher2.setId(2L);
        teacher2.setFirstName("Marie").setLastName("Test");

        when(teacherRepository.findAll()).thenReturn(Arrays.asList(teacher1, teacher2));

        List<Teacher> teachers = teacherService.findAll();

        assertEquals(2, teachers.size());
        assertEquals("John", teachers.get(0).getFirstName());
        assertEquals("Test", teachers.get(0).getLastName());
        assertEquals("Marie", teachers.get(1).getFirstName());
        assertEquals("Test", teachers.get(1).getLastName());
    }


    @Test
    public void testFindById() {
        Teacher teacher1 = new Teacher();
        teacher1.setId(1L);
        teacher1.setFirstName("John").setLastName("Test");

        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher1));

        Teacher foundTeacher = teacherService.findById(1L);

        assertEquals("John", foundTeacher.getFirstName());
    }


    @Test
    public void testFindByIdNotFound() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.empty());

        Teacher foundTeacher = teacherService.findById(1L);

        assertNull(foundTeacher);
    }
}
