package br.com.elo7.sonda.candidato.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MovimentProbeRequest {

    @Pattern(regexp = "^[LRM]+$", message = "Only three moviment posible. L R M")
    String moviment;
}
