package com.scme.messenger.controller;

import com.scme.messenger.services.IGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequiredArgsConstructor
public class GroupController {

    private final IGroupService iGroupService;

}
