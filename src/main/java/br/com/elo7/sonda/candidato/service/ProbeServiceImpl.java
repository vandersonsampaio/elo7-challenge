package br.com.elo7.sonda.candidato.service;

import br.com.elo7.sonda.candidato.model.entity.Planet;
import br.com.elo7.sonda.candidato.model.entity.Probe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProbeServiceImpl implements ProbeService{

    private final PlanetService planetService;

    @Override
    public Probe create(String planetName, Probe entity) {
        Planet planet = planetService.getPlanet(planetName);
        planet.addProbe(entity);

        planetService.save(planet);
        return entity;
    }

    @Override
    public Probe move(String planetName, String probeName, List<MovimentationCommand> commands) {
        Planet planet = planetService.getPlanet(planetName);

        commands.forEach(c -> c.action(planet, probeName));
        planetService.save(planet);

        return planet.getProb(probeName);
    }

    @Override
    public Probe getProbe(String planetName, String probeName) {
        return planetService.getPlanet(planetName).getProb(probeName);
    }

    @Override
    public List<Probe> listAllProbes(String planetName) {
        return planetService.getPlanet(planetName).getProbes();
    }

    @Override
    public void departureProbe(String planetName, String probeName) {
        Planet planet = planetService.getPlanet(planetName);
        planet.departure(probeName);

        planetService.save(planet);
    }
}
