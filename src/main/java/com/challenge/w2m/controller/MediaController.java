package com.challenge.w2m.controller;

import com.challenge.w2m.dto.MediaRequestDto;
import com.challenge.w2m.dto.MediaResponseDto;
import com.challenge.w2m.service.MediaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/media")
public class MediaController {

    private final MediaService mediaService;

    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @Operation(summary = "Create a Media")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Media created"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "500", description = "Interval server error")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody MediaRequestDto mediaRequestDto){
        mediaService.create(mediaRequestDto);
    }

    @Operation(summary = "Get all media")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Media found"),
            @ApiResponse(responseCode = "404", description = "Media not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MediaResponseDto> findAll(){
        return mediaService.findAll();
    }


}
