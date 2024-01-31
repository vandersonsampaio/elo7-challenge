package br.com.elo7.sonda.candidato.tests;

import br.com.elo7.sonda.candidato.controller.dto.request.CreateProbeRequest;
import br.com.elo7.sonda.candidato.controller.dto.request.MovimentProbeRequest;
import br.com.elo7.sonda.candidato.helper.ProbeHelper;
import br.com.elo7.sonda.candidato.model.entity.Probe;
import br.com.elo7.sonda.candidato.model.enums.Direction;
import br.com.elo7.sonda.candidato.model.expection.ErrorResponse;
import br.com.elo7.sonda.candidato.model.expection.SimpleErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProbeIntegrationTest extends ProbeHelper {

    @Override
    @BeforeEach
    public void init() {
        super.init();
    }

    @Test
    @DisplayName("Deve retornar erro ao tentar pousar uma sonda sem nome")
    void shouldThrowExceptionWhenLendProbeWithNoName() {
        CreateProbeRequest request = new CreateProbeRequest("", X_DEFAULT, Y_DEFAULT, Direction.N);

        ErrorResponse actual = lendProbeBadRequest(PLANET_NAME_DEFAULT, request);

        assertThat(actual.getMessage()).contains(BLANK_MESSAGE);
    }

    @Test
    @DisplayName("Deve retornar error ao tentar pousar uma sonda com posicao X negativa")
    void shouldThrowExceptionWhenNegativeXInLendProbe() {
        String probeName = "probe-x-negative";
        CreateProbeRequest request = new CreateProbeRequest(probeName, -1, Y_DEFAULT, Direction.N);

        ErrorResponse actual = lendProbeBadRequest(PLANET_NAME_DEFAULT, request);

        assertThat(actual.getMessage()).contains(NEGATIVE_MESSAGE);
    }

    @Test
    @DisplayName("Deve retornar error ao tentar pousar uma sonda com posicao Y negativa")
    void shouldThrowExceptionWhenNegativeYInLendProbe() {
        String probeName = "probe-y-negative";
        CreateProbeRequest request = new CreateProbeRequest(probeName, X_DEFAULT, -1, Direction.N);

        ErrorResponse actual = lendProbeBadRequest(PLANET_NAME_DEFAULT, request);

        assertThat(actual.getMessage()).contains(NEGATIVE_MESSAGE);
    }

    @Test
    @DisplayName("Deve retornar error ao tentar pousar uma sonda sem direcao")
    void shouldThrowExceptionWhenNoDirectionInLendProbe() {
        String probeName = "probe-direction-null";
        CreateProbeRequest request = new CreateProbeRequest(probeName, X_DEFAULT, Y_DEFAULT, null);

        ErrorResponse actual = lendProbeBadRequest(PLANET_NAME_DEFAULT, request);

        assertThat(actual.getMessage()).contains("cannot be null");
    }

    @Test
    @DisplayName("Deve retornar error ao tentar pousar uma sonda em um planeta nao cadastrado")
    void shouldThrowExceptionWhenLendProbeInPlanetNotFound() {
        String planetName = "planet-not-found";
        String probeName = "probe-default";
        CreateProbeRequest request = new CreateProbeRequest(probeName, X_DEFAULT, Y_DEFAULT, Direction.N);

        SimpleErrorResponse actual = lendProbeNotFound(planetName, request);

        assertThat(actual.getMessage()).contains(NOT_FOUND_MESSAGE);
    }

    @Test
    @DisplayName("Deve retornar error ao tentar pousar uma sonda com nome repetido no planeta")
    void shouldThrowExceptionWhenLendProbeWithSameName() {
        String planetName = "planet-repeated-probes";
        String probeName = "probe-repeated";
        CreateProbeRequest request = new CreateProbeRequest(probeName, X_DEFAULT, Y_DEFAULT, Direction.N);

        insertPlanetAndProbe(planetName, probeName);

        ErrorResponse actual = lendProbeBadRequest(planetName, request);

        assertThat(actual.getMessage()).contains(NOT_UNIQUE_MESSAGE);
    }

    @Test
    @DisplayName("Deve retornar error ao tentar pousar uma sonda em uma localizacao do planeta nao disponivel")
    void shouldThrowExceptionWhenLendProbeWithBusySpace() {
        String planetName = "planet-busy-space";
        String probeName = "probe-original-space";
        String probeNameBusy = "probe-busy-space";
        CreateProbeRequest request = new CreateProbeRequest(probeNameBusy, X_DEFAULT, Y_DEFAULT, Direction.N);

        insertPlanetAndProbe(planetName, probeName);

        ErrorResponse actual = lendProbeBadRequest(planetName, request);

        assertThat(actual.getMessage()).contains(SPACE_NOT_AVAILABLE_MESSAGE);
    }

    @Test
    @DisplayName("Deve retornar error ao tentar pousar uma sonda em uma localizacao do planeta além da mapeada")
    void shouldThrowExceptionWhenLendProbeWithIlligalSpace() {
        String planetName = "planet-illigal-probe";
        String probeName = "probe-illigal-position";
        CreateProbeRequest request = new CreateProbeRequest(probeName, WIDTH_DEFAULT + 1, HEIGHT_DEFAULT + 3, Direction.N);

        insertPlanet(planetName);

        ErrorResponse actual = lendProbeBadRequest(planetName, request);

        assertThat(actual.getMessage()).contains(SPACE_NOT_AVAILABLE_MESSAGE);
    }

    @Test
    @DisplayName("Deve pousar uma sonda em um planeta")
    void shouldLendProbe() {
        String planetName = "planet-success";
        String probeName = "probe-success";
        String probeNameSuccess = "probe-success-second";
        int x = X_DEFAULT + 1;
        int y = Y_DEFAULT;
        Direction direction = Direction.W;
        CreateProbeRequest request = new CreateProbeRequest(probeNameSuccess, x, y, direction);

        insertPlanetAndProbe(planetName, probeName);

        Probe actual = lendProbe(planetName, request);

        assertProbe(actual, probeNameSuccess, x, y, direction);
        assertThat(actual.getUpdateTime()).isNull();
    }

    @Test
    @DisplayName("Deve retornar error ao tentar obter uma sonda em um planeta nao cadastrado")
    void shouldThrowExceptionWhenGetProbeInPlanetNotFound() {
        String planetName = "planet-not-found";
        String probeName = "probe-default";

        SimpleErrorResponse actual = getProbeNotFound(planetName, probeName);

        assertThat(actual.getMessage()).contains(NOT_FOUND_MESSAGE);
    }

    @Test
    @DisplayName("Deve retornar error ao tentar obter uma sonda nao cadastrada em um planeta")
    void shouldThrowExceptionWhenGetProbeNotFoundInAPlanet() {
        String planetName = "planet-found-probe-not-found";
        String probeName = "probe-not-found";
        insertPlanet(planetName);

        SimpleErrorResponse actual = getProbeNotFound(planetName, probeName);

        assertThat(actual.getMessage()).contains(NOT_FOUND_MESSAGE);
    }

    @Test
    @DisplayName("Deve obter a sonda de um planeta")
    void shouldGetProbe() {
        String planetName = "planet-get-success";
        String probeName = "probe-get-success";
        insertPlanetAndProbe(planetName, probeName);

        Probe actual = getProbe(planetName, probeName);

        assertProbe(actual, probeName, X_DEFAULT, Y_DEFAULT, Direction.N);
    }

    @Test
    @DisplayName("Deve retornar error ao tentar listar as sondas em um planeta nao cadastrado")
    void shouldThrowExceptionWhenFindAllProbesInPlanetNotFound() {
        String planetName = "planet-not-found";

        SimpleErrorResponse actual = findProbesNotFound(planetName);

        assertThat(actual.getMessage()).contains(NOT_FOUND_MESSAGE);
    }

    @Test
    @DisplayName("Deve listar as sondas de um planeta")
    void shouldFindAllProbes() {
        String planetName = "planet-find-success";
        String probeName = "probe-find-success";
        insertPlanetAndProbe(planetName, probeName);

        List<Probe> actual = findProbes(planetName);

        assertThat(actual).hasSize(1);
        assertProbe(actual.get(0), probeName, X_DEFAULT, Y_DEFAULT, Direction.N);
    }

    @Test
    @DisplayName("Deve retornar error ao tentar retirar uma sonda em um planeta nao cadastrado")
    void shouldThrowExceptionWhenDepartureProbeInPlanetNotFound() {
        String planetName = "planet-not-found";
        String probeName = "planet-not-found";

        SimpleErrorResponse actual = departureProbeNotFound(planetName, probeName);

        assertThat(actual.getMessage()).contains(NOT_FOUND_MESSAGE);
    }

    @Test
    @DisplayName("Deve retornar error ao tentar retirar uma sonda nao cadastrada em um planeta")
    void shouldThrowExceptionWhenDepartureProbeNotFoundInAPlanet() {
        String planetName = "planet-departure-probe-not-found";
        String probeName = "planet-not-found";
        insertPlanet(planetName);

        SimpleErrorResponse actual = departureProbeNotFound(planetName, probeName);

        assertThat(actual.getMessage()).contains(NOT_FOUND_MESSAGE);
    }

    @Test
    @DisplayName("Deve retirar uma sonda de um planeta")
    void shouldDepartureProbe() {
        String planetName = "planet-departure-success";
        String probeName = "planet-departure-success";
        insertPlanetAndProbe(planetName, probeName);

        departureProbe(planetName, probeName);
    }

    @Test
    @DisplayName("Deve retirar uma sonda de um planeta e realizar um novo pouso no mesmo local")
    void shouldDepartureProbeAndLendOtherProbeTheSameLocal() {
        String planetName = "planet-departure-success-relend";
        String probeName = "probe-departure-success-relend";
        insertPlanetAndProbe(planetName, probeName);

        departureProbe(planetName, probeName);

        CreateProbeRequest request = new CreateProbeRequest(probeName, 0, 0, Direction.N);
        Probe actual = lendProbe(planetName, request);

        assertProbe(actual, probeName, 0, 0, Direction.N);
    }

    @Test
    @DisplayName("Deve retornar error ao tentar realizar um movimento ilegal")
    void shouldThrowExceptionWhenMoveProbeButIlligalCommand() {
        String planetName = "planet-move-illigal-command";
        String probeName = "probe-move-illigal-command";
        MovimentProbeRequest request = new MovimentProbeRequest("LLMLQ");

        ErrorResponse actual = moveProbeBadRequest(planetName, probeName, request);

        assertThat(actual.getMessage()).contains(MOVIMENT_NOT_POSSIBLE_MESSAGE);
    }

    @Test
    @DisplayName("Deve retornar error ao tentar mover uma sonda em um planeta nao cadastrado")
    void shouldThrowExceptionWhenMoveProbeInAPlanetNotFound() {
        String planetName = "planet-not-found";
        String probeName = "probe-not-found";
        MovimentProbeRequest request = new MovimentProbeRequest("MML");

        SimpleErrorResponse actual = moveProbeNotFound(planetName, probeName, request);

        assertThat(actual.getMessage()).contains(NOT_FOUND_MESSAGE);
    }

    @Test
    @DisplayName("Deve retornar error ao tentar mover uma sonda nao cadastrada em um planeta")
    void shouldThrowExceptionWhenMoveProbeNotFoundInAPlanet() {
        String planetName = "planet-prob-not-found";
        String probeName = "probe-not-found";
        MovimentProbeRequest request = new MovimentProbeRequest("MML");
        insertPlanet(planetName);

        SimpleErrorResponse actual = moveProbeNotFound(planetName, probeName, request);

        assertThat(actual.getMessage()).contains(NOT_FOUND_MESSAGE);
    }

    @Test
    @DisplayName("Deve retornar error ao tentar mover uma sonda e ocorrer uma colisao")
    void shouldThrowExceptionWhenMoveProbeButCrashAnotherProbe() {
        String planetName = "planet-crash";
        String probeName = "probe-crash-origin";
        String probeNameCrash = "probe-crash";
        MovimentProbeRequest request = new MovimentProbeRequest("M");

        insertPlanetAndProbe(planetName, probeName);
        insertProbe(planetName, probeNameCrash, X_DEFAULT, Y_DEFAULT + 1, Direction.S);

        ErrorResponse actual = moveProbeBadRequest(planetName, probeNameCrash, request);

        assertThat(actual.getMessage()).contains(SPACE_NOT_AVAILABLE_MESSAGE);

        Probe actualProbe = getProbe(planetName, probeNameCrash);
        assertProbe(actualProbe, probeNameCrash, X_DEFAULT, Y_DEFAULT + 1, Direction.S);
    }

    @Test
    @DisplayName("Deve retornar error ao tentar mover uma sonda alem da area mapeada")
    void shouldThrowExceptionWhenMoveProbeAcrossTheBorder() {
        String planetName = "planet-across-border";
        String probeName = "probe-across-border";
        MovimentProbeRequest request = new MovimentProbeRequest("MM");

        insertProbe(planetName, probeName, WIDTH_DEFAULT - 1, HEIGHT_DEFAULT - 2, Direction.E);

        ErrorResponse actual = moveProbeBadRequest(planetName, probeName, request);

        assertThat(actual.getMessage()).contains(MOVIMENT_NOT_PERMITTED_MESSAGE);

        Probe actualProbe = getProbe(planetName, probeName);
        assertProbe(actualProbe, probeName, WIDTH_DEFAULT - 1, HEIGHT_DEFAULT - 2, Direction.E);
    }

    @Test
    @DisplayName("Deve retornar error ao tentar mover uma sonda alem da area mapeada na origem")
    void shouldThrowExceptionWhenMoveProbeAcrossTheLeftBorder() {
        String planetName = "planet-across-border-zero";
        String probeName = "probe-across-border-zero";
        MovimentProbeRequest request = new MovimentProbeRequest("MM");

        insertProbe(planetName, probeName, X_DEFAULT, HEIGHT_DEFAULT - 2, Direction.W);

        ErrorResponse actual = moveProbeBadRequest(planetName, probeName, request);

        assertThat(actual.getMessage()).contains(MOVIMENT_NOT_PERMITTED_MESSAGE);

        Probe actualProbe = getProbe(planetName, probeName);
        assertProbe(actualProbe, probeName, X_DEFAULT, HEIGHT_DEFAULT - 2, Direction.W);
    }

    @Test
    @DisplayName("Deve mover uma sonda de um planeta")
    void shouldMoveProbe() {
        String planetName = "planet-move";
        String probeName = "probe-move";
        MovimentProbeRequest request = new MovimentProbeRequest("MRM");

        insertProbe(planetName, probeName, X_DEFAULT, Y_DEFAULT , Direction.N);

        Probe actual = moveProbe(planetName, probeName, request);

        assertProbe(actual, probeName, X_DEFAULT + 1, Y_DEFAULT + 1, Direction.E);
        assertThat(actual.getUpdateTime()).isNotNull();
    }

    private void assertProbe(Probe actual, String probeName, int x, int y, Direction direction) {
        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isEqualTo(probeName);
        assertThat(actual.getX()).isEqualTo(x);
        assertThat(actual.getY()).isEqualTo(y);
        assertThat(actual.getDirection()).isEqualTo(direction);
        assertThat(actual.getRegisterTime()).isNotNull();
    }
}
