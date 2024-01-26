package br.com.elo7.sonda.candidato.service;

import br.com.elo7.sonda.candidato.model.entity.Probe;

public class LeftCommand implements MovimentationCommand {

    @Override
    public void action(Probe probe) {
        probe.turnLeft();
    }
}
