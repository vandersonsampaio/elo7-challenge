package br.com.elo7.sonda.candidato.service;

import br.com.elo7.sonda.candidato.model.entity.Planet;

import java.util.List;

public interface PlanetService {

    Planet create(Planet entity);
    Planet updateDimentions(Planet entity);

    Planet getPlanet(String name);
    List<Planet> findAllPlanets();
}
