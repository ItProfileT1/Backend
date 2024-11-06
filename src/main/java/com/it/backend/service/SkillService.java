package com.it.backend.service;

import com.it.backend.dto.request.SkillRequest;
import com.it.backend.dto.response.CategoryResponse;
import com.it.backend.dto.response.TypeResponse;
import com.it.backend.dto.response.SkillResponse;
import com.it.backend.entity.Category;
import com.it.backend.entity.Skill;
import com.it.backend.entity.Type;
import com.it.backend.exception.entity.EntityNotFoundException;
import com.it.backend.mapper.CategoryMapper;
import com.it.backend.mapper.SkillMapper;
import com.it.backend.mapper.skills.TypeMapper;
import com.it.backend.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = true)
    public Map<TypeResponse, Map<CategoryResponse, Set<SkillResponse>>> findAll(){
        Map<Type, Map<Category, Set<Skill>>> skills = new HashMap<>();
        for (Skill skill : skillRepository.findAll()) {
            var type = skill.getType();
            Category category = new Category();
            if (skill.getCategory() == null) {
                category.setId(1L);
                category.setName("Undefined");
                category.setType(type);
            }
            else {
                category = skill.getCategory();
            }
            skills.putIfAbsent(type, new HashMap<>());
            skills.get(type).putIfAbsent(category, new HashSet<>());
            skills.get(type).get(category).add(skill);
        }
        Map<TypeResponse, Map<CategoryResponse, Set<SkillResponse>>> typeCategoriesSkillsResponses = new HashMap<>();
        skills.forEach((curType, categoryCategorySkill) -> {
            Map<CategoryResponse, Set<SkillResponse>> categorySkillResponses = new HashMap<>();
            categoryCategorySkill.forEach((curCategory, curSkills) -> {
                Set<SkillResponse> skillResponses = new HashSet<>();
                for (Skill curSkill : curSkills) {
                    var skillResponse = SkillMapper.INSTANCE.toSkillResponse(curSkill);
                    skillResponses.add(skillResponse);
                }
                var categoryResponse = CategoryMapper.INSTANCE.toCategoryResponse(curCategory);
                categorySkillResponses.put(categoryResponse, skillResponses);
            });
            var typeResponse = TypeMapper.INSTANCE.toTypeResponse(curType);
            typeCategoriesSkillsResponses.put(typeResponse, categorySkillResponses);
        });

        return typeCategoriesSkillsResponses;
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
