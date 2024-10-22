package com.it.backend.service;

import com.it.backend.dto.NameDescriptionDto;
import com.it.backend.dto.StringDto;
import com.it.backend.entity.HardSkill;
import com.it.backend.entity.Position;
import com.it.backend.entity.PositionsHardSkills;
import com.it.backend.repository.HardSkillsRepository;
import com.it.backend.repository.PositionRepository;
import com.it.backend.repository.PositionsHardSkillsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HardSkillsServiceTest {
    @Mock
    HardSkillsRepository hardSkillsRepository;

    @Mock
    PositionRepository positionRepository;

    @Mock
    PositionsHardSkillsRepository positionsHardSkillsRepository;

    @InjectMocks
    HardSkillsService hardSkillsService;

    @Test
    void createHardSkill_notExisting_returnsOptionalOfId(){
        //given
        NameDescriptionDto dto = mock(NameDescriptionDto.class);
        HardSkill hardSkill = new HardSkill();
        hardSkill.setId(1L);
        //when
        when(hardSkillsRepository.existsHardSkillByName(dto.name())).thenReturn(false);
        when(hardSkillsRepository.save(any(HardSkill.class))).thenReturn(hardSkill);
        var result = hardSkillsService.createHardSkill(dto);
        //then
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get());
    }

    @Test
    void createHardSkill_existing_returnsOptionalEmpty(){
        //given
        NameDescriptionDto dto = mock(NameDescriptionDto.class);
        //when
        when(hardSkillsRepository.existsHardSkillByName(dto.name())).thenReturn(true);
        var result = hardSkillsService.createHardSkill(dto);
        //then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void attachToPosition_withExistingPositionAndHardSkill_returnsOptionalOfObject(){
        //given
        Long positionId = 1L;
        StringDto hardSkillNameDto = new StringDto("Java");
        HardSkill hardSkill = new HardSkill();
        Position position = new Position();
        //when
        when(hardSkillsRepository.findHardSkillByName(hardSkillNameDto.name())).thenReturn(Optional.of(hardSkill));
        when(positionRepository.findById(positionId)).thenReturn(Optional.of(position));
        when(positionsHardSkillsRepository.save(any(PositionsHardSkills.class))).thenReturn(new PositionsHardSkills());
        var result = hardSkillsService.attachToPosition(positionId, hardSkillNameDto);
        //then
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(Object.class, result.get().getClass());
    }

    @Test
    void attachToPosition_withExistingPositionAndNotExistingHardSkill_returnsOptionalEmpty(){
        //given
        Long positionId = 1L;
        StringDto hardSkillNameDto = new StringDto("Java");
        //when
        when(hardSkillsRepository.findHardSkillByName(hardSkillNameDto.name())).thenReturn(Optional.empty());
        var result = hardSkillsService.attachToPosition(positionId, hardSkillNameDto);
        //then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void attachToPosition_withNotExistingPositionAndExistingHardSkill_returnsOptionalEmpty(){
        //given
        Long positionId = 1L;
        StringDto hardSkillNameDto = new StringDto("Java");
        HardSkill hardSkill = new HardSkill();
        //when
        when(hardSkillsRepository.findHardSkillByName(hardSkillNameDto.name())).thenReturn(Optional.of(hardSkill));
        when(positionRepository.findById(positionId)).thenReturn(Optional.empty());
        var result = hardSkillsService.attachToPosition(positionId, hardSkillNameDto);
        //then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}