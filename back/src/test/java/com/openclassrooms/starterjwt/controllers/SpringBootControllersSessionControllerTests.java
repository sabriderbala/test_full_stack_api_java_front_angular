package com.openclassrooms.starterjwt.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get; // Importez cette classe
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status; // Importez cette classe
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content; // Importez cette classe
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath; // Importez cette classe
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.services.SessionService;

import com.fasterxml.jackson.databind.ObjectMapper;



@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class SpringBootControllersSessionControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private SessionService sessionService;

  @MockBean
  private SessionMapper sessionMapper;

  @Test
  @WithMockUser(username = "yoga@studio.com", roles = {"ADMIN"})
  public void testFindById() throws Exception {
      Session session = new Session();
      session.setId(1L);
      when(sessionService.getById(1L)).thenReturn(session);

      SessionDto sessionDto = new SessionDto();
      sessionDto.setId(1L);
      when(sessionMapper.toDto(any(Session.class))).thenReturn(sessionDto);

      mockMvc.perform(get("/api/session/1"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(1L));
  }

  @Test
  @WithMockUser(username = "yoga@studio.com", roles = {"ADMIN"})
  public void testFindByIdNotFound() throws Exception {
      when(sessionService.getById(1L)).thenReturn(null);

      mockMvc.perform(get("/api/session/1"))
        .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser(username = "yoga@studio.com", roles = {"ADMIN"})
  public void testFindByIdBadRequest() throws Exception {
      mockMvc.perform(get("/api/session/abc"))
        .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(username = "yoga@studio.com", roles = {"ADMIN"})
  public void testFindAll() throws Exception {
      mockMvc.perform(get("/api/session"))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username = "yoga@studio.com", roles = {"ADMIN"})
  public void testCreate() throws Exception {
      SessionDto sessionDto = new SessionDto();
      sessionDto.setName("Sample Session");
      sessionDto.setDescription("Description of the sample session");
      sessionDto.setDate(new Date());
      sessionDto.setTeacher_id(1L);

      String sessionDtoJson = new ObjectMapper().writeValueAsString(sessionDto);

      mockMvc.perform(post("/api/session")
              .contentType(MediaType.APPLICATION_JSON)
              .content(sessionDtoJson))
              .andExpect(status().isOk());

  }

  @Test
  @WithMockUser(username = "yoga@studio.com", roles = {"ADMIN"})
  public void testUpdate() throws Exception {

      SessionDto existingSessionDto = new SessionDto();
      existingSessionDto.setName("Existing Session");
      existingSessionDto.setDescription("Description of the existing session");
      existingSessionDto.setDate(new Date());
      existingSessionDto.setTeacher_id(1L);


      String existingSessionDtoJson = new ObjectMapper().writeValueAsString(existingSessionDto);


      mockMvc.perform(put("/api/session/{id}", "1")
              .contentType(MediaType.APPLICATION_JSON)
              .content(existingSessionDtoJson))
              .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username = "yoga@studio.com", roles = {"ADMIN"})
  public void testNoLongerParticipate() throws Exception {

      SessionDto sessionDto = new SessionDto();
      sessionDto.setName("Sample Session");
      sessionDto.setDescription("Description of the sample session");
      sessionDto.setDate(new Date());
      sessionDto.setTeacher_id(1L);

      String sessionDtoJson = new ObjectMapper().writeValueAsString(sessionDto);

      mockMvc.perform(delete("/api/session/{id}/participate/{userId}", "1", "2")
              .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username = "yoga@studio.com", roles = {"ADMIN"})
  public void testParticipate() throws Exception {

      SessionDto sessionDto = new SessionDto();
      sessionDto.setName("Sample Session");
      sessionDto.setDescription("Description of the sample session");
      sessionDto.setDate(new Date());
      sessionDto.setTeacher_id(1L);


      String sessionDtoJson = new ObjectMapper().writeValueAsString(sessionDto);

      mockMvc.perform(post("/api/session/{id}/participate/{userId}", "1", "2")
              .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username = "yoga@studio.com", roles = {"ADMIN"})
  public void testDeleteSessionSuccess() throws Exception {
      Session session = new Session();
      session.setId(1L);

      Mockito.doNothing().when(sessionService).delete(1L);
      Mockito.when(sessionService.getById(1L)).thenReturn(session);

      mockMvc.perform(MockMvcRequestBuilders.delete("/api/session/{id}", "1"))
              .andExpect(MockMvcResultMatchers.status().isOk());

      Mockito.verify(sessionService, Mockito.times(1)).delete(1L);
  }


  @Test
  @WithMockUser(username = "yoga@studio.com", roles = {"ADMIN"})
  public void testDeleteSessionInvalidId() throws Exception {

      mockMvc.perform(delete("/api/session/{id}", "invalid"))
          .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(username = "yoga@studio.com", roles = {"ADMIN"})
  public void testEqualityInControllerResponse() throws Exception {
      SessionDto sessionDto = new SessionDto();
      sessionDto.setId(1L);
      sessionDto.setName("Session Name");

      // Simule le même objet retourné pour garantir l'égalité
      when(sessionService.getById(1L)).thenReturn(new Session());
      when(sessionMapper.toDto(any(Session.class))).thenReturn(sessionDto);

      String result = mockMvc.perform(get("/api/session/1"))
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
          .andReturn().getResponse().getContentAsString();

      SessionDto resultDto = new ObjectMapper().readValue(result, SessionDto.class);

      // Tester l'égalité
      assertTrue(resultDto.equals(sessionDto));
      assertEquals(resultDto.hashCode(), sessionDto.hashCode());
  }

  @Test
  @WithMockUser(username = "yoga@studio.com", roles = {"ADMIN"})
  public void testInequalityInControllerResponse() throws Exception {
      SessionDto sessionDto = new SessionDto();
      sessionDto.setId(1L);
      sessionDto.setName("Session Name");

      SessionDto differentSessionDto = new SessionDto();
      differentSessionDto.setId(2L);
      differentSessionDto.setName("Different Name");

      // Simuler différents objets retournés
      when(sessionService.getById(1L)).thenReturn(new Session());
      when(sessionMapper.toDto(any(Session.class))).thenReturn(sessionDto);

      String result = mockMvc.perform(get("/api/session/1"))
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
          .andReturn().getResponse().getContentAsString();

      SessionDto resultDto = new ObjectMapper().readValue(result, SessionDto.class);

      // Tester l'inégalité
      assertFalse(resultDto.equals(differentSessionDto));
  }


}
