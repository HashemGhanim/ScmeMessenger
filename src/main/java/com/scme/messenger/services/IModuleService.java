package com.scme.messenger.services;

import com.scme.messenger.dto.module.ModuleDto;

import java.util.List;

public interface IModuleService {
    void create(ModuleDto moduleDto);
    void delete(String moduleId);
    ModuleDto get(String moduleId);
    ModuleDto edit(ModuleDto moduleDto);

    List<ModuleDto> getAllModules();
}
