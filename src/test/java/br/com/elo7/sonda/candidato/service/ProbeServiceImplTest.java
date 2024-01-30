package br.com.elo7.sonda.candidato.service;

import br.com.elo7.sonda.candidato.model.entity.Planet;
import br.com.elo7.sonda.candidato.model.entity.Probe;
import br.com.elo7.sonda.candidato.model.enums.Command;
import br.com.elo7.sonda.candidato.model.enums.Direction;
import br.com.elo7.sonda.candidato.model.expection.MovementNotPermittedException;
import br.com.elo7.sonda.candidato.model.expection.UniqueEntityException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProbeServiceImplTest {

    private static final String PLANET_NAME = "Planet";
    private static final String PROBE_NAME = "Probe";

    @Mock
    private PlanetService planetService;

    @InjectMocks
    private ProbeServiceImpl service;

    @Test
    void shouldCreateProbeWhenThereIsPlanet() {
        when(planetService.getPlanet(PLANET_NAME)).thenReturn(generatePlanet());

        service.create(PLANET_NAME, generateProbe());

        verify(planetService).getPlanet(PLANET_NAME);
        verify(planetService).save(any(Planet.class));
        verifyNoMoreInteractions(planetService);
    }

    @Test
    void shouldThrowUniqueExceptionWhenCreateProbeButPlanetAlreadyTheSameProbe() {
        Planet planet = generatePlanet();
        planet.addProbe(generateProbe());

        when(planetService.getPlanet(PLANET_NAME)).thenReturn(planet);

        assertThrows(UniqueEntityException.class, () -> service.create(PLANET_NAME, generateProbe()));

        verify(planetService).getPlanet(PLANET_NAME);
        verifyNoMoreInteractions(planetService);
    }

    @Test
    void shouldMoveProbe() {
        List<MovimentationCommand> commands = List.of(Command.M.getCommand(),
                Command.R.getCommand(), Command.M.getCommand(), Command.M.getCommand());
        Planet planet = generatePlanet();
        planet.addProbe(generateProbe());

        when(planetService.getPlanet(PLANET_NAME)).thenReturn(planet);

        Probe actual = service.move(PLANET_NAME, PROBE_NAME, commands);

        assertThat(actual.getName()).isEqualTo(PROBE_NAME);
        assertThat(actual.getDirection()).isEqualTo(Direction.E);
        assertThat(actual.getX()).isEqualTo(2);
        assertThat(actual.getY()).isEqualTo(1);

        verify(planetService).getPlanet(PLANET_NAME);
        verify(planetService).save(any(Planet.class));
        verifyNoMoreInteractions(planetService);
    }

    @Test
    void shouldThrowMovementNotPermittedWhenMoveProbeAcrossBorder() {
        List<MovimentationCommand> commands = List.of(Command.M.getCommand(),
                Command.L.getCommand(), Command.M.getCommand());
        Planet planet = generatePlanet();
        planet.addProbe(generateProbe());

        when(planetService.getPlanet(PLANET_NAME)).thenReturn(planet);

        assertThrows(MovementNotPermittedException.class,
                () -> service.move(PLANET_NAME, PROBE_NAME, commands));

        verify(planetService).getPlanet(PLANET_NAME);
        verifyNoMoreInteractions(planetService);
    }

    @Test
    void shouldGetProbe() {
        Planet planet = generatePlanet();
        Probe probe = generateProbe();
        planet.addProbe(probe);

        when(planetService.getPlanet(PLANET_NAME)).thenReturn(planet);

        assertThat(service.getProbe(PLANET_NAME, PROBE_NAME)).isSameAs(probe);

        verify(planetService).getPlanet(PLANET_NAME);
        verifyNoMoreInteractions(planetService);
    }

    @Test
    void shouldListAllProbes() {
        Planet planet = generatePlanet();
        Probe probe = generateProbe();
        planet.addProbe(probe);

        when(planetService.getPlanet(PLANET_NAME)).thenReturn(planet);

        List<Probe> actual = service.listAllProbes(PLANET_NAME);

        assertThat(actual).hasSize(1);
        assertThat(actual.get(0)).isSameAs(probe);

        verify(planetService).getPlanet(PLANET_NAME);
        verifyNoMoreInteractions(planetService);
    }

    @Test
    void shouldDepartureProbe() {
        Planet planet = generatePlanet();
        Probe probe = generateProbe();
        planet.addProbe(probe);

        when(planetService.getPlanet(PLANET_NAME)).thenReturn(planet);

        service.departureProbe(PLANET_NAME, PROBE_NAME);

        verify(planetService).getPlanet(PLANET_NAME);
        verify(planetService).save(any(Planet.class));
        verifyNoMoreInteractions(planetService);
    }

    private Probe generateProbe() {
        return new Probe(PROBE_NAME, 0, 0, Direction.N);
    }

    private Planet generatePlanet() {
        return new Planet(PLANET_NAME, 5, 5);
    }
}