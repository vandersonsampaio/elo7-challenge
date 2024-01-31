package br.com.elo7.sonda.candidato.client;

import br.com.elo7.sonda.candidato.controller.dto.request.CreatePlanetRequest;
import br.com.elo7.sonda.candidato.controller.dto.request.UpdatePlanetRequest;
import br.com.elo7.sonda.candidato.model.entity.Planet;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;

import static io.restassured.RestAssured.given;

public class PlanetClient {

    private static final String BASE_URI = "/planet";
    private static final String PATH_PARAM = "name";
    private static final String PATH_PARAM_FULL = "/{" + PATH_PARAM + "}";
    private final RequestSpecification specBuilder;

    public PlanetClient(int port) {
        specBuilder = new RequestSpecBuilder().setPort(port)
                .setContentType(MediaType.APPLICATION_JSON_VALUE)
                .setBaseUri("http://localhost")
                .build().log().all();
    }

    public List<Planet> findAll() {
        Planet[] responses = given()
                .spec(specBuilder)
                .when()
                .get(BASE_URI)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(Planet[].class);

        return List.of(responses);
    }

    public Response getPlanet(String name) {
        return given()
                .pathParam(PATH_PARAM, name)
                .spec(specBuilder)
                .when()
                .get(BASE_URI + PATH_PARAM_FULL);
    }

    public Response update(String name, UpdatePlanetRequest request) {
        return given()
                .pathParam(PATH_PARAM, name)
                .spec(specBuilder)
                .with()
                .body(request)
                .when()
                .put(BASE_URI + PATH_PARAM_FULL);
    }

    public Response create(CreatePlanetRequest request) {
        return given()
                .spec(specBuilder)
                .with()
                .body(request)
                .when()
                .post(BASE_URI);
    }
}
