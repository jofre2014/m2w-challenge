package com.challenge.w2m.controller;

import com.challenge.w2m.dto.ErrorDto;
import com.challenge.w2m.dto.MediaRequestDto;
import com.challenge.w2m.dto.UserDto;
import com.challenge.w2m.dto.UserRequest;
import com.challenge.w2m.service.impl.SecurityUserDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final SecurityUserDetailService userDetailService;

    public UserController(SecurityUserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users found"),
            @ApiResponse(responseCode = "404", description = "Users not found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            })
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<UserDto>> getAllUsers(){

        return new ResponseEntity<>(userDetailService.getAllUsers(), HttpStatus.OK);
    }


    @Operation(summary = "Create User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserRequest.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "500", description = "Interval server error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            })
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('WRITE')")
    public void createUser(@RequestBody UserRequest userRequest){
        userDetailService.create(userRequest);

    }


}
