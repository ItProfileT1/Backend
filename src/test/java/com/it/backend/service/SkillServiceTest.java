package com.it.backend.service;

import com.it.backend.entity.Skill;
import com.it.backend.entity.Position;
import com.it.backend.repository.SkillRepository;
import com.it.backend.repository.PositionRepository;
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
class SkillServiceTest {
    @Mock
    SkillRepository skillRepository;

    @Mock
    PositionRepository positionRepository;

    @Mock
    PositionHardSkillRepository positionHardSkillRepository;

    @InjectMocks
    SkillService skillService;

    @Test
    void createSkill_notExisting_returnsOptionalOfId(){
        //given
        NameDescriptionDto dto = mock(NameDescriptionDto.class);
        Skill skill = new Skill();
        skill.setId(1L);
        //when
        when(skillRepository.existsHardSkillByName(dto.name())).thenReturn(false);
        when(skillRepository.save(any(Skill.class))).thenReturn(skill);
        var result = skillService.createSkill(dto);
        //then
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get());
    }

    @Test
    void createSkill_existing_returnsOptionalEmpty(){
        //given
        NameDescriptionDto dto = mock(NameDescriptionDto.class);
        //when
        when(skillRepository.existsHardSkillByName(dto.name())).thenReturn(true);
        var result = skillService.createSkill(dto);
        //then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void attachToPosition_withExistingPositionAndHardSkill_returnsOptionalOfObject(){
        //given
        Long positionId = 1L;
        StringDto hardSkillNameDto = new StringDto("Java");
        Skill skill = new Skill();
        Position position = new Position();
        //when
        when(skillRepository.findSkillByName(hardSkillNameDto.name())).thenReturn(Optional.of(skill));
        when(positionRepository.findById(positionId)).thenReturn(Optional.of(position));
        when(positionHardSkillRepository.save(any(PositionHardSkill.class))).thenReturn(new PositionHardSkill());
        var result = skillService.attachToPosition(positionId, hardSkillNameDto);
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
        when(skillRepository.findSkillByName(hardSkillNameDto.name())).thenReturn(Optional.empty());
        var result = skillService.attachToPosition(positionId, hardSkillNameDto);
        //then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void attachToPosition_withNotExistingPositionAndExistingHardSkill_returnsOptionalEmpty(){
        //given
        Long positionId = 1L;
        StringDto hardSkillNameDto = new StringDto("Java");
        Skill skill = new Skill();
        //when
        when(skillRepository.findSkillByName(hardSkillNameDto.name())).thenReturn(Optional.of(skill));
        when(positionRepository.findById(positionId)).thenReturn(Optional.empty());
        var result = skillService.attachToPosition(positionId, hardSkillNameDto);
        //then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}