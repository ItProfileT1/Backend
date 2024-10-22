package com.it.backend.service;

import com.it.backend.dto.NameDescriptionDto;
import com.it.backend.entity.HardSkill;
import com.it.backend.repository.HardSkillsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HardSkillsServiceTest {
    @Mock
    HardSkillsRepository hardSkillsRepository;

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
}