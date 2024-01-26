package br.com.elo7.sonda.candidato.model.expection;

public class UniqueEntityException extends RuntimeException {

    public UniqueEntityException(String entity, String value) {
        super("There is already a [" + entity + "] registered with this [" + value + "].");
    }
}
