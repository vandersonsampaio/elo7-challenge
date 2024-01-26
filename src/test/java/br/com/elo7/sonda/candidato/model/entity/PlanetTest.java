package br.com.elo7.sonda.candidato.model.entity;

import br.com.elo7.sonda.candidato.model.enums.Direction;
import br.com.elo7.sonda.candidato.model.expection.EntityNotFoundException;
import br.com.elo7.sonda.candidato.model.expection.MovementNotPermittedException;
import br.com.elo7.sonda.candidato.model.expection.ResizeMapException;
import br.com.elo7.sonda.candidato.model.expection.SpaceNotAvailableException;
import br.com.elo7.sonda.candidato.model.expection.UniqueEntityException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PlanetTest {

    private static final int WIDTH = 5;
    private static final int HEIGHT = 5;
    private Planet planet;


    @BeforeEach
    public void init() {
        planet = new Planet("Planet one", WIDTH, HEIGHT);
    }

    @Test
    void shouldThrowExceptionWhenResizeDimentionsHasSameWidthAndHeight() {
        assertThrows(ResizeMapException.class, () -> planet.resizeMap(WIDTH, HEIGHT));
    }

    @Test
    void shouldResizeDimentionsWhenSameWidthAndIncHeight() {
        planet.resizeMap(WIDTH, HEIGHT + 1);

        assertThat(planet.getWidth()).isEqualTo(WIDTH);
        assertThat(planet.getHeight()).isEqualTo(HEIGHT + 1);
    }

    @Test
    void shouldResizeDimentionsWhenSameWidthAndDecHeight() {
        planet.resizeMap(WIDTH, HEIGHT - 1);

        assertThat(planet.getWidth()).isEqualTo(WIDTH);
        assertThat(planet.getHeight()).isEqualTo(HEIGHT - 1);
    }

    @Test
    void shouldThrowExceptionWhenResizeDimentionsHasSameWidthAndDecHeightAndAnyProbeCrossTheBoard() {
        Probe probe = new Probe("Probe Y Board", 1, 4, Direction.N);
        planet.addProbe(probe);

        assertThrows(ResizeMapException.class, () -> planet.resizeMap(WIDTH, HEIGHT - 1));
    }

    @Test
    void shouldResizeDimentionsWhenIncWidthAndSameHeight() {
        planet.resizeMap(WIDTH + 1, HEIGHT);

        assertThat(planet.getWidth()).isEqualTo(WIDTH + 1);
        assertThat(planet.getHeight()).isEqualTo(HEIGHT);
    }

    @Test
    void shouldResizeDimentionsWhenDecWidthAndSameHeight() {
        planet.resizeMap(WIDTH - 1, HEIGHT);

        assertThat(planet.getWidth()).isEqualTo(WIDTH - 1);
        assertThat(planet.getHeight()).isEqualTo(HEIGHT);
    }

    @Test
    void shouldThrowExceptionWhenResizeDimentionsHasDecWidthAndSameHeightAndAnyProbeCrossTheBoard() {
        Probe probe = new Probe("Probe X Board", 4, 1,Direction.E);
        planet.addProbe(probe);

        assertThrows(ResizeMapException.class, () -> planet.resizeMap(WIDTH - 1, HEIGHT));
    }

    @Test
    void shouldResizeDimentionsWhenIncWidthAndDecHeight() {
        planet.resizeMap(WIDTH + 1, HEIGHT - 1);

        assertThat(planet.getWidth()).isEqualTo(WIDTH + 1);
        assertThat(planet.getHeight()).isEqualTo(HEIGHT - 1);
    }

    @Test
    void shouldThrowExceptionWhenResizeDimentionsHasIncWidthAndDecHeightAndAnyProbeCrossTheBoard() {
        Probe probe = new Probe("Probe Y Board", 1, 4, Direction.S);
        planet.addProbe(probe);

        assertThrows(ResizeMapException.class, () -> planet.resizeMap(WIDTH + 1, HEIGHT - 1));
    }

    @Test
    void shouldResizeDimentionsWhenDecWidthAndIncHeight() {
        planet.resizeMap(WIDTH - 1, HEIGHT + 1);

        assertThat(planet.getWidth()).isEqualTo(WIDTH - 1);
        assertThat(planet.getHeight()).isEqualTo(HEIGHT + 1);
    }

    @Test
    void shouldThrowExceptionWhenResizeDimentionsHasDecWidthAndIncHeightAndAnyProbeCrossTheBoard() {
        Probe probe = new Probe("Probe Y Board", 4, 1, Direction.W);
        planet.addProbe(probe);

        assertThrows(ResizeMapException.class, () -> planet.resizeMap(WIDTH - 1, HEIGHT + 1));
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
        assertThrows(EntityNotFoundException.class, () -> planet.getProb("Empty"));
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
        assertThrows(UniqueEntityException.class, () -> planet.addProbe(probe));
    }

    @Test
    void shouldThrowExceptionWhenAddProbeButPositionIsBusy() {
        Probe probe = new Probe("Probe One", 1, 1, Direction.N);
        planet.addProbe(probe);

        Probe samePositionProbe = new Probe("Probe Same Position", 1, 1, Direction.S);
        assertThrows(SpaceNotAvailableException.class, () -> planet.addProbe(samePositionProbe));
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

    @Test
    void shouldThrowExceptionWhenProbeNotFoundInDeparture() {
        String probeName = "empty";
        assertThrows(EntityNotFoundException.class, () -> planet.departure(probeName));
    }

    @Test
    void shouldThrowExceptionWhenGetProbeButProbeHasDepartured() {
        String probeName = "probe-departure";
        Probe probe = new Probe(probeName, 1, 1, Direction.N);

        planet.addProbe(probe);
        planet.departure(probeName);

        assertThrows(EntityNotFoundException.class, () -> planet.getProb(probeName));
    }

    @Test
    void shouldThrowExceptionWhenProbeNotFoundInMove() {
        String probeName = "empty";
        assertThrows(EntityNotFoundException.class, () -> planet.moveProbe(probeName));
    }

    @Test
    void shouldThrowExceptionWhenProbeMoveCrossBorder() {
        String probeNameXPlus = "probe-cross-border-xplus";
        String probeNameYPlus = "probe-cross-border-yplus";
        String probeNameXZero = "probe-cross-border-xzero";
        String probeNameYZero = "probe-cross-border-yzero";

        Probe probeXPlus = new Probe(probeNameXPlus, WIDTH - 1, 2, Direction.E);
        Probe probeYPlus = new Probe(probeNameYPlus, 2, HEIGHT - 1, Direction.N);
        Probe probeXZero = new Probe(probeNameXZero, 0, 2, Direction.W);
        Probe probeYZero = new Probe(probeNameYZero, 2, 0, Direction.S);

        planet.addProbe(probeXPlus);
        planet.addProbe(probeYPlus);
        planet.addProbe(probeXZero);
        planet.addProbe(probeYZero);

        assertThrows(MovementNotPermittedException.class, () -> planet.moveProbe(probeNameXPlus));
        assertThrows(MovementNotPermittedException.class, () -> planet.moveProbe(probeNameYPlus));
        assertThrows(MovementNotPermittedException.class, () -> planet.moveProbe(probeNameXZero));
        assertThrows(MovementNotPermittedException.class, () -> planet.moveProbe(probeNameYZero));
    }

    @Test
    void shouldThrowExceptionWhenProbeMoveButSpaceIsBusy() {
        String probeName = "probe-busy";
        String probeNameOld = "probe-old";

        Probe probe = new Probe(probeName, 2, 1, Direction.N);
        Probe probeOld = new Probe(probeNameOld, 2, 2, Direction.E);

        planet.addProbe(probe);
        planet.addProbe(probeOld);

        assertThrows(SpaceNotAvailableException.class, () -> planet.moveProbe(probeName));
    }

    @Test
    void shouldMoveWhenProbeIsAvailable() {
        String probeName = "probe-available";
        String probeNameOld = "probe-old";
        int xOrigin = 2;
        int yOrigin = 1;

        Probe probe = new Probe(probeName, xOrigin, yOrigin, Direction.N);
        Probe probeOld = new Probe(probeNameOld, 2, 3, Direction.E);

        planet.addProbe(probe);
        planet.addProbe(probeOld);

        planet.moveProbe(probeName);

        assertThat(planet.positionEmpty(xOrigin, yOrigin)).isTrue();
        assertThat(planet.positionEmpty(probe.getX(), probe.getY())).isFalse();
    }
}