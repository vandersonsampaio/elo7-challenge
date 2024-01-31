package br.com.elo7.sonda.candidato.controller;

import br.com.elo7.sonda.candidato.controller.dto.mapper.ProbeMapperImpl;
import br.com.elo7.sonda.candidato.controller.dto.request.CreateProbeRequest;
import br.com.elo7.sonda.candidato.controller.dto.request.MovimentProbeRequest;
import br.com.elo7.sonda.candidato.model.entity.Probe;
import br.com.elo7.sonda.candidato.model.enums.Direction;
import br.com.elo7.sonda.candidato.service.LeftCommand;
import br.com.elo7.sonda.candidato.service.MoveCommand;
import br.com.elo7.sonda.candidato.service.MovimentationCommand;
import br.com.elo7.sonda.candidato.service.ProbeService;
import br.com.elo7.sonda.candidato.service.RightCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProbeControllerTest {

    private static final String PLANET_NAME = "Planet";
    private static final String PROBE_NAME = "Probe";

    @Mock
    private ProbeService service;

    @Captor
    private ArgumentCaptor<Probe> probeCaptor;
    @Captor
    private ArgumentCaptor<List<MovimentationCommand>> commandCaptor;

    private ProbeController controller;

    @BeforeEach
    public void init() {
        controller = new ProbeController(service, new ProbeMapperImpl());
    }

    @Test
    void shouldLendProbeInAPlanet() {
        when(service.create(eq(PLANET_NAME), any(Probe.class))).thenReturn(generateProbe());

        ResponseEntity<Probe> actual = controller.lend(PLANET_NAME, new CreateProbeRequest(PROBE_NAME, 1, 1, Direction.N));

        verify(service).create(eq(PLANET_NAME), probeCaptor.capture());

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(actual.getBody()).isNotNull();
        assertThat(actual.getBody().getName()).isEqualTo(probeCaptor.getValue().getName());
        assertThat(actual.getBody().getX()).isEqualTo(probeCaptor.getValue().getX());
        assertThat(actual.getBody().getY()).isEqualTo(probeCaptor.getValue().getY());
        assertThat(actual.getBody().getDirection()).isEqualTo(probeCaptor.getValue().getDirection());
        assertThat(actual.getBody().getRegisterTime()).isNotNull();
        assertThat(actual.getBody().getUpdateTime()).isNull();

        verifyNoMoreInteractions(service);
    }

    @Test
    void shouldGetAProbe() {
        Probe expected = generateProbe();

        when(service.getProbe(PLANET_NAME, PROBE_NAME)).thenReturn(expected);

        ResponseEntity<Probe> actual = controller.get(PLANET_NAME, PROBE_NAME);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody()).isNotNull();
        assertThat(actual.getBody()).isSameAs(expected);

        verify(service).getProbe(PLANET_NAME, PROBE_NAME);
        verifyNoMoreInteractions(service);
    }

    @Test
    void shouldFindAllProbesInAPlanet() {
        List<Probe> expected = List.of(generateProbe());

        when(service.listAllProbes(PLANET_NAME)).thenReturn(expected);

        ResponseEntity<List<Probe>> actual = controller.findAll(PLANET_NAME);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody()).isNotNull();
        assertThat(actual.getBody()).isSameAs(expected);

        verify(service).listAllProbes(PLANET_NAME);
        verifyNoMoreInteractions(service);
    }

    @Test
    void shouldMoveProbeInAPlanet() {
        MovimentProbeRequest request = new MovimentProbeRequest("MLMMRMLLMRRM");

        when(service.move(eq(PLANET_NAME), eq(PROBE_NAME), any())).thenReturn(generateProbe());

        ResponseEntity<Probe> actual = controller.move(PLANET_NAME, PROBE_NAME, request);

        verify(service).move(eq(PLANET_NAME), eq(PROBE_NAME), commandCaptor.capture());

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody()).isNotNull();

        assertThat(commandCaptor.getValue()).hasSize(12);
        assertThat(commandCaptor.getValue().get(0)).isInstanceOf(MoveCommand.class);
        assertThat(commandCaptor.getValue().get(1)).isInstanceOf(LeftCommand.class);
        assertThat(commandCaptor.getValue().get(2)).isInstanceOf(MoveCommand.class);
        assertThat(commandCaptor.getValue().get(3)).isInstanceOf(MoveCommand.class);
        assertThat(commandCaptor.getValue().get(4)).isInstanceOf(RightCommand.class);
        assertThat(commandCaptor.getValue().get(5)).isInstanceOf(MoveCommand.class);
        assertThat(commandCaptor.getValue().get(6)).isInstanceOf(LeftCommand.class);
        assertThat(commandCaptor.getValue().get(7)).isInstanceOf(LeftCommand.class);
        assertThat(commandCaptor.getValue().get(8)).isInstanceOf(MoveCommand.class);
        assertThat(commandCaptor.getValue().get(9)).isInstanceOf(RightCommand.class);
        assertThat(commandCaptor.getValue().get(10)).isInstanceOf(RightCommand.class);
        assertThat(commandCaptor.getValue().get(11)).isInstanceOf(MoveCommand.class);

        verifyNoMoreInteractions(service);
    }

    @Test
    void shouldDepartureProbe() {
        ResponseEntity<Void> actual = controller.departure(PLANET_NAME, PROBE_NAME);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody()).isNull();

        verify(service).departureProbe(PLANET_NAME, PROBE_NAME);
        verifyNoMoreInteractions(service);
    }

    private Probe generateProbe() {
        return new Probe(PROBE_NAME, 1, 1, Direction.N);
    }
}