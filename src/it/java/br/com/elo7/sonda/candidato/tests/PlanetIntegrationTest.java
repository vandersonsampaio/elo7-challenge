package br.com.elo7.sonda.candidato.tests;

import br.com.elo7.sonda.candidato.helper.PlanetHelper;
import br.com.elo7.sonda.candidato.model.entity.Planet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(actualPlanet.get().getName()).isEqualTo(planetName);
        assertThat(actualPlanet.get().getWidth()).isEqualTo(WIDTH_DEFAULT);
        assertThat(actualPlanet.get().getHeight()).isEqualTo(HEIGHT_DEFAULT);
        assertThat(actualPlanet.get().getRegisterTime()).isNotNull();
        assertThat(actualPlanet.get().getUpdateTime()).isNull();
    }
}
