package br.com.elo7.sonda.candidato.controller.dto.mapper;


import br.com.elo7.sonda.candidato.controller.dto.request.MovimentProbeRequest;
import br.com.elo7.sonda.candidato.model.enums.Command;
import br.com.elo7.sonda.candidato.service.MovimentationCommand;

import java.util.List;

public class MovimentProbeMapper {

    public static List<MovimentationCommand> from(MovimentProbeRequest request) {
        List<Command> commands = request.getMoviment().chars().mapToObj(c -> Command.valueOf(String.valueOf((char)c))).toList();
        return commands.stream().map(Command::getCommand).toList();
    }
}
