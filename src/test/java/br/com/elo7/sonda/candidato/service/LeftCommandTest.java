package br.com.elo7.sonda.candidato.service;

import br.com.elo7.sonda.candidato.model.entity.Planet;
import br.com.elo7.sonda.candidato.model.entity.Probe;
import br.com.elo7.sonda.candidato.model.enums.Direction;
import br.com.elo7.sonda.candidato.model.expection.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;

class LeftCommandTest {

    private MovimentationCommand command;

    @BeforeEach
    public void init() {
        command = new LeftCommand();
    }

    @Test
    void shouldThrowExceptionWhenProbeNotFoundInAction() {
        String nameProbe = "empty";
        Planet planet = new Planet("Planet", 1, 1);

        assertThrows(EntityNotFoundException.class, () -> command.action(planet, nameProbe));
    }

    @Test
    void shouldTurnLeft() {
        String nameProbe = "Probe";
        Planet planet = new Planet("Planet", 1, 1);
        Probe probe = new Probe(nameProbe, 0, 0, Direction.N);
        planet.addProbe(probe);

        command.action(planet, nameProbe);
        assertThat(probe.getDirection()).isEqualTo(Direction.W);
    }
}