package br.com.elo7.sonda.candidato.controller.dto.request;

import br.com.elo7.sonda.candidato.model.enums.Direction;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@AllArgsConstructor
@Getter
public class CreateProbeRequest {

    @NotBlank(message = "Name cannot be blank.")
    private String name;
    @PositiveOrZero(message = "X cannot be negative.")
    private int x;
    @PositiveOrZero(message = "Y cannot be negative.")
    private int y;
    @NotNull(message = "Direction cannot be null.")
    private Direction direction;
}
