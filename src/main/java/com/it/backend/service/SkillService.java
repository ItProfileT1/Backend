package com.it.backend.service;

import com.it.backend.dto.request.SkillRequest;
import com.it.backend.dto.response.CategoryResponse;
import com.it.backend.dto.response.CategorySkillResponse;
import com.it.backend.dto.response.SkillResponse;
import com.it.backend.entity.Skill;
import com.it.backend.exception.entity.EntityNotFoundException;
import com.it.backend.mapper.CategoryMapper;
import com.it.backend.mapper.SkillMapper;
import com.it.backend.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;
    private final TypeService typeService;
    private final CategoryService categoryService;
    private final ScaleService scaleService;

    public Skill findById(Long id){
        return skillRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("skill.not.found", id));
    }
//category == null
    public Set<CategorySkillResponse> findAll(){
        Map<CategoryResponse, Set<SkillResponse>> categories = new HashMap<>();
        for (Skill skill : skillRepository.findAll()) {
            var skillResponse = SkillMapper.INSTANCE.toSkillResponse(skill);
            var category = skill.getCategory();
            CategoryResponse categoryResponse;
            if (category != null){
                categoryResponse = CategoryMapper.INSTANCE.toCategoryResponse(category);
            }
            else {
                categoryResponse = new CategoryResponse(0L, "Undefined");
            }
            if (!categories.containsKey(categoryResponse)) {
                categories.put(categoryResponse, new HashSet<>());
            }
            categories.get(categoryResponse).add(skillResponse);
        }
        Set<CategorySkillResponse> responses = new HashSet<>();
        categories.forEach((key, value) -> responses.add(new CategorySkillResponse(key, value)));
        return responses;
    }

    public SkillResponse create(SkillRequest request) {
        Skill skill = SkillMapper.INSTANCE.toSkill(
                request,
                typeService.findById(request.typeId()),
                request.categoryId().map(categoryService::findById).orElse(null),
                scaleService.findById(request.scaleId()));
        var existingSkill = skillRepository.findByName(skill.getName());
        if (existingSkill.isPresent()) {
            //TODO Вернуть исключение
            return SkillMapper.INSTANCE.toSkillResponse(existingSkill.get());
        }
        skillRepository.save(skill);
        return SkillMapper.INSTANCE.toSkillResponse(skill);
    }
}
