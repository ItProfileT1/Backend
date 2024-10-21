package com.it.backend.service;

import com.it.backend.dto.IdNameDescriptionDto;
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
}