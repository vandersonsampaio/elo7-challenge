package br.com.elo7.sonda.candidato.service;

import br.com.elo7.sonda.candidato.model.entity.Planet;
import br.com.elo7.sonda.candidato.model.entity.Probe;
import br.com.elo7.sonda.candidato.model.enums.Direction;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MoveCommandTest {

    @Test
    void action() {
        String nameProbe = "Probe";
        int initX = 0;
        int initY = 0;
        Planet planet = new Planet("Planet", 2, 2);
        Probe probe = new Probe(nameProbe, initX, initY, Direction.N);
        planet.addProbe(probe);

        new MoveCommand().action(planet, nameProbe);

        assertThat(probe.getDirection()).isEqualTo(Direction.N);
        assertThat(probe.getY()).isEqualTo(1);
        assertThat(probe.getX()).isZero();
        assertThat(planet.positionEmpty(initX, initY)).isTrue();
    }
}