package br.com.elo7.sonda.candidato.service;

import br.com.elo7.sonda.candidato.model.entity.Planet;
import br.com.elo7.sonda.candidato.model.expection.EntityNotFoundException;
import br.com.elo7.sonda.candidato.model.expection.UniqueEntityException;
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
            throw new UniqueEntityException(Planet.class.getSimpleName(), name);
        }

        return save(new Planet(name, width, height));
    }

    @Override
    public Planet updateDimentions(String name, int width, int height) {
        Planet planet = getPlanet(name);
        planet.resizeMap(width, height);

        return save(planet);
    }

    @Override
    public Planet getPlanet(String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException(Planet.class.getSimpleName(), name));
    }

    @Override
    public List<Planet> findAllPlanets() {
        return repository.findAll();
    }

    @Override
    public Planet save(Planet entity) {
        return repository.save(entity);
    }
}
