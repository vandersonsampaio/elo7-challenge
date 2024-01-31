package br.com.elo7.sonda.candidato.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Positive;

@AllArgsConstructor
@Getter
public class UpdatePlanetRequest {

    @Positive(message = "Width cannot be negative or zero.")
    private int width;
    @Positive(message = "Height cannot be negative or zero.")
    private int height;
}
