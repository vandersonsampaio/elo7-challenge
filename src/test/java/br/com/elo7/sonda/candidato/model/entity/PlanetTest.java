package br.com.elo7.sonda.candidato.model.entity;

import br.com.elo7.sonda.candidato.model.enums.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PlanetTest {

    private Planet planet;

    @BeforeEach
    public void init() {
        planet = new Planet("Planet one", 5, 5);
    }

    @Test
    void shouldThrowExceptionWhenResizeDimentionsHasSameWidthAndHeight() {
        assertThrows(RuntimeException.class, () -> planet.resizeDimentions(5, 5));
    }

    @Test
    void shouldResizeDimentionsWhenSameWidthAndIncHeight() {
        planet.resizeDimentions(5, 6);

        assertThat(planet.getWidth()).isEqualTo(5);
        assertThat(planet.getHeight()).isEqualTo(6);
    }

    @Test
    void shouldResizeDimentionsWhenSameWidthAndDecHeight() {
        planet.resizeDimentions(5, 4);

        assertThat(planet.getWidth()).isEqualTo(5);
        assertThat(planet.getHeight()).isEqualTo(4);
    }

    @Test
    void shouldThrowExceptionWhenResizeDimentionsHasSameWidthAndDecHeightAndAnyProbeCrossTheBoard() {
        Probe probe = new Probe("Probe Y Board", 1, 4, Direction.N);
        planet.addProbe(probe);

        assertThrows(RuntimeException.class, () -> planet.resizeDimentions(5, 4));
    }

    @Test
    void shouldResizeDimentionsWhenIncWidthAndSameHeight() {
        planet.resizeDimentions(6, 5);

        assertThat(planet.getWidth()).isEqualTo(6);
        assertThat(planet.getHeight()).isEqualTo(5);
    }

    @Test
    void shouldResizeDimentionsWhenDecWidthAndSameHeight() {
        planet.resizeDimentions(4, 5);

        assertThat(planet.getWidth()).isEqualTo(4);
        assertThat(planet.getHeight()).isEqualTo(5);
    }

    @Test
    void shouldThrowExceptionWhenResizeDimentionsHasDecWidthAndSameHeightAndAnyProbeCrossTheBoard() {
        Probe probe = new Probe("Probe X Board", 4, 1,Direction.E);
        planet.addProbe(probe);

        assertThrows(RuntimeException.class, () -> planet.resizeDimentions(4, 5));
    }

    @Test
    void shouldResizeDimentionsWhenIncWidthAndDecHeight() {
        planet.resizeDimentions(6, 4);

        assertThat(planet.getWidth()).isEqualTo(6);
        assertThat(planet.getHeight()).isEqualTo(4);
    }

    @Test
    void shouldThrowExceptionWhenResizeDimentionsHasIncWidthAndDecHeightAndAnyProbeCrossTheBoard() {
        Probe probe = new Probe("Probe Y Board", 1, 4, Direction.S);
        planet.addProbe(probe);

        assertThrows(RuntimeException.class, () -> planet.resizeDimentions(6, 4));
    }

    @Test
    void shouldResizeDimentionsWhenDecWidthAndIncHeight() {
        planet.resizeDimentions(4, 6);

        assertThat(planet.getWidth()).isEqualTo(4);
        assertThat(planet.getHeight()).isEqualTo(6);
    }

    @Test
    void shouldThrowExceptionWhenResizeDimentionsHasDecWidthAndIncHeightAndAnyProbeCrossTheBoard() {
        Probe probe = new Probe("Probe Y Board", 4, 1, Direction.W);
        planet.addProbe(probe);

        assertThrows(RuntimeException.class, () -> planet.resizeDimentions(4, 6));
    }

    @Test
    void shouldGetProbWhenThereIsProbeName() {
        String name = "ProbeName";
        Probe probe = new Probe(name, 1, 1, Direction.N);
        planet.addProbe(probe);

        assertThat(planet.getProb(name)).isSameAs(probe);
    }

    @Test
    void shouldThrowExceptionWhenThereIsNotProbeName() {
        assertThrows(RuntimeException.class, () -> planet.getProb("Empty"));
    }

    @Test
    void shouldAddProbe() {
        String name = "Probe";
        Probe probe = new Probe(name, 1, 1, Direction.N);
        planet.addProbe(probe);

        assertThat(planet.getProb(name)).isSameAs(probe);
    }

    @Test
    void shouldThrowExceptionWhenAddProbeAndThereIsProbeNameAlready() {
        Probe probe = new Probe("Same Probe", 1, 1, Direction.N);
        planet.addProbe(probe);
        assertThrows(RuntimeException.class, () -> planet.addProbe(probe));
    }

    @Test
    void shouldThrowExceptionWhenAddProbeButPositionIsBusy() {
        Probe probe = new Probe("Probe One", 1, 1, Direction.N);
        planet.addProbe(probe);

        Probe samePositionProbe = new Probe("Probe Same Position", 1, 1, Direction.S);
        assertThrows(RuntimeException.class, () -> planet.addProbe(samePositionProbe));
    }

    @Test
    void shouldReturnTrueWhenPositionIsEmpty() {
        assertThat(planet.positionEmpty(1, 1)).isTrue();
    }

    @Test
    void shouldReturnTrueWhenPositionIsBusy() {
        Probe probe = new Probe("Probe One", 1, 1, Direction.N);
        planet.addProbe(probe);

        assertThat(planet.positionEmpty(1, 1)).isFalse();
    }
}