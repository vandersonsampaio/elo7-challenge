package br.com.elo7.sonda.candidato.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@Getter
public class CreatePlanetRequest {

    @NotBlank(message = "Name cannot be blank.")
    private final String name;
    @Positive(message = "Width cannot be negative or zero.")
    private final int width;
    @Positive(message = "Height cannot be negative or zero.")
    private final int height;
}
