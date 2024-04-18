package com.scme.messenger.controller;

import com.scme.messenger.constants.ResponseConstants;
import com.scme.messenger.dto.ResponseDto;
import com.scme.messenger.model.Image;
import com.scme.messenger.services.IImageService;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;

import org.apache.coyote.BadRequestException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping(path = "/image")
@Validated
@RequiredArgsConstructor
public class ImageController {

    private final IImageService imageService;

    @PostMapping
    @PreAuthorize("#id == authentication.principal.username")
    public ResponseEntity<?> setImage(
            @RequestPart("file") MultipartFile file,
            @RequestPart("id") @Pattern(regexp = "^\\d{8}$", message = "ID must be 8 digits") String id)
            throws Exception {

        imageService.setImage(file, id);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.builder()
                        .statusCode(ResponseConstants.STATUS_201)
                        .statusMsg(ResponseConstants.MESSAGE_201)
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getImage(
            @PathVariable @Pattern(regexp = "^\\d{8}$", message = "ID must be 8 digits") String id)
            throws BadRequestException {

        Image image = imageService.getImage(id);

        ByteArrayResource body = new ByteArrayResource(image.getData());

        return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.CONTENT_TYPE, image.getMime_type()).body(body);
    }
}
