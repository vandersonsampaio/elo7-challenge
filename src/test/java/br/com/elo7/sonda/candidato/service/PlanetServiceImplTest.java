package br.com.elo7.sonda.candidato.service;

import br.com.elo7.sonda.candidato.model.entity.Planet;
import br.com.elo7.sonda.candidato.model.expection.EntityNotFoundException;
import br.com.elo7.sonda.candidato.model.expection.UniqueEntityException;
import br.com.elo7.sonda.candidato.model.repository.PlanetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlanetServiceImplTest {

    private static final String PLANET_NAME = "Planet";
    private static final int WIDTH = 5;
    private static final int HEIGHT = 6;
    @Mock
    private PlanetRepository repository;

    @InjectMocks
    private PlanetServiceImpl service;

    @Captor
    private ArgumentCaptor<Planet> planetCaptor;

    @Test
    void shouldCreateAPlanet() {
        when(repository.existsByName(PLANET_NAME)).thenReturn(false);

        service.create(PLANET_NAME, WIDTH, HEIGHT);
        verify(repository).save(planetCaptor.capture());

        assertThat(planetCaptor.getValue()).isNotNull();
        assertThat(planetCaptor.getValue().getName()).isEqualTo(PLANET_NAME);
        assertThat(planetCaptor.getValue().getWidth()).isEqualTo(WIDTH);
        assertThat(planetCaptor.getValue().getHeight()).isEqualTo(HEIGHT);
        assertThat(planetCaptor.getValue().getRegisterTime()).isNotNull();
        assertThat(planetCaptor.getValue().getUpdateTime()).isNull();
        assertThat(planetCaptor.getValue().getProbes()).isEmpty();

        verify(repository).existsByName(PLANET_NAME);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldThrowUniqueExceptionWhenCreatePlanetButNameIsUsing() {
        when(repository.existsByName(PLANET_NAME)).thenReturn(true);

        assertThrows(UniqueEntityException.class, () -> service.create(PLANET_NAME, WIDTH, HEIGHT));

        verify(repository).existsByName(PLANET_NAME);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldUpdateDimentions() {
        when(repository.findByName(PLANET_NAME)).thenReturn(Optional.of(generatePlanet()));

        service.updateDimentions(PLANET_NAME, WIDTH - 1, HEIGHT + 1);
        verify(repository).save(planetCaptor.capture());

        assertThat(planetCaptor.getValue()).isNotNull();
        assertThat(planetCaptor.getValue().getName()).isEqualTo(PLANET_NAME);
        assertThat(planetCaptor.getValue().getWidth()).isEqualTo(WIDTH - 1);
        assertThat(planetCaptor.getValue().getHeight()).isEqualTo(HEIGHT + 1);
        assertThat(planetCaptor.getValue().getRegisterTime()).isNotNull();
        assertThat(planetCaptor.getValue().getUpdateTime()).isNotNull();
        assertThat(planetCaptor.getValue().getProbes()).isEmpty();

        verify(repository).findByName(PLANET_NAME);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldGetPlanet() {
        Planet expected = generatePlanet();

        when(repository.findByName(PLANET_NAME)).thenReturn(Optional.of(expected));

        assertThat(service.getPlanet(PLANET_NAME)).isSameAs(expected);

        verify(repository).findByName(PLANET_NAME);
        verifyNoMoreInteractions(repository);

    }

    @Test
    void shouldThrowNotFoundExceptionWhenGetPlanet() {
        when(repository.findByName(PLANET_NAME)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.getPlanet(PLANET_NAME));

        verify(repository).findByName(PLANET_NAME);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldFindAllPlanets() {
        List<Planet> expected = List.of(generatePlanet());

        when(repository.findAll()).thenReturn(expected);

        assertThat(service.findAllPlanets()).isSameAs(expected);

        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }

    private Planet generatePlanet() {
        return new Planet(PLANET_NAME, WIDTH, HEIGHT);
    }
}