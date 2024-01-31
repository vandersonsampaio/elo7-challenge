package br.com.elo7.sonda.candidato.helper;

import br.com.elo7.sonda.candidato.SevenMarsApplicationIT;
import br.com.elo7.sonda.candidato.client.ProbeClient;
import br.com.elo7.sonda.candidato.controller.dto.request.CreateProbeRequest;
import br.com.elo7.sonda.candidato.controller.dto.request.MovimentProbeRequest;
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
import java.util.Optional;

public class ProbeHelper extends SevenMarsApplicationIT {

    protected final static int X_DEFAULT = 1;
    protected final static int WIDTH_DEFAULT = 5;
    protected final static int Y_DEFAULT = 1;
    protected final static int HEIGHT_DEFAULT = 5;
    protected final static String PLANET_NAME_DEFAULT = "planet-default";
    protected final static String BLANK_MESSAGE = "cannot be blank";
    protected final static String NEGATIVE_MESSAGE = "cannot be negative";
    protected final static String NOT_FOUND_MESSAGE = "not found";
    protected final static String SPACE_NOT_AVAILABLE_MESSAGE = "not available";
    protected final static String MOVIMENT_NOT_POSSIBLE_MESSAGE = "Only three moviment possible";
    protected final static String MOVIMENT_NOT_PERMITTED_MESSAGE = "not permitted";
    protected final static String NOT_UNIQUE_MESSAGE = "There is already a";

    @Autowired
    private PlanetRepository planetRepository;

    private ProbeClient client;

    protected void init() {
        client = new ProbeClient(port);
    }

    protected ErrorResponse lendProbeBadRequest(String planetName, CreateProbeRequest request) {
        Response response = client.create(planetName, request);

        response.then().statusCode(HttpStatus.BAD_REQUEST.value());

        return response.as(ErrorResponse.class);
    }

    protected SimpleErrorResponse lendProbeNotFound(String planetName, CreateProbeRequest request) {
        Response response = client.create(planetName, request);

        response.then().statusCode(HttpStatus.NOT_FOUND.value());

        return response.as(SimpleErrorResponse.class);
    }

    protected Probe lendProbe(String planetName, CreateProbeRequest request) {
        Response response = client.create(planetName, request);

        response.then().statusCode(HttpStatus.CREATED.value());

        return response.as(Probe.class);
    }

    protected SimpleErrorResponse getProbeNotFound(String planetName, String probeName) {
        Response response = client.getProbe(planetName, probeName);

        response.then().statusCode(HttpStatus.NOT_FOUND.value());

        return response.as(SimpleErrorResponse.class);
    }

    protected Probe getProbe(String planetName, String probeName) {
        Response response = client.getProbe(planetName, probeName);

        response.then().statusCode(HttpStatus.OK.value());

        return response.as(Probe.class);
    }

    protected SimpleErrorResponse findProbesNotFound(String planetName) {
        Response response = client.findAll(planetName);

        response.then().statusCode(HttpStatus.NOT_FOUND.value());

        return response.as(SimpleErrorResponse.class);
    }

    protected List<Probe> findProbes(String planetName) {
        Response response = client.findAll(planetName);

        response.then().statusCode(HttpStatus.OK.value());

        return List.of(response.as(Probe[].class));
    }

    protected SimpleErrorResponse departureProbeNotFound(String planetName, String probeName) {
        Response response = client.departure(planetName, probeName);

        response.then().statusCode(HttpStatus.NOT_FOUND.value());

        return response.as(SimpleErrorResponse.class);
    }

    protected void departureProbe(String planetName, String probeName) {
        Response response = client.departure(planetName, probeName);

        response.then().statusCode(HttpStatus.OK.value());
    }

    protected ErrorResponse moveProbeBadRequest(String planetName, String probeName, MovimentProbeRequest request) {
        Response response = client.move(planetName, probeName, request);

        response.then().statusCode(HttpStatus.BAD_REQUEST.value());

        return response.as(ErrorResponse.class);
    }

    protected SimpleErrorResponse moveProbeNotFound(String planetName, String probeName, MovimentProbeRequest request) {
        Response response = client.move(planetName, probeName, request);

        response.then().statusCode(HttpStatus.NOT_FOUND.value());

        return response.as(SimpleErrorResponse.class);
    }

    protected Probe moveProbe(String planetName, String probeName, MovimentProbeRequest request) {
        Response response = client.move(planetName, probeName, request);

        response.then().statusCode(HttpStatus.OK.value());

        return response.as(Probe.class);
    }

    private void insertPlanet(Planet planet) {
        if (!planetRepository.existsByName(planet.getName())) {
            planetRepository.save(planet);
        }
    }

    protected void insertPlanet(String planetName) {
        insertPlanet(new Planet(planetName, WIDTH_DEFAULT, HEIGHT_DEFAULT));
    }

    protected void insertPlanetAndProbe(String planetName, String probeName) {
        Planet planet = new Planet(planetName, WIDTH_DEFAULT, HEIGHT_DEFAULT);
        planet.addProbe(new Probe(probeName, X_DEFAULT, Y_DEFAULT, Direction.N));

        insertPlanet(planet);
    }

    protected void insertProbe(String planetName, String probeName, int x, int y, Direction direction) {
        Planet planet = getOrCreatePlanet(planetName);
        planet.addProbe(new Probe(probeName, x, y, direction));

        planetRepository.save(planet);
    }

    private Planet getOrCreatePlanet(String planetName) {
        Optional<Planet> planet = planetRepository.findByName(planetName);

        if (planet.isEmpty()) {
            return planetRepository.save(new Planet(planetName, WIDTH_DEFAULT, HEIGHT_DEFAULT));
        }

        return planet.get();
    }
}
