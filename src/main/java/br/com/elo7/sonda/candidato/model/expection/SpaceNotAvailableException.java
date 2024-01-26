package br.com.elo7.sonda.candidato.model.expection;

public class SpaceNotAvailableException extends RuntimeException {

    public SpaceNotAvailableException(int x, int y) {
        super("Space [X: " + x + ", Y: " + y + "] not available.");
    }
}
