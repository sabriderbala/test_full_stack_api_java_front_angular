package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.mapper.SessionMapperImpl;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SpringBootMapperSessionMapperTests {

    @Autowired
    private SessionMapper sessionMapper;

    private Session session;
    private SessionDto sessionDto;

    @BeforeEach
    public void setup() {
        LocalDateTime now = LocalDateTime.now();
        Date sessionDate = new Date();

        Teacher teacher = new Teacher(1L, "Doe", "John", now, now);
        User user = new User();
        user.setId(1L);

        session = new Session();
        session.setId(1L);
        session.setName("Test Session");
        session.setDate(sessionDate);
        session.setDescription("Description for test session");
        session.setTeacher(teacher);
        session.setUsers(Collections.singletonList(user));
        session.setCreatedAt(now);
        session.setUpdatedAt(now);

        sessionDto = new SessionDto();
        sessionDto.setId(1L);
        sessionDto.setName("Test Session");
        sessionDto.setDate(sessionDate);
        sessionDto.setTeacher_id(teacher.getId());
        sessionDto.setDescription("Description for test session");
        sessionDto.setUsers(Collections.singletonList(user.getId()));
        sessionDto.setCreatedAt(now);
        sessionDto.setUpdatedAt(now);
    }

    @Test
    public void testFromDto() {
        Session entity = sessionMapper.toEntity(sessionDto);

        assertNotNull(entity);
        assertEquals(session.getId(), entity.getId());
        assertEquals(session.getName(), entity.getName());
        assertEquals(session.getDate(), entity.getDate());
        assertEquals(session.getDescription(), entity.getDescription());
        assertNotNull(entity.getTeacher());
        assertEquals(session.getTeacher().getId(), entity.getTeacher().getId());
        assertEquals(session.getCreatedAt(), entity.getCreatedAt());
        assertEquals(session.getUpdatedAt(), entity.getUpdatedAt());
    }

    @Test
    public void testToDto() {
        SessionDto dto = sessionMapper.toDto(session);

        assertNotNull(dto);
        assertEquals(session.getId(), dto.getId());
        assertEquals(session.getName(), dto.getName());
        assertEquals(session.getDate(), dto.getDate());
        assertEquals(session.getDescription(), dto.getDescription());
        assertEquals(session.getTeacher().getId(), dto.getTeacher_id());
        assertEquals(session.getCreatedAt(), dto.getCreatedAt());
        assertEquals(session.getUpdatedAt(), dto.getUpdatedAt());
    }

    @Test
    public void testEntityListToDtoList() {
        Session session1 = new Session(1L, "Session 1", new Date(), "Description 1", new Teacher(1L, "Doe", "John", LocalDateTime.now(), LocalDateTime.now()), new ArrayList<>(), LocalDateTime.now(), LocalDateTime.now());
        Session session2 = new Session(2L, "Session 2", new Date(), "Description 2", new Teacher(2L, "Smith", "Anna", LocalDateTime.now(), LocalDateTime.now()), new ArrayList<>(), LocalDateTime.now(), LocalDateTime.now());

        List<Session> sessionList = Arrays.asList(session1, session2);

        List<SessionDto> dtoList = sessionMapper.toDto(sessionList);

        assertNotNull(dtoList);
        assertEquals(2, dtoList.size());

        SessionDto dto1 = dtoList.get(0);
        assertEquals(session1.getId(), dto1.getId());
        assertEquals(session1.getName(), dto1.getName());
        assertEquals(session1.getDescription(), dto1.getDescription());
        assertEquals(session1.getTeacher().getId(), dto1.getTeacher_id());

        SessionDto dto2 = dtoList.get(1);
        assertEquals(session2.getId(), dto2.getId());
        assertEquals(session2.getName(), dto2.getName());
        assertEquals(session2.getDescription(), dto2.getDescription());
        assertEquals(session2.getTeacher().getId(), dto2.getTeacher_id());
    }

    @Test
    public void testDtoListToEntityList() {
        SessionDto dto1 = new SessionDto(1L, "Session 1", new Date(), 1L, "Description 1", new ArrayList<>(), LocalDateTime.now(), LocalDateTime.now());
        SessionDto dto2 = new SessionDto(2L, "Session 2", new Date(), 2L, "Description 2", new ArrayList<>(), LocalDateTime.now(), LocalDateTime.now());

        List<SessionDto> dtoList = Arrays.asList(dto1, dto2);

        List<Session> sessionList = sessionMapper.toEntity(dtoList);

        assertNotNull(sessionList);
        assertEquals(2, sessionList.size());

        Session session1 = sessionList.get(0);
        assertEquals(dto1.getId(), session1.getId());
        assertEquals(dto1.getName(), session1.getName());
        assertEquals(dto1.getDescription(), session1.getDescription());
        assertNotNull(session1.getTeacher());
        assertEquals(dto1.getTeacher_id(), session1.getTeacher().getId());

        Session session2 = sessionList.get(1);
        assertEquals(dto2.getId(), session2.getId());
        assertEquals(dto2.getName(), session2.getName());
        assertEquals(dto2.getDescription(), session2.getDescription());
        assertNotNull(session2.getTeacher());
        assertEquals(dto2.getTeacher_id(), session2.getTeacher().getId());
    }


    @Test
    public void testMapperImplementation() {
        assertTrue(sessionMapper instanceof SessionMapperImpl, "Expected the actual mapper to be an instance of SessionMapperImpl");
    }
}
