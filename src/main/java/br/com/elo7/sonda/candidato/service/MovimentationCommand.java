package br.com.elo7.sonda.candidato.service;

import br.com.elo7.sonda.candidato.model.entity.Probe;

public interface MovimentationCommand {

    void action(Probe probe);
}
