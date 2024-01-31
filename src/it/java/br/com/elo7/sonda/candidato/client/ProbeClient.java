package br.com.elo7.sonda.candidato.client;

import br.com.elo7.sonda.candidato.controller.dto.request.CreateProbeRequest;
import br.com.elo7.sonda.candidato.controller.dto.request.MovimentProbeRequest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;

public class ProbeClient {

    private static final String PATH_PARAM_PLANET_NAME = "planetName";
    private static final String BASE_URI = "/planet/{" + PATH_PARAM_PLANET_NAME + "}/probe";
    private static final String PATH_PARAM = "probeName";
    private static final String PATH_PARAM_FULL = "/{" + PATH_PARAM + "}";
    private final RequestSpecification specBuilder;

    public ProbeClient(int port) {
        specBuilder = new RequestSpecBuilder().setPort(port)
                .setContentType(MediaType.APPLICATION_JSON_VALUE)
                .setBaseUri("http://localhost")
                .build().log().all();
    }

    public Response departure(String planetName, String probeName) {
        return given()
                .pathParams(PATH_PARAM_PLANET_NAME, planetName, PATH_PARAM, probeName)
                .spec(specBuilder)
                .when()
                .post(BASE_URI + PATH_PARAM_FULL + "/departure");
    }

    public Response move(String planetName, String probeName, MovimentProbeRequest request) {
        return given()
                .pathParams(PATH_PARAM_PLANET_NAME, planetName, PATH_PARAM, probeName)
                .spec(specBuilder)
                .with()
                .body(request)
                .when()
                .post(BASE_URI + PATH_PARAM_FULL + "/departure");
    }

    public Response findAll(String planetName) {
        return given()
                .pathParam(PATH_PARAM, planetName)
                .spec(specBuilder)
                .when()
                .get(BASE_URI);
    }

    public Response getProbe(String planetName, String probeName) {
        return given()
                .pathParams(PATH_PARAM_PLANET_NAME, planetName, PATH_PARAM, probeName)
                .spec(specBuilder)
                .when()
                .get(BASE_URI + PATH_PARAM_FULL);
    }

    public Response create(String planetName, CreateProbeRequest request) {
        return given()
                .pathParams(PATH_PARAM_PLANET_NAME, planetName)
                .spec(specBuilder)
                .with()
                .body(request)
                .when()
                .post(BASE_URI);
    }
}
