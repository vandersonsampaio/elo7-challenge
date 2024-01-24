package br.com.elo7.sonda.candidato.persistence;

import java.util.Optional;

import br.com.elo7.sonda.candidato.model.Probe;

public interface Probes {

	void save(Probe probe);

	Optional<Probe> findById(int id);

}
