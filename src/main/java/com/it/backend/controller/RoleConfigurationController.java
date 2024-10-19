package com.it.backend.controller;

import com.it.backend.dto.NameAndDescriptionDto;
import com.it.backend.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/roles")
public class RoleConfigurationController {

    private final RoleService roleService;

    @PostMapping("new")
    public void createRole(@RequestBody NameAndDescriptionDto dto){
        roleService.createRole(dto);
    }

    public void getAllRoles(){}

    @PostMapping("hard_skills")
    public void addHardSkillsToRole(){}
}
