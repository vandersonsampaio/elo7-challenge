package br.com.elo7.sonda.candidato.model.repository;

import br.com.elo7.sonda.candidato.model.entity.Planet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlanetRepository extends MongoRepository<Planet, String> {

    boolean existsByName(String name);
    Optional<Planet> findByName(String name);
}
