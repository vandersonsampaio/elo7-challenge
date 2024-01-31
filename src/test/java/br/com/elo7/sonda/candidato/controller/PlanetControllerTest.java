package br.com.elo7.sonda.candidato.controller;

import br.com.elo7.sonda.candidato.controller.dto.request.CreatePlanetRequest;
import br.com.elo7.sonda.candidato.controller.dto.request.UpdatePlanetRequest;
import br.com.elo7.sonda.candidato.model.entity.Planet;
import br.com.elo7.sonda.candidato.service.PlanetService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlanetControllerTest {

    private static final String PLANET_NAME = "PLANET";
    private static final int WIDTH = 5;
    private static final int HEIGHT = 6;

    @Mock
    private PlanetService service;
    @InjectMocks
    private PlanetController controller;

    @Test
    void shouldCreateAPlanet() {
        CreatePlanetRequest request = new CreatePlanetRequest(PLANET_NAME, WIDTH, HEIGHT);

        ResponseEntity<Planet> actual = controller.createPlanet(request);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(actual.getBody()).isNull();

        verify(service).create(PLANET_NAME, WIDTH, HEIGHT);
        verifyNoMoreInteractions(service);
    }

    @Test
    void shouldUpdateAPlanet() {
        UpdatePlanetRequest request = new UpdatePlanetRequest(WIDTH - 1, HEIGHT + 1);

        ResponseEntity<Planet> actual = controller.updatePlanet(PLANET_NAME, request);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody()).isNull();

        verify(service).updateDimentions(PLANET_NAME, WIDTH - 1, HEIGHT + 1);
        verifyNoMoreInteractions(service);
    }

    @Test
    void shouldGetAPlanet() {
        Planet expected = generatePlanet();

        when(service.getPlanet(PLANET_NAME)).thenReturn(expected);

        ResponseEntity<Planet> actual = controller.getPlanet(PLANET_NAME);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody()).isNotNull();
        assertThat(actual.getBody()).isSameAs(expected);

        verify(service).getPlanet(PLANET_NAME);
        verifyNoMoreInteractions(service);
    }

    @Test
    void shouldFindAllPlanets() {
        List<Planet> expected = List.of(generatePlanet());

        when(service.findAllPlanets()).thenReturn(expected);

        ResponseEntity<List<Planet>> actual = controller.findAllPlanets();

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody()).isNotNull();
        assertThat(actual.getBody()).isSameAs(expected);

        verify(service).findAllPlanets();
        verifyNoMoreInteractions(service);
    }

    private Planet generatePlanet() {
        return new Planet(PLANET_NAME, WIDTH, HEIGHT);
    }
}