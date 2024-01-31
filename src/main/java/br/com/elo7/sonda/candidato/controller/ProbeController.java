package br.com.elo7.sonda.candidato.controller;

import br.com.elo7.sonda.candidato.controller.dto.mapper.MovimentProbeMapper;
import br.com.elo7.sonda.candidato.controller.dto.mapper.ProbeMapper;
import br.com.elo7.sonda.candidato.controller.dto.request.CreateProbeRequest;
import br.com.elo7.sonda.candidato.controller.dto.request.MovimentProbeRequest;
import br.com.elo7.sonda.candidato.model.entity.Probe;
import br.com.elo7.sonda.candidato.model.expection.ErrorResponse;
import br.com.elo7.sonda.candidato.model.expection.SimpleErrorResponse;
import br.com.elo7.sonda.candidato.service.ProbeService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/planet/{planetName}/probe")
@Tag(name = "Probe", description = "Probe management APIs")
public class ProbeController {

    private final ProbeService service;
    private final ProbeMapper mapper;

    @Operation(
            summary = "Lend a Probe in a Planet",
            description = "Create a Probe in a Planet.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Probe.class), mediaType = MediaType.APPLICATION_JSON_VALUE) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = SimpleErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE) }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Probe> lend(@PathVariable("planetName") String planetName,
                     @Valid @RequestBody CreateProbeRequest request) {
        log.info("m=lend, planet={}, probe={}", planetName, request.getName());
        return new ResponseEntity<>(service.create(planetName, mapper.from(request)), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Retrieve a Probe in a Planet",
            description = "Get a Probe in a Planet.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Probe.class), mediaType = MediaType.APPLICATION_JSON_VALUE) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = SimpleErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE) }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping(value = "/{probeName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Probe> get(@PathVariable("planetName") String planetName,
                                     @PathVariable("probeName") String probeName) {
        log.info("m=get, planet={}, probe={}", planetName, probeName);
        return ResponseEntity.ok(service.getProbe(planetName, probeName));
    }

    @Operation(
            summary = "List all Probes in a Planet",
            description = "List all Probes in a Planet.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Probe.class), mediaType = MediaType.APPLICATION_JSON_VALUE) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = SimpleErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE) }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Probe>> findAll(@PathVariable("planetName") String planetName) {
        log.info("m=findAll, planet={}", planetName);
        return ResponseEntity.ok(service.listAllProbes(planetName));

    }

    @Operation(
            summary = "Move a Probe in a Planet",
            description = "Move a Probe in a Planet.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Probe.class), mediaType = MediaType.APPLICATION_JSON_VALUE) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = SimpleErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE) }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping(value = "/{probeName}/move", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Probe> move(@PathVariable("planetName") String planetName,
                                      @PathVariable("probeName") String probeName,
                                      @Valid @RequestBody MovimentProbeRequest request) {
        log.info("m=move, planet={}, probe={}", planetName, probeName);
        return ResponseEntity.ok(service.move(planetName, probeName, MovimentProbeMapper.from(request)));
    }

    @Operation(
            summary = "Departure a Probe in a Planet",
            description = "Make a Probe leave the Planet.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(), mediaType = MediaType.APPLICATION_JSON_VALUE) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = SimpleErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE) }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping(value = "/{probeName}/departure")
    public ResponseEntity<Void> departure(@PathVariable("planetName") String planetName,
                                          @PathVariable("probeName") String probeName) {
        log.info("m=departure, planet={}, probe={}", planetName, probeName);
        service.departureProbe(planetName, probeName);

        return ResponseEntity.ok().build();
    }
}
