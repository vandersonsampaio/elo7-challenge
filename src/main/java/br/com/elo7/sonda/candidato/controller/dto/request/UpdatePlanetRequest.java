package br.com.elo7.sonda.candidato.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Positive;

@AllArgsConstructor
@Getter
public class UpdatePlanetRequest {

    @Positive
    private int width;
    @Positive
    private int height;
}
