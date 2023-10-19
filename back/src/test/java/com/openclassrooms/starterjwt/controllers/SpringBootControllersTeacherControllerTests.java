package com.openclassrooms.starterjwt.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.mapper.TeacherMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.services.TeacherService;

import java.util.Arrays;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class SpringBootControllersTeacherControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TeacherService teacherService;

  @MockBean
  private TeacherMapper teacherMapper;

  @Test
  @WithMockUser(username = "yoga@studio.com", roles = {"ADMIN"})
  public void testFindById() throws Exception {
      Teacher teacher = new Teacher();
      teacher.setId(1L);

      when(teacherService.findById(1L)).thenReturn(teacher);

      TeacherDto teacherDto = new TeacherDto();
      teacherDto.setId(1L);
      when(teacherMapper.toDto(any(Teacher.class))).thenReturn(teacherDto);

      mockMvc.perform(get("/api/teacher/1"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(1L));
  }


  @WithMockUser(username = "yoga@studio.com", roles = {"ADMIN"})
  @Test
  public void testFindByIdNotFound() throws Exception {
    when(teacherService.findById(1L)).thenReturn(null);

    mockMvc.perform(get("/api/teacher/1"))
      .andExpect(status().isNotFound());
  }

  @WithMockUser(username = "yoga@studio.com", roles = {"ADMIN"})
  @Test
  public void testFindByIdBadRequest() throws Exception {
    mockMvc.perform(get("/api/teacher/abc"))
      .andExpect(status().isBadRequest());
  }

  @WithMockUser(username = "yoga@studio.com", roles = {"ADMIN"})
  @Test
  public void testFindAll() throws Exception {
    when(teacherService.findAll()).thenReturn(Arrays.asList(new Teacher(), new Teacher()));
    when(teacherMapper.toDto(anyList())).thenReturn(Arrays.asList(new TeacherDto(), new TeacherDto()));

    mockMvc.perform(get("/api/teacher"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$", hasSize(2)));
  }

  @Test
  @WithMockUser(username = "yoga@studio.com", roles = {"ADMIN"})
  public void testEqualityInControllerResponse() throws Exception {
      TeacherDto teacherDto = new TeacherDto();
      teacherDto.setId(1L);
      teacherDto.setFirstName("Joe");

      when(teacherService.findById(1L)).thenReturn(new Teacher());
      when(teacherMapper.toDto(any(Teacher.class))).thenReturn(teacherDto);

      String result = mockMvc.perform(get("/api/teacher/1"))
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
          .andReturn().getResponse().getContentAsString();

      TeacherDto resultDto = new ObjectMapper().readValue(result, TeacherDto.class);

      assertTrue(resultDto.equals(teacherDto));
      assertEquals(resultDto.hashCode(), teacherDto.hashCode());
  }
  @Test
  @WithMockUser(username = "yoga@studio.com", roles = {"ADMIN"})
  public void testInequalityInControllerResponse() throws Exception {
      TeacherDto teacherDto = new TeacherDto();
      teacherDto.setId(1L);
      teacherDto.setFirstName("Joe");

      TeacherDto differentTeacherDto = new TeacherDto();
      differentTeacherDto.setId(2L);
      differentTeacherDto.setFirstName("Bob");

      when(teacherService.findById(1L)).thenReturn(new Teacher());
      when(teacherMapper.toDto(any(Teacher.class))).thenReturn(teacherDto);

      String result = mockMvc.perform(get("/api/teacher/1"))
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
          .andReturn().getResponse().getContentAsString();

      TeacherDto resultDto = new ObjectMapper().readValue(result, TeacherDto.class);

      assertFalse(resultDto.equals(differentTeacherDto));
  }
}
