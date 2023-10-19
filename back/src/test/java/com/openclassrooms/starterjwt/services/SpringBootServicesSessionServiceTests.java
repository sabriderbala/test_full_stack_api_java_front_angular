package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.exception.BadRequestException;
import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.services.SessionService;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SessionServiceTest {

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SessionService sessionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateSession() {
        Session session = new Session();
        when(sessionRepository.save(any(Session.class))).thenReturn(session);

        sessionService.create(session);

        verify(sessionRepository).save(session);
    }

    @Test
    void shouldDeleteSession() {
        Long sessionId = 1L;

        sessionService.delete(sessionId);

        verify(sessionRepository).deleteById(sessionId);
    }

    @Test
    void shouldFindAllSessions() {
        sessionService.findAll();

        verify(sessionRepository).findAll();
    }

    @Test
    void shouldGetSessionById() {
        Long sessionId = 1L;
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(new Session()));

        sessionService.getById(sessionId);

        verify(sessionRepository).findById(sessionId);
    }

    @Test
    void shouldUpdateSession() {
        Long sessionId = 1L;
        Session session = new Session();
        when(sessionRepository.save(any(Session.class))).thenReturn(session);

        sessionService.update(sessionId, session);

        verify(sessionRepository).save(session);
    }

    @Test
    void shouldThrowBadRequestExceptionWhenAlreadyParticipate() {
        Long sessionId = 1L;
        Long userId = 1L;
        Session session = mock(Session.class);
        User user = mock(User.class);

        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(user.getId()).thenReturn(userId);
        when(session.getUsers()).thenReturn(List.of(user));

        try {
            sessionService.participate(sessionId, userId);
        } catch (BadRequestException e) {
            verify(sessionRepository).findById(sessionId);
            verify(userRepository).findById(userId);
            return;
        }

        verify(sessionRepository).findById(sessionId);
        verify(userRepository).findById(userId);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenSessionNotFoundForParticipate() {
        Long sessionId = 1L;
        Long userId = 1L;

        when(sessionRepository.findById(sessionId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            sessionService.participate(sessionId, userId);
        });
    }

    @Test
    void shouldThrowNotFoundExceptionWhenUserNotFoundForParticipate() {
        Long sessionId = 1L;
        Long userId = 1L;
        Session session = mock(Session.class);

        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            sessionService.participate(sessionId, userId);
        });
    }

    @Test
    void shouldAddUserToSessionWhenParticipate() {
        Long sessionId = 1L;
        Long userId = 1L;
        Session session = mock(Session.class);
        User user = mock(User.class);
        List<User> mockUserList = mock(ArrayList.class);
        when(user.getId()).thenReturn(userId);
        when(session.getUsers()).thenReturn(mockUserList);

        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        sessionService.participate(sessionId, userId);

        verify(mockUserList).add(user);
        verify(sessionRepository).save(session);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenSessionNotFoundForNoLongerParticipate() {
        Long sessionId = 1L;
        Long userId = 1L;

        when(sessionRepository.findById(sessionId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            sessionService.noLongerParticipate(sessionId, userId);
        });
    }

    @Test
    void shouldThrowBadRequestExceptionWhenUserNotParticipatingForNoLongerParticipate() {
        Long sessionId = 1L;
        Long userId = 1L;
        Session session = mock(Session.class);

        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));
        when(session.getUsers()).thenReturn(new ArrayList<>());

        assertThrows(BadRequestException.class, () -> {
            sessionService.noLongerParticipate(sessionId, userId);
        });
    }



}
