package br.com.elo7.sonda.candidato.controller.dto.request;

import br.com.elo7.sonda.candidato.model.enums.Direction;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateProbeRequest {

    private String name;
    private int x;
    private int y;
    private Direction direction;
}
