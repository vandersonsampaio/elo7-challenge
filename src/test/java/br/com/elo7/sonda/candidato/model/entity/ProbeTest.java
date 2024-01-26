package br.com.elo7.sonda.candidato.model.entity;

import br.com.elo7.sonda.candidato.model.enums.Direction;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProbeTest {

    @Test
    void turnLeft() {
        Probe probe = new Probe("Name", 0, 0, Direction.N);

        probe.turnLeft();
        assertThat(probe.getDirection()).isEqualTo(Direction.W);

        probe.turnLeft();
        assertThat(probe.getDirection()).isEqualTo(Direction.S);

        probe.turnLeft();
        assertThat(probe.getDirection()).isEqualTo(Direction.E);

        probe.turnLeft();
        assertThat(probe.getDirection()).isEqualTo(Direction.N);
    }

    @Test
    void turnRight() {
        Probe probe = new Probe("Name", 0, 0, Direction.N);

        probe.turnRight();
        assertThat(probe.getDirection()).isEqualTo(Direction.E);

        probe.turnRight();
        assertThat(probe.getDirection()).isEqualTo(Direction.S);

        probe.turnRight();
        assertThat(probe.getDirection()).isEqualTo(Direction.W);

        probe.turnRight();
        assertThat(probe.getDirection()).isEqualTo(Direction.N);
    }

    @Test
    void move() {
        Probe probeN = new Probe("Name", 0, 0, Direction.N);
        Probe probeW = new Probe("Name", 0, 0, Direction.W);
        Probe probeS = new Probe("Name", 0, 0, Direction.S);
        Probe probeE = new Probe("Name", 0, 0, Direction.E);

        probeN.move();
        assertThat(probeN.getX()).isZero();
        assertThat(probeN.getY()).isEqualTo(1);

        probeW.move();
        assertThat(probeW.getX()).isEqualTo(-1);
        assertThat(probeW.getY()).isZero();

        probeS.move();
        assertThat(probeS.getX()).isZero();
        assertThat(probeS.getY()).isEqualTo(-1);

        probeE.move();
        assertThat(probeE.getX()).isEqualTo(1);
        assertThat(probeE.getY()).isZero();
    }
}