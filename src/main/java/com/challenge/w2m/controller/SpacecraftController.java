package com.challenge.w2m.controller;

import com.challenge.w2m.dto.PaginatedResponseDto;
import com.challenge.w2m.dto.SpacecraftRequestDto;
import com.challenge.w2m.dto.SpacecraftResponseDto;
import com.challenge.w2m.service.SpacecraftService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/spacecraft")
public class SpacecraftController {

    private final SpacecraftService spacecraftService;

    public SpacecraftController(SpacecraftService spacecraftService) {
        this.spacecraftService = spacecraftService;
    }


    @Operation(summary = "Gets all spacecraft paginated")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = SpacecraftResponseDto.class))}
            ),
            @ApiResponse(responseCode = "404", description = "Spacecraft not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PaginatedResponseDto<SpacecraftResponseDto>> findAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "limit", defaultValue = "10") int limit
    ){
        PaginatedResponseDto<SpacecraftResponseDto> spacecraftPage = spacecraftService.findAll(page, limit);
        return ResponseEntity.ok(spacecraftPage);
    }


    @Operation(summary = "Get a Spacecraft by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Spacecraft found", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = SpacecraftResponseDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Spacecraft not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<SpacecraftResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(spacecraftService.findById(id));
    }

    @Operation(summary = "Get all Spacecraft that contain in their name the parameter sent ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Spacecraft found", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = SpacecraftResponseDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Spacecraft not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/findByName")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<SpacecraftResponseDto>> findByValue(@RequestParam String value) {
        return ResponseEntity.ok(spacecraftService.findByName(value));
    }

    @Operation(summary = "Create a Spacecraft")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "Spacecraft created"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody SpacecraftRequestDto spacecraftRequestDto){

        spacecraftService.create(spacecraftRequestDto);

    }

    @Operation(summary = "Update a Spacecraft")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Spacecraft updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "404", description = "Spacecraft not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<SpacecraftResponseDto> update(@PathVariable Long id, @RequestBody SpacecraftRequestDto spacecraftRequestDto) {
        return ResponseEntity.ok(spacecraftService.update(id, spacecraftRequestDto));
    }

    @Operation(summary = "Delete a Spacecraft by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Spacecraft deleted"),
            @ApiResponse(responseCode = "404", description = "Spacecraft not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        spacecraftService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
