package com.scme.messenger.mapper;

import com.scme.messenger.dto.module.ModuleDto;
import com.scme.messenger.model.Module;
import org.springframework.stereotype.Component;

@Component
public class ModuleMapper {

    public Module getModule(ModuleDto moduleDto){
        Module module = Module.builder()
                .moduleId(moduleDto.getModuleId())
                .name(moduleDto.getName())
                .build();

        return module;
    }

    public ModuleDto getModuleDto(Module module){
        ModuleDto moduleDto = ModuleDto.builder()
                .moduleId(module.getModuleId())
                .name(module.getName())
                .build();

        return moduleDto;
    }
}
