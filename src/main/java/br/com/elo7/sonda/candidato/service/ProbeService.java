package br.com.elo7.sonda.candidato.service;

import br.com.elo7.sonda.candidato.model.entity.Probe;

import java.util.List;

public interface ProbeService {

    Probe create(String planetName, Probe entity);

    Probe move(String planetName, String probeName, List<MovimentationCommand> commands);

    Probe getProbe(String planetName, String probeName);

    List<Probe> listAllProbes(String planetName);

    void departureProbe(String planetName, String probeName);
}
