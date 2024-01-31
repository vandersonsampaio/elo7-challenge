package br.com.elo7.sonda.candidato.helper;

import br.com.elo7.sonda.candidato.SevenMarsApplicationIT;
import br.com.elo7.sonda.candidato.client.PlanetClient;
import br.com.elo7.sonda.candidato.model.entity.Planet;
import br.com.elo7.sonda.candidato.model.repository.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PlanetHelper extends SevenMarsApplicationIT {

    protected final int WIDTH_DEFAULT = 5;
    protected final int HEIGHT_DEFAULT = 5;
    @Autowired
    private PlanetRepository planetRepository;

    private PlanetClient client;

    protected void init() {
        client = new PlanetClient(port);
    }

    protected void insertPlanet(String planetName) {
        if (!planetRepository.existsByName(planetName)) {
            planetRepository.save(new Planet(planetName, WIDTH_DEFAULT, HEIGHT_DEFAULT));
        }
    }

    protected List<Planet> findAllPlanets() {
        return client.findAll();
    }
}
