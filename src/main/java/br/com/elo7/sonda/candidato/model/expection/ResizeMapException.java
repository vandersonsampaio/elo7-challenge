package br.com.elo7.sonda.candidato.model.expection;

public class ResizeMapException extends RuntimeException {

    public ResizeMapException(String planetName, int newWidth, int newHeight) {
        super("It is not possible to resize Planet [" + planetName + "] to " + newWidth + " and " + newHeight + ".");
    }
}
