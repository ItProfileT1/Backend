package com.it.backend.service;

import com.it.backend.entity.Position;
import com.it.backend.entity.Specialist;
import com.it.backend.repository.PositionRepository;
import com.it.backend.repository.SpecialistRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PositionServiceTest {

    @Mock
    PositionRepository positionRepository;

    @Mock
    SpecialistRepository specialistRepository;

    @InjectMocks
    PositionService positionService;

    @Test
    void createPosition_forExistingSpecialist_ReturnsOptionalOfLong() {
        //given
        IdNameDescriptionDto dto = mock(IdNameDescriptionDto.class);
        Specialist specialist = mock(Specialist.class);
        Position position = new Position();
        position.setId(1L);
        //when
        when(specialistRepository.findById(dto.id())).thenReturn(Optional.of(specialist));
        when(positionRepository.save(any(Position.class))).thenReturn(position);
        when(specialistRepository.save(specialist)).thenReturn(specialist);
        var result = positionService.createPosition(dto);
        //then
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get());
    }

    @Test
    void createPosition_forNotExistingSpecialist_ReturnsOptionalEmpty() {
        //given
        IdNameDescriptionDto dto = mock(IdNameDescriptionDto.class);
        //when
        when(specialistRepository.findById(dto.id())).thenReturn(Optional.empty());
        var result = positionService.createPosition(dto);
        //then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    @Test
    void createPosition_forNotExistingSpecialistHavingExistingPosition_ReturnsOptionalEmpty() {
        //given
        IdNameDescriptionDto dto = mock(IdNameDescriptionDto.class);
        Specialist specialist = new Specialist();
        specialist.setPosition(mock(Position.class));
        //when
        when(specialistRepository.findById(dto.id())).thenReturn(Optional.of(specialist));
        var result = positionService.createPosition(dto);
        //then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getBySpecialistId_existing_returnsOptionalOfIdNameDescriptionDto(){
        //given
        Long id = 1L;
        var specialist = new Specialist();
        specialist.setPosition(mock(Position.class));
        //when
        when(specialistRepository.findById(id)).thenReturn(Optional.of(specialist));
        var result = positionService.getBySpecialistId(1L);
        //then
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(IdNameDescriptionDto.class, result.get().getClass());
    }

    @Test
    void getBySpecialistId_notExisting_returnsOptionalEmpty(){
        //given
        Long id = 1L;
        var specialist = new Specialist();
        //when
        when(specialistRepository.findById(id)).thenReturn(Optional.of(specialist));
        var result = positionService.getBySpecialistId(1L);
        //then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getBySpecialistId_forNotExistingSpecialist_returnsOptionalEmpty(){
        //given
        Long id = 1L;
        //when
        when(specialistRepository.findById(id)).thenReturn(Optional.empty());
        var result = positionService.getBySpecialistId(1L);
        //then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getById_existing_returnsOptionalOfIdNameDescriptionDto(){
        //given
        Long id = 1L;
        Position position = new Position();
        //when
        when(positionRepository.findById(id)).thenReturn(Optional.of(position));
        var result = positionService.getById(1L);
        //then
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(IdNameDescriptionDto.class, result.get().getClass());
    }

    @Test
    void getById_notExisting_returnsOptionalEmpty(){
        //given
        Long id = 1L;
        //when
        when(positionRepository.findById(id)).thenReturn(Optional.empty());
        var result = positionService.getById(1L);
        //then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}