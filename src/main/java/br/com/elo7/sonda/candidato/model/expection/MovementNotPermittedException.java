package br.com.elo7.sonda.candidato.model.expection;

public class MovementNotPermittedException extends RuntimeException {

    public MovementNotPermittedException(int x, int y) {
        super("Moviment [X: " + x + ", Y: " + y + "] not permitted.");
    }
}
