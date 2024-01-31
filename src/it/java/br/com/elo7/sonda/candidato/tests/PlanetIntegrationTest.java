package br.com.elo7.sonda.candidato.tests;

import br.com.elo7.sonda.candidato.controller.dto.request.CreatePlanetRequest;
import br.com.elo7.sonda.candidato.controller.dto.request.UpdatePlanetRequest;
import br.com.elo7.sonda.candidato.helper.PlanetHelper;
import br.com.elo7.sonda.candidato.model.entity.Planet;
import br.com.elo7.sonda.candidato.model.expection.ErrorResponse;
import br.com.elo7.sonda.candidato.model.expection.SimpleErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext
class PlanetIntegrationTest extends PlanetHelper {

    @Override
    @BeforeEach
    public void init() {
        super.init();
    }

    @Test
    @DisplayName("Deve listar todos os Planetas cadastrados")
    void shouldListAllPlanets() {
        String planetName = "planet-find-all";
        insertPlanet(planetName);

        List<Planet> planets = findAllPlanets();

        Optional<Planet> actualPlanet = planets.stream().filter(p -> p.getName().equals(planetName)).findFirst();

        assertThat(actualPlanet).isPresent();
        assertPlanetResponse(actualPlanet.get(), planetName, WIDTH_DEFAULT);
        assertThat(actualPlanet.get().getUpdateTime()).isNull();
    }

    @Test
    @DisplayName("Deve retornar erro ao tentar obter um planeta que nao esta cadastro")
    void shouldThrowExceptionWhenGetPlanetNotFound() {
        String planetName = "planet-not-found";

        SimpleErrorResponse actual = getPlanetNotFound(planetName);

        assertThat(actual.getMessage()).contains(MESSAGE_NOT_FOUND);
    }

    @Test
    @DisplayName("Deve retornar o planeta cadastro")
    void shouldGetPlanetByName() {
        String planetName = "planet-get";
        insertPlanet(planetName);

        Planet actual = getPlanet(planetName);

        assertPlanetResponse(actual, planetName, WIDTH_DEFAULT);
        assertThat(actual.getUpdateTime()).isNull();
    }

    @Test
    @DisplayName("Deve retornar error ao tentar atualizar um planeta com largura negativa")
    void shouldThrowExceptionWhenNegativeWidthInUpdatePlanet() {
        UpdatePlanetRequest request = new UpdatePlanetRequest(WIDTH_DEFAULT, -1);

        ErrorResponse actual = updatePlanetBadRequest(PLANET_PARAMETER_ERROR, request);

        assertThat(actual.getMessage()).contains(MESSAGE_NEGATIVE_OR_ZERO);
    }

    @Test
    @DisplayName("Deve retornar error ao tentar atualizar um planeta com largura zero")
    void shouldThrowExceptionWhenZeroWidthInUpdatePlanet() {
        UpdatePlanetRequest request = new UpdatePlanetRequest(WIDTH_DEFAULT, 0);

        ErrorResponse actual = updatePlanetBadRequest(PLANET_PARAMETER_ERROR, request);

        assertThat(actual.getMessage()).contains(MESSAGE_NEGATIVE_OR_ZERO);
    }

    @Test
    @DisplayName("Deve retornar error ao tentar atualizar um planeta com comprimento negativa")
    void shouldThrowExceptionWhenNegativeHeightInUpdatePlanet() {
        UpdatePlanetRequest request = new UpdatePlanetRequest(-1, HEIGHT_DEFAULT);

        ErrorResponse actual = updatePlanetBadRequest(PLANET_PARAMETER_ERROR, request);

        assertThat(actual.getMessage()).contains(MESSAGE_NEGATIVE_OR_ZERO);
    }

    @Test
    @DisplayName("Deve retornar error ao tentar atualizar um planeta com comprimento zero")
    void shouldThrowExceptionWhenZeroHeightInUpdatePlanet() {
        UpdatePlanetRequest request = new UpdatePlanetRequest(0, HEIGHT_DEFAULT);

        ErrorResponse actual = updatePlanetBadRequest(PLANET_PARAMETER_ERROR, request);

        assertThat(actual.getMessage()).contains(MESSAGE_NEGATIVE_OR_ZERO);
    }

    @Test
    @DisplayName("Deve retornar error ao tentar atualizar um planeta nao cadastrado")
    void shouldThrowExceptionWhenNotFoundInUpdatePlanet() {
        String planetName = "planet-not-found";

        UpdatePlanetRequest request = new UpdatePlanetRequest(WIDTH_DEFAULT - 1, HEIGHT_DEFAULT);

        SimpleErrorResponse actual = updatePlanetNotFound(planetName, request);

        assertThat(actual.getMessage()).contains(MESSAGE_NOT_FOUND);
    }

    @Test
    @DisplayName("Deve retornar error ao tentar atualizar um planeta sem alterar nenhuma dimensao")
    void shouldThrowExceptionWhenSameDimensionsInUpdatePlanet() {
        String planetName = "planet-same-dimensions";
        insertPlanet(planetName);

        UpdatePlanetRequest request = new UpdatePlanetRequest(WIDTH_DEFAULT, HEIGHT_DEFAULT);

        ErrorResponse actual = updatePlanetBadRequest(planetName, request);

        assertThat(actual.getMessage()).contains("It is not possible to resize Planet");
    }

    @Test
    @DisplayName("Deve retornar error ao tentar atualizar um planeta que terá uma Sonda fora da area mapeada")
    void shouldThrowExceptionWhenProbeAcrossBorderInUpdatePlanet() {
        String planetName = "planet-probe-lost";
        insertPlanetAndProbe(planetName);

        UpdatePlanetRequest request = new UpdatePlanetRequest(WIDTH_DEFAULT - 1, HEIGHT_DEFAULT - 1);

        ErrorResponse actual = updatePlanetBadRequest(planetName, request);

        assertThat(actual.getMessage()).contains("It is not possible to resize Planet");
    }

    @Test
    @DisplayName("Deve atualizar as dimensoes de um planeta")
    void shouldUpdatePlanet() {
        String planetName = "planet-update-dimensions";
        insertPlanet(planetName);

        UpdatePlanetRequest request = new UpdatePlanetRequest(WIDTH_DEFAULT - 1, HEIGHT_DEFAULT);

        Planet actual = updatePlanet(planetName, request);

        assertPlanetResponse(actual, planetName, WIDTH_DEFAULT - 1);
        assertThat(actual.getUpdateTime()).isNotNull();
    }

    @Test
    @DisplayName("Deve retornar error ao tentar criar um planeta sem nome")
    void shouldThrowExceptionWhenNameBlankInCreatePlanet() {
        CreatePlanetRequest request = new CreatePlanetRequest("", WIDTH_DEFAULT, HEIGHT_DEFAULT);

        ErrorResponse actual = createPlanetBadRequest(request);

        assertThat(actual.getMessage()).contains("cannot be blank");
    }

    @Test
    @DisplayName("Deve retornar error ao tentar criar um planeta com largura negativa")
    void shouldThrowExceptionWhenNegativeWidthInCreatePlanet() {
        CreatePlanetRequest request = new CreatePlanetRequest(PLANET_PARAMETER_ERROR, WIDTH_DEFAULT, -1);

        ErrorResponse actual = createPlanetBadRequest(request);

        assertThat(actual.getMessage()).contains(MESSAGE_NEGATIVE_OR_ZERO);
    }

    @Test
    @DisplayName("Deve retornar error ao tentar criar um planeta com largura zero")
    void shouldThrowExceptionWhenZeroWidthInCreatePlanet() {
        CreatePlanetRequest request = new CreatePlanetRequest(PLANET_PARAMETER_ERROR, WIDTH_DEFAULT, 0);

        ErrorResponse actual = createPlanetBadRequest(request);

        assertThat(actual.getMessage()).contains(MESSAGE_NEGATIVE_OR_ZERO);

    }

    @Test
    @DisplayName("Deve retornar error ao tentar criar um planeta com comprimento negativa")
    void shouldThrowExceptionWhenNegativeHeightInCreatePlanet() {
        CreatePlanetRequest request = new CreatePlanetRequest(PLANET_PARAMETER_ERROR, -1, HEIGHT_DEFAULT);

        ErrorResponse actual = createPlanetBadRequest(request);

        assertThat(actual.getMessage()).contains(MESSAGE_NEGATIVE_OR_ZERO);
    }

    @Test
    @DisplayName("Deve retornar error ao tentar criar um planeta com comprimento zero")
    void shouldThrowExceptionWhenZeroHeightInCreatePlanet() {
        CreatePlanetRequest request = new CreatePlanetRequest(PLANET_PARAMETER_ERROR, 0, HEIGHT_DEFAULT);

        ErrorResponse actual = createPlanetBadRequest(request);

        assertThat(actual.getMessage()).contains(MESSAGE_NEGATIVE_OR_ZERO);
    }

    @Test
    @DisplayName("Deve retornar error ao tentar criar um planeta com nome repetido")
    void shouldThrowExceptionWhenSameNameInCreatePlanet() {
        String planetName = "planet-unique-error";
        insertPlanet(planetName);
        CreatePlanetRequest request = new CreatePlanetRequest(planetName, WIDTH_DEFAULT, HEIGHT_DEFAULT);

        ErrorResponse actual = createPlanetBadRequest(request);

        assertThat(actual.getMessage()).contains("There is already a");
    }

    @Test
    @DisplayName("Deve criar um planeta")
    void shouldCreatePlanet() {
        String planetName = "planet-new-success";
        CreatePlanetRequest request = new CreatePlanetRequest(planetName, WIDTH_DEFAULT, HEIGHT_DEFAULT);

        Planet actual = createPlanet(request);

        assertPlanetResponse(actual, planetName, WIDTH_DEFAULT);
        assertThat(actual.getUpdateTime()).isNull();
    }

    private void assertPlanetResponse(Planet actual, String planetName, int width) {
        assertThat(actual.getName()).isEqualTo(planetName);
        assertThat(actual.getWidth()).isEqualTo(width);
        assertThat(actual.getHeight()).isEqualTo(HEIGHT_DEFAULT);
        assertThat(actual.getRegisterTime()).isNotNull();
    }
}
