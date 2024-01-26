package br.com.elo7.sonda.candidato.model.expection;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String entity, String name) {
        super("Entity [" + entity +"] not found. Name: " + name + ".");
    }
}
