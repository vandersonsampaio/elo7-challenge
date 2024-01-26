package br.com.elo7.sonda.candidato.service;

import br.com.elo7.sonda.candidato.model.entity.Planet;

import java.util.List;

public interface PlanetService {

    Planet create(String name, int width, int height);
    Planet updateDimentions(String name, int width, int height);

    Planet getPlanet(String name);
    List<Planet> findAllPlanets();

    void save(Planet entity);
}
