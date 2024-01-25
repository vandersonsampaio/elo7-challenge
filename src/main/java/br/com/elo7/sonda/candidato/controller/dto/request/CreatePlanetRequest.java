package br.com.elo7.sonda.candidato.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@Getter
public class CreatePlanetRequest {

    @NotBlank
    private final String name;
    @Positive
    private final int width;
    @Positive
    private final int height;
}
