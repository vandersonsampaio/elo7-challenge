package br.com.elo7.sonda.candidato.service;

import br.com.elo7.sonda.candidato.model.entity.Planet;

public class MoveCommand implements MovimentationCommand {

    @Override
    public void action(Planet planet, String probeName) {
        planet.moveProbe(probeName);
    }
}
