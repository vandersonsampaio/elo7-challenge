package br.com.elo7.sonda.candidato.service;

import br.com.elo7.sonda.candidato.model.entity.Probe;

public class MoveCommand implements MovimentationCommand {

    @Override
    public void action(Probe probe) {
        //TODO verificar crash com outros probes
        probe.move();
    }
}
