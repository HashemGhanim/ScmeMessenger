package com.scme.messenger.dto.module;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModuleDto {
    @Pattern(regexp = "^\\d{6}$", message = "Module Id must be 6 digits")
    private String moduleId;
    @NotEmpty(message = "Name should not be empty")
    private String name;
}
