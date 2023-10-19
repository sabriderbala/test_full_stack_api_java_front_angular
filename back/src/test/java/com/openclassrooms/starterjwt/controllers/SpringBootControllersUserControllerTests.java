package com.openclassrooms.starterjwt.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;



import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.UserService;



@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class SpringBootControllersUserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;


    @Test
    @WithMockUser(username = "yoga@studio.com", roles = {"ADMIN"})
    public void testFindById() throws Exception {
        User user = new User();
        user.setId(1L);

        when(userService.findById(1L)).thenReturn(user);

        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setEmail("user@user.com");
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setCreatedAt(LocalDateTime.now());
        userDto.setUpdatedAt(LocalDateTime.now());
        userDto.setAdmin(false);

        when(userMapper.toDto(any(User.class))).thenReturn(userDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.email").value("user@user.com"))
            .andExpect(jsonPath("$.firstName").value("John"))
            .andExpect(jsonPath("$.lastName").value("Doe"))
            .andExpect(jsonPath("$.createdAt").exists())
            .andExpect(jsonPath("$.updatedAt").exists())
            .andExpect(jsonPath("$.admin").value(false));
          }

    @Test
    @WithMockUser(username = "yoga@studio.com", roles = {"ADMIN"})
    public void testFindByIdInvalidId() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/invalid"))
            .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "yoga@studio.com", roles = {"ADMIN"})
    public void testFindByIdNotFound() throws Exception {
        when(userService.findById(1L)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/1"))
            .andExpect(status().isNotFound())
            .andExpect(content().string(""));
    }


    @Test
    @WithMockUser(username = "yoga@studio.com", roles = {"ADMIN"})
    public void testDeleteUser() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setEmail("yoga@studio.com");

        when(userService.findById(1L)).thenReturn(user);

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("differentuser@example.com");

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/user/1"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "yoga@studio.com", roles = {"ADMIN"})
    public void testDeleteUserSuccess() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setEmail("yoga@studio.com");

        when(userService.findById(1L)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/user/1"))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "yoga@studio.com", roles = {"ADMIN"})
    public void testDeleteUserNotFound() throws Exception {
        when(userService.findById(1L)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/user/1"))
            .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "yoga@studio.com", roles = {"ADMIN"})
    public void testDeleteUserInvalidId() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/user/invalid"))
            .andExpect(status().isBadRequest());
    }

}
