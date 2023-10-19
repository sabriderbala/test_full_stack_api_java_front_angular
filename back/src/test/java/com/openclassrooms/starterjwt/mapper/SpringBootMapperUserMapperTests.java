package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SpringBootMapperUserMapperTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void contextLoads() {
        assertNotNull(userMapper, "UserMapper should not be null");
        String implClassName = userMapper.getClass().getSimpleName();
        assertEquals("UserMapperImpl", implClassName, "Expected the mapper to be an instance of UserMapperImpl");
    }

    @Test
    public void testToEntity() {
        UserDto dto = new UserDto();
        dto.setId(1L);
        dto.setEmail("test@example.com");
        dto.setFirstName("TestFirstName");
        dto.setLastName("TestLastName");
        dto.setPassword("password123");

        User entity = userMapper.toEntity(dto);

        assertNotNull(entity, "Mapped entity should not be null");

        assertEquals(dto.getId(), entity.getId(), "Id value did not match");
        assertEquals(dto.getEmail(), entity.getEmail(), "Email value did not match");
        assertEquals(dto.getFirstName(), entity.getFirstName(), "FirstName value did not match");
        assertEquals(dto.getLastName(), entity.getLastName(), "LastName value did not match");
        assertEquals(dto.getPassword(), entity.getPassword(), "Password value did not match");
    }


    @Test
    public void testToDto() {
        User entity = new User();
        entity.setId(1L);
        entity.setEmail("testentity@example.com");
        entity.setFirstName("EntityFirstName");
        entity.setLastName("EntityLastName");
        entity.setPassword("entitypassword");
        entity.setAdmin(true);

        UserDto dto = userMapper.toDto(entity);

        assertNotNull(dto, "Mapped dto should not be null");

        assertEquals(entity.getId(), dto.getId(), "Id value did not match");
        assertEquals(entity.getEmail(), dto.getEmail(), "Email value did not match");
        assertEquals(entity.getFirstName(), dto.getFirstName(), "FirstName value did not match");
        assertEquals(entity.getLastName(), dto.getLastName(), "LastName value did not match");
        assertEquals(entity.getPassword(), dto.getPassword(), "Password value did not match");
        assertEquals(entity.isAdmin(), dto.isAdmin(), "Admin status did not match");
    }


    @Test
    public void testToEntityList() {
        UserDto dto = new UserDto();
        dto.setId(1L);
        dto.setEmail("testdto@example.com");
        dto.setFirstName("DtoFirstName");
        dto.setLastName("DtoLastName");
        dto.setPassword("dtosecurepassword");
        dto.setAdmin(true);

        List<User> entities = userMapper.toEntity(Collections.singletonList(dto));

        assertNotNull(entities, "Mapped entities list should not be null");
        assertFalse(entities.isEmpty(), "Mapped entities list should not be empty");
        assertEquals(1, entities.size(), "Size of mapped entities list should be 1");

        User mappedEntity = entities.get(0);
        assertEquals(dto.getId(), mappedEntity.getId());
        assertEquals(dto.getEmail(), mappedEntity.getEmail());
        assertEquals(dto.getFirstName(), mappedEntity.getFirstName());
        assertEquals(dto.getLastName(), mappedEntity.getLastName());
        assertEquals(dto.getPassword(), mappedEntity.getPassword());
        assertEquals(dto.isAdmin(), mappedEntity.isAdmin());
    }


    @Test
    public void testToDtoList() {
        User entity = new User();
        entity.setId(1L);
        entity.setEmail("testentity@example.com");
        entity.setFirstName("EntityFirstName");
        entity.setLastName("EntityLastName");
        entity.setPassword("entitysecurepassword");
        entity.setAdmin(true);

        List<UserDto> dtoList = userMapper.toDto(Collections.singletonList(entity));

        assertNotNull(dtoList, "Mapped dto list should not be null");
        assertFalse(dtoList.isEmpty(), "Mapped dto list should not be empty");
        assertEquals(1, dtoList.size(), "Size of mapped dto list should be 1");

        UserDto mappedDto = dtoList.get(0);
        assertEquals(entity.getId(), mappedDto.getId());
        assertEquals(entity.getEmail(), mappedDto.getEmail());
        assertEquals(entity.getFirstName(), mappedDto.getFirstName());
        assertEquals(entity.getLastName(), mappedDto.getLastName());
        assertEquals(entity.getPassword(), mappedDto.getPassword());
        assertEquals(entity.isAdmin(), mappedDto.isAdmin());
    }

}
