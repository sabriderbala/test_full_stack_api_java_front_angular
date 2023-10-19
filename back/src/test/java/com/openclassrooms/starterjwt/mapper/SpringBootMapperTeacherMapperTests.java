package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.mapper.TeacherMapper;
import com.openclassrooms.starterjwt.mapper.TeacherMapperImpl;
import com.openclassrooms.starterjwt.models.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class SpringBootMapperTeacherMapperTests {
@Autowired
    private TeacherMapper teacherMapper;

    private Teacher teacher;
    private TeacherDto teacherDto;

    @BeforeEach
    public void setup() {
        LocalDateTime now = LocalDateTime.now();
        teacher = new Teacher(1L, "Doe", "John", now, now);
        teacherDto = new TeacherDto(1L, "Doe", "John", now, now);
    }

    @Test
    public void testFromDto() {
        Teacher entity = teacherMapper.toEntity(teacherDto);

        assertNotNull(entity);
        assertEquals(teacher.getId(), entity.getId());
        assertEquals(teacher.getFirstName(), entity.getFirstName());
        assertEquals(teacher.getLastName(), entity.getLastName());
        assertEquals(teacher.getCreatedAt(), entity.getCreatedAt());
        assertEquals(teacher.getUpdatedAt(), entity.getUpdatedAt());
    }

    @Test
    public void testToDto() {
        TeacherDto dto = teacherMapper.toDto(teacher);

        assertNotNull(dto);
        assertEquals(teacher.getId(), dto.getId());
        assertEquals(teacher.getFirstName(), dto.getFirstName());
        assertEquals(teacher.getLastName(), dto.getLastName());
        assertEquals(teacher.getCreatedAt(), dto.getCreatedAt());
        assertEquals(teacher.getUpdatedAt(), dto.getUpdatedAt());
    }


    @Test
    public void testToEntityList() {
        LocalDateTime now = LocalDateTime.now();
        TeacherDto dto = new TeacherDto(1L, "Doe", "John", now, now);
        List<TeacherDto> dtoList = Collections.singletonList(dto);

        List<Teacher> entityList = teacherMapper.toEntity(dtoList);

        assertNotNull(entityList);
        assertFalse(entityList.isEmpty());
        assertEquals(1, entityList.size());

        Teacher entity = entityList.get(0);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getFirstName(), entity.getFirstName());
        assertEquals(dto.getLastName(), entity.getLastName());
        assertEquals(dto.getCreatedAt(), entity.getCreatedAt());
        assertEquals(dto.getUpdatedAt(), entity.getUpdatedAt());
    }

    @Test
    public void testToDtoList() {
        LocalDateTime now = LocalDateTime.now();
        Teacher teacher = new Teacher(1L, "Doe", "John", now, now);
        List<Teacher> entityList = Collections.singletonList(teacher);

        List<TeacherDto> dtoList = teacherMapper.toDto(entityList);

        assertNotNull(dtoList);
        assertFalse(dtoList.isEmpty());
        assertEquals(1, dtoList.size());

        TeacherDto dto = dtoList.get(0);
        assertEquals(teacher.getId(), dto.getId());
        assertEquals(teacher.getFirstName(), dto.getFirstName());
        assertEquals(teacher.getLastName(), dto.getLastName());
        assertEquals(teacher.getCreatedAt(), dto.getCreatedAt());
        assertEquals(teacher.getUpdatedAt(), dto.getUpdatedAt());
    }

  @Test
  public void testMapperImplementation() {
      assertTrue(teacherMapper instanceof TeacherMapperImpl, "Expected the actual mapper to be an instance of TeacherMapperImpl");
  }

}
