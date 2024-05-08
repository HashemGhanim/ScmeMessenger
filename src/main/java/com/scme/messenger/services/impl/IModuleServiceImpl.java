package com.scme.messenger.services.impl;

import com.scme.messenger.constants.ResponseConstants;
import com.scme.messenger.dto.module.ModuleDto;
import com.scme.messenger.exception.BadRequestException;
import com.scme.messenger.mapper.ModuleMapper;
import com.scme.messenger.model.Module;
import com.scme.messenger.repository.ModuleRepo;
import com.scme.messenger.services.IModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IModuleServiceImpl implements IModuleService {

    private final ModuleRepo moduleRepo;

    private final ModuleMapper moduleMapper;

    @Override
    public void create(ModuleDto moduleDto) {
        if(moduleRepo.existsById(moduleDto.getModuleId()))
            throw new BadRequestException(ResponseConstants.MODULE_IS_EXIST);

        Module module = moduleMapper.getModule(moduleDto);
        moduleRepo.save(module);
    }

    @Override
    public void delete(String moduleId) {
        moduleRepo.deleteById(moduleId);
    }

    @Override
    public ModuleDto get(String moduleId) {
        Module module = Optional.of(moduleRepo.getReferenceById(moduleId))
                .orElseThrow(()-> new BadRequestException(ResponseConstants.MODULE_NOT_FOUND));
        ModuleDto moduleDto = moduleMapper.getModuleDto(module);
        return moduleDto;
    }

    @Override
    public ModuleDto edit(ModuleDto moduleDto) {
        Module module = Optional.of(moduleRepo.getReferenceById(moduleDto.getModuleId()))
                .orElseThrow(()->new BadRequestException(ResponseConstants.MODULE_NOT_FOUND));

        module.setName(module.getName());

        moduleRepo.save(module);

        return moduleDto;
    }

}
