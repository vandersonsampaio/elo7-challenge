package br.com.elo7.sonda.candidato.controller;

import br.com.elo7.sonda.candidato.controller.dto.request.CreatePlanetRequest;
import br.com.elo7.sonda.candidato.controller.dto.request.UpdatePlanetRequest;
import br.com.elo7.sonda.candidato.model.entity.Planet;
import br.com.elo7.sonda.candidato.model.expection.ErrorResponse;
import br.com.elo7.sonda.candidato.model.expection.SimpleErrorResponse;
import br.com.elo7.sonda.candidato.service.PlanetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/planet")
@Tag(name = "Planet", description = "Planet management APIs")
public class PlanetController {

    private final PlanetService service;

    @Operation(
            summary = "Create a Planet",
            description = "Create a Planet object by specifying name.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Planet.class), mediaType = MediaType.APPLICATION_JSON_VALUE) }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Planet> createPlanet(@Valid @RequestBody CreatePlanetRequest request) {
        log.info("m=createPlanet, planet={}", request.getName());
        return new ResponseEntity<>(service.create(request.getName(), request.getWidth(), request.getHeight()),
                HttpStatus.CREATED);
    }

    @Operation(
            summary = "Resize a Planet",
            description = "Update the size of a Planet object by specifying name.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Planet.class), mediaType = MediaType.APPLICATION_JSON_VALUE) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = SimpleErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE) }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PutMapping(value = "/{name}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Planet> updatePlanet(@PathVariable String name, @Valid @RequestBody UpdatePlanetRequest request) {
        log.info("m=updatePlanet, planet={}", name);
        return ResponseEntity.ok(service.updateDimentions(name, request.getWidth(), request.getHeight()));
    }

    @Operation(
            summary = "Retrieve a Planet",
            description = "Get a Planet object by specifying name.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Planet.class), mediaType = MediaType.APPLICATION_JSON_VALUE) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = SimpleErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping(value = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Planet> getPlanet(@PathVariable String name) {
        log.info("m=getPlanet, planet={}", name);
        return ResponseEntity.ok(service.getPlanet(name));
    }

    @Operation(
            summary = "Find All Planets",
            description = "Retrieve all Planets in database.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Planet.class), mediaType = MediaType.APPLICATION_JSON_VALUE) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Planet>> findAllPlanets() {
        log.info("m=findAllPlanets");
        return ResponseEntity.ok(service.findAllPlanets());
    }
}
