package br.com.elo7.sonda.candidato.model.enums;

import br.com.elo7.sonda.candidato.service.LeftCommand;
import br.com.elo7.sonda.candidato.service.MoveCommand;
import br.com.elo7.sonda.candidato.service.MovimentationCommand;
import br.com.elo7.sonda.candidato.service.RightCommand;
import lombok.Getter;

public enum Command {

	L(new LeftCommand()),
	M(new MoveCommand()),
	R(new RightCommand());

	@Getter
	private final MovimentationCommand command;

	Command(MovimentationCommand command) {
		this.command = command;
	}
}
