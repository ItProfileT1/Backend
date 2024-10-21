package com.it.backend.service;

import com.it.backend.dto.SpecialistDto;
import com.it.backend.entity.Specialist;
import com.it.backend.repository.SpecialistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialistServiceTest {

    @Mock
    SpecialistRepository specialistRepository;

    @InjectMocks
    SpecialistService specialistService;

    SpecialistDto specialistDto;
    Long id;
    Specialist specialist;

    @BeforeEach
    void init(){
        id = 1L;
        specialist = new Specialist();
        specialist.setId(id);
        specialistDto = new SpecialistDto("John Smith", "18.08.1999", "male", "Moscow");
    }

    @Test
    void createSpecialist_notExisting_returnsOptionalOfLong() {
        //when
        when(specialistRepository.existsByFio("John Smith")).thenReturn(false);
        when(specialistRepository.save(any(Specialist.class))).thenReturn(specialist);
        var result = specialistService.createSpecialist(specialistDto);
        //then
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(id, result.get());
    }

    @Test
    void createSpecialist_existing_returnsOptionalEmpty() {
        //when
        when(specialistRepository.existsByFio("John Smith")).thenReturn(true);
        var result = specialistService.createSpecialist(specialistDto);
        //then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getSpecialistDtoById_existing_returnsOptionalOfSpecialistDto(){
        //when
        when(specialistRepository.findById(id)).thenReturn(Optional.of(new Specialist()));
        var result = specialistService.getSpecialistDtoById(id);
        //then
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(SpecialistDto.class, result.get().getClass());
    }
    @Test
    void getSpecialistDtoById_notExisting_returnsOptionalEmpty(){
        //when
        when(specialistRepository.findById(id)).thenReturn(Optional.empty());
        var result = specialistService.getSpecialistDtoById(id);
        //then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}