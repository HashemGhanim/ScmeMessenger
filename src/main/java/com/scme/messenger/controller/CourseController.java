package com.scme.messenger.controller;

import com.scme.messenger.constants.ResponseConstants;
import com.scme.messenger.dto.ResponseDto;
import com.scme.messenger.dto.course.CourseDto;
import com.scme.messenger.dto.course.CourseIdDto;
import com.scme.messenger.dto.course.CoursePreviewResponseDto;
import com.scme.messenger.dto.course.CourseResponseDto;
import com.scme.messenger.services.ICourseService;
import com.scme.messenger.validations.AuthorizeCourseAccess;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/course", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class CourseController {

    private final ICourseService iCourseService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> create(@Valid @RequestBody CourseDto courseDto) throws Exception {
        iCourseService.create(courseDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.builder()
                        .statusCode(ResponseConstants.STATUS_201)
                        .statusMsg(ResponseConstants.MESSAGE_201)
                        .build());
    }

    @GetMapping("/{moduleId}/{courseId}")
    @AuthorizeCourseAccess
    public ResponseEntity<?> get(
            @PathVariable @Pattern(regexp = "^[1-9]$", message = "Course Id must be greater than zero") String courseId,
            @PathVariable @Pattern(regexp = "^\\d{6}$", message = "Module Id must be 6 digits") String moduleId,
            @RequestParam(name = "page" , defaultValue = "0") int page,
            @RequestParam(name = "size" , defaultValue = "20") int size
    ){

        CourseResponseDto courseDto = iCourseService.get(courseId , moduleId, page, size);
        return ResponseEntity.status(HttpStatus.OK)
                .body(courseDto);
    }

    @PatchMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> update(@Valid @RequestBody CourseDto courseDto) throws Exception {

        iCourseService.update(courseDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.builder()
                        .statusCode(ResponseConstants.STATUS_200)
                        .statusMsg(ResponseConstants.MESSAGE_200)
                        .build());
    }

    @DeleteMapping("/{moduleId}/{courseId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> delete(
            @PathVariable @Pattern(regexp = "^[1-9]$", message = "Course Id must be greater than zero") String courseId,
            @PathVariable @Pattern(regexp = "^\\d{6}$", message = "Module Id must be 6 digits") String moduleId
    ){
        iCourseService.delete(courseId , moduleId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.builder()
                        .statusCode(ResponseConstants.STATUS_200)
                        .statusMsg(ResponseConstants.MESSAGE_200)
                        .build());
    }

    @GetMapping("/{userId}")
    @PreAuthorize("#userId == authentication.principal.username && hasAnyRole('ROLE_DOCTOR' , 'ROLE_STUDENT')")
    public ResponseEntity<?> getAllCoursesOfStudent(
            @PathVariable @Pattern(regexp = "^\\d{8}$", message = "Student Id must be 8 digits") String userId
    ){
        Set<CoursePreviewResponseDto> courses = iCourseService.getAllCoursesOfUser(userId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(courses);
    }

    @PatchMapping("/stop")
    @PreAuthorize("#courseIdDto.instructorId == authentication.principal.username && hasRole('ROLE_DOCTOR')")
    public ResponseEntity<?> stopConversationOfGroup(@Valid @RequestBody CourseIdDto courseIdDto){

        iCourseService.stopConversationOfGroup(courseIdDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        ResponseDto.builder()
                                .statusCode(ResponseConstants.STATUS_200)
                                .statusMsg(ResponseConstants.MESSAGE_200)
                                .build()
                );
    }

    @PatchMapping("/allow")
    @PreAuthorize("#courseIdDto.instructorId == authentication.principal.username && hasRole('ROLE_DOCTOR')")
    public ResponseEntity<?> allowConversationOfGroup(@Valid @RequestBody CourseIdDto courseIdDto){

        iCourseService.allowConversationOfGroup(courseIdDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        ResponseDto.builder()
                                .statusCode(ResponseConstants.STATUS_200)
                                .statusMsg(ResponseConstants.MESSAGE_200)
                                .build()
                );
    }
}
