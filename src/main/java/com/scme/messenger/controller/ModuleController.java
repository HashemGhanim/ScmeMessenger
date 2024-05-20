package com.scme.messenger.controller;

import com.scme.messenger.constants.ResponseConstants;
import com.scme.messenger.dto.ResponseDto;
import com.scme.messenger.dto.module.ModuleDto;
import com.scme.messenger.services.IModuleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/module", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class ModuleController {

    private final IModuleService iModuleService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createModule(@Valid @RequestBody ModuleDto moduleDto){

        iModuleService.create(moduleDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.builder()
                        .statusCode(ResponseConstants.STATUS_201)
                        .statusMsg(ResponseConstants.MESSAGE_201)
                        .build());
    }

    @PatchMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> editModule(@Valid @RequestBody ModuleDto moduleDto){

        iModuleService.edit(moduleDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.builder()
                        .statusCode(ResponseConstants.STATUS_200)
                        .statusMsg(ResponseConstants.MESSAGE_200)
                        .build());
    }

    @GetMapping("/{moduleId}")
    public ResponseEntity<?> getModule(
            @PathVariable @Pattern(regexp = "^\\d{6}$", message = "Module Id must be 6 digits") String moduleId
    ){

        ModuleDto moduleDto = iModuleService.get(moduleId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(moduleDto);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllModules(){

        List<ModuleDto> moduleDto = iModuleService.getAllModules();

        return ResponseEntity.status(HttpStatus.OK)
                .body(moduleDto);
    }

    @DeleteMapping("/{moduleId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteModule(
            @PathVariable @Pattern(regexp = "^\\d{6}$", message = "Module Id must be 6 digits") String moduleId
    ){
        iModuleService.delete(moduleId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.builder()
                        .statusCode(ResponseConstants.STATUS_200)
                        .statusMsg(ResponseConstants.MESSAGE_200)
                        .build());
    }
}
