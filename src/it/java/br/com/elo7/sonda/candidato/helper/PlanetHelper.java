package br.com.elo7.sonda.candidato.helper;

import br.com.elo7.sonda.candidato.SevenMarsApplicationIT;
import br.com.elo7.sonda.candidato.client.PlanetClient;
import br.com.elo7.sonda.candidato.controller.dto.request.CreatePlanetRequest;
import br.com.elo7.sonda.candidato.controller.dto.request.UpdatePlanetRequest;
import br.com.elo7.sonda.candidato.model.entity.Planet;
import br.com.elo7.sonda.candidato.model.entity.Probe;
import br.com.elo7.sonda.candidato.model.enums.Direction;
import br.com.elo7.sonda.candidato.model.expection.ErrorResponse;
import br.com.elo7.sonda.candidato.model.expection.SimpleErrorResponse;
import br.com.elo7.sonda.candidato.model.repository.PlanetRepository;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.List;

public class PlanetHelper extends SevenMarsApplicationIT {

    protected final String MESSAGE_NOT_FOUND = "not found";
    protected final String MESSAGE_NEGATIVE_OR_ZERO = "cannot be negative or zero";
    protected final int WIDTH_DEFAULT = 5;
    protected final int HEIGHT_DEFAULT = 5;
    protected final String PLANET_PARAMETER_ERROR = "planet-parameter-error";
    @Autowired
    private PlanetRepository planetRepository;

    private PlanetClient client;

    protected void init() {
        client = new PlanetClient(port);
    }

    protected void insertPlanet(String planetName) {
        insertPlanet(new Planet(planetName, WIDTH_DEFAULT, HEIGHT_DEFAULT));
    }

    private void insertPlanet(Planet planet) {
        if (!planetRepository.existsByName(planet.getName())) {
            planetRepository.save(planet);
        }
    }

    protected void insertPlanetAndProbe(String planetName) {
        Planet planet = new Planet(planetName, WIDTH_DEFAULT, HEIGHT_DEFAULT);
        planet.addProbe(new Probe("probe-name", WIDTH_DEFAULT - 1, HEIGHT_DEFAULT - 1, Direction.N));

        insertPlanet(planet);
    }

    protected List<Planet> findAllPlanets() {
        return client.findAll();
    }

    protected SimpleErrorResponse getPlanetNotFound(String name) {
        Response response = client.getPlanet(name);

        response.then().statusCode(HttpStatus.NOT_FOUND.value());

        return response.as(SimpleErrorResponse.class);
    }

    protected Planet getPlanet(String name) {
        Response response = client.getPlanet(name);

        response.then().statusCode(HttpStatus.OK.value());

        return response.as(Planet.class);
    }

    protected Planet createPlanet(CreatePlanetRequest request) {
        Response response = client.create(request);

        response.then().statusCode(HttpStatus.CREATED.value());

        return response.as(Planet.class);
    }

    protected ErrorResponse createPlanetBadRequest(CreatePlanetRequest request) {
        Response response = client.create(request);

        response.then().statusCode(HttpStatus.BAD_REQUEST.value());

        return response.as(ErrorResponse.class);
    }

    protected Planet updatePlanet(String planetName, UpdatePlanetRequest request) {
        Response response = client.update(planetName, request);

        response.then().statusCode(HttpStatus.OK.value());

        return response.as(Planet.class);
    }

    protected ErrorResponse updatePlanetBadRequest(String planetName, UpdatePlanetRequest request) {
        Response response = client.update(planetName, request);

        response.then().statusCode(HttpStatus.BAD_REQUEST.value());

        return response.as(ErrorResponse.class);
    }

    protected SimpleErrorResponse updatePlanetNotFound(String planetName, UpdatePlanetRequest request) {
        Response response = client.update(planetName, request);

        response.then().statusCode(HttpStatus.NOT_FOUND.value());

        return response.as(SimpleErrorResponse.class);
    }
}
