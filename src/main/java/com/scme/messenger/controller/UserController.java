package com.scme.messenger.controller;

import com.scme.messenger.constants.ResponseConstants;
import com.scme.messenger.dto.ResponseDto;
import com.scme.messenger.dto.userdto.BlockUserDto;
import com.scme.messenger.dto.userdto.UserDTO;
import com.scme.messenger.services.IUserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Block;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "/user", produces = { MediaType.APPLICATION_JSON_VALUE })
@Validated
@RequiredArgsConstructor
public class UserController {

        private final IUserService userService;


        @GetMapping
        @PreAuthorize("hasRole('ROLE_ADMIN')")
        public ResponseEntity<?> getAllUsers() {

                List<UserDTO> users = userService.getAllUsers();

                return ResponseEntity.status(HttpStatus.OK)
                        .body(users);
        }

        @GetMapping("/{userId}/")
        @PreAuthorize("#userId == authentication.principal.username")
        public ResponseEntity<?> searchUsers(@RequestParam(defaultValue = "") String username,
                                             @PathVariable @Pattern(regexp = "^\\d{8}$", message = "User ID must be 8 digits") String userId) {

                Set<UserDTO> users = userService.searchUsers(username , userId);

                return ResponseEntity.status(HttpStatus.OK)
                        .body(users);
        }

        @GetMapping("/{userId}")
        public ResponseEntity<?> getUser(
                        @PathVariable @Pattern(regexp = "^\\d{8}$", message = "User ID must be 8 digits") String userId) {

                UserDTO user = userService.getUser(userId);

                return ResponseEntity.status(HttpStatus.OK)
                                .body(user);
        }

        @PostMapping
        @PreAuthorize("hasRole('ROLE_ADMIN')")
        public ResponseEntity<?> setUser(@Valid @RequestBody UserDTO user) {

                userService.setUser(user);

                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(ResponseDto.builder()
                                                .statusMsg(ResponseConstants.MESSAGE_201)
                                                .statusCode(ResponseConstants.STATUS_201)
                                                .build());
        }

        @DeleteMapping("/{userId}")
        @PreAuthorize("hasRole('ROLE_ADMIN')")
        public ResponseEntity<?> deleteUser(
                        @PathVariable @Pattern(regexp = "^\\d{8}$", message = "User ID must be 8 digits") String userId) {

                userService.deleteUser(userId);

                return ResponseEntity.status(HttpStatus.OK)
                                .body(ResponseDto.builder()
                                                .statusCode(ResponseConstants.STATUS_200)
                                                .statusMsg(ResponseConstants.MESSAGE_200)
                                                .build());
        }

        @PatchMapping
        @PreAuthorize("#userDTO.userId == authentication.principal.username")
        public ResponseEntity<?> updateUser(@Valid @RequestBody UserDTO userDTO) {
                UserDTO user = userService.updateUser(userDTO);

                return ResponseEntity.status(HttpStatus.OK)
                                .body(user);
        }

        @PostMapping("/{userId}/two-step")
        @PreAuthorize("#userId == authentication.principal.username")
        public ResponseEntity<?> setTwoStepVerification(
                        @PathVariable @Pattern(regexp = "^\\d{8}$", message = "User ID must be 8 digits") String userId) {

                userService.setTwoStepVerification(userId);

                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(ResponseDto.builder()
                                                .statusMsg(ResponseConstants.MESSAGE_201)
                                                .statusCode(ResponseConstants.STATUS_201)
                                                .build());
        }

        @DeleteMapping("/{userId}/two-step")
        @PreAuthorize("#userId == authentication.principal.username")
        public ResponseEntity<?> turnOffTwoStepVerification(
                        @PathVariable @Pattern(regexp = "^\\d{8}$", message = "User ID must be 8 digits") String userId) {

                userService.turnOffTwoStepVerification(userId);

                return ResponseEntity.status(HttpStatus.OK)
                                .body(ResponseDto.builder()
                                                .statusMsg(ResponseConstants.MESSAGE_200)
                                                .statusCode(ResponseConstants.STATUS_200)
                                                .build());
        }

        @PostMapping("/block")
        @PreAuthorize("#blockUserDto.senderId == authentication.principal.username")
        public ResponseEntity<?> blockUser(@Valid @RequestBody BlockUserDto blockUserDto){

                userService.blockUser(blockUserDto);

                return ResponseEntity.status(HttpStatus.OK)
                        .body(ResponseDto.builder()
                                .statusCode(ResponseConstants.STATUS_200)
                                .statusMsg(ResponseConstants.MESSAGE_200)
                                .build());
        }

        @PostMapping("/unblock")
        @PreAuthorize("#blockUserDto.senderId == authentication.principal.username")
        public ResponseEntity<?> unBlockUser(@Valid @RequestBody BlockUserDto blockUserDto){

                userService.unBlockUser(blockUserDto);

                return ResponseEntity.status(HttpStatus.OK)
                        .body(ResponseDto.builder()
                                .statusCode(ResponseConstants.STATUS_200)
                                .statusMsg(ResponseConstants.MESSAGE_200)
                                .build());
        }

}
