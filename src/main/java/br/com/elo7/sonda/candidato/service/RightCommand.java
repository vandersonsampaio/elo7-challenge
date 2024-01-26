package br.com.elo7.sonda.candidato.service;

import br.com.elo7.sonda.candidato.model.entity.Planet;
import br.com.elo7.sonda.candidato.model.entity.Probe;

public class RightCommand implements MovimentationCommand {

    @Override
    public void action(Planet planet, String probeName) {
        Probe probe = planet.getProb(probeName);
        probe.turnRight();
    }
}
