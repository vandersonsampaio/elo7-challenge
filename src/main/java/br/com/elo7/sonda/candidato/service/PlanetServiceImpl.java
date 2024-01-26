package br.com.elo7.sonda.candidato.service;

import br.com.elo7.sonda.candidato.model.entity.Planet;
import br.com.elo7.sonda.candidato.model.repository.PlanetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanetServiceImpl implements PlanetService {

    private final PlanetRepository repository;

    @Override
    public Planet create(String name, int width, int height) {
        if (repository.existsByName(name)) {
            throw new RuntimeException();
        }

        return repository.save(new Planet(name, width, height));
    }

    @Override
    public Planet updateDimentions(String name, int width, int height) {
        Planet planet = getPlanet(name);
        planet.resizeDimentions(width, height);

        return repository.save(planet);
    }

    @Override
    public Planet getPlanet(String name) {
        return repository.findByName(name).orElseThrow();
    }

    @Override
    public List<Planet> findAllPlanets() {
        return repository.findAll();
    }

    @Override
    public void save(Planet entity) {
        repository.save(entity);
    }
}
