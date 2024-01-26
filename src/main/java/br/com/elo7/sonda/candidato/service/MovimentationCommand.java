package br.com.elo7.sonda.candidato.service;

import br.com.elo7.sonda.candidato.model.entity.Planet;

public interface MovimentationCommand {

    void action(Planet planet, String probeName);
}
