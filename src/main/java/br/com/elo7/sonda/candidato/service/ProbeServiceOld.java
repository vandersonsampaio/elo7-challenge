package br.com.elo7.sonda.candidato.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;
import br.com.elo7.sonda.candidato.controller.dto.InputDTO;
import br.com.elo7.sonda.candidato.controller.dto.ProbeDTO;
import br.com.elo7.sonda.candidato.model.enums.Command;
import br.com.elo7.sonda.candidato.model.entity.Planet;
import br.com.elo7.sonda.candidato.model.entity.Probe;
import br.com.elo7.sonda.candidato.persistence.Planets;
import br.com.elo7.sonda.candidato.persistence.Probes;

@Service
public class ProbeServiceOld {

	private Planets planets;

	private Probes probes;
	
	public List<Probe> landProbes(InputDTO input) {
		Planet planet = concertPlanet(input);
		planets.save(planet);
		
		List<Probe> convertedProbes = convertAndMoveProbes(input, planet);
		convertedProbes.forEach(probe -> probes.save(probe));
		
		return convertedProbes;
	}
	
	@VisibleForTesting
	void applyCommandToProbe(Probe probe, char command) {
		switch (command) {
			case Command.R:
				turnProbeRight(probe);
				break;
			case Command.L:
				turnProbeLeft(probe);
				break;
			case Command.M:
				moveProbeForward(probe);
				break;
		}
	}

	private void moveProbeForward(Probe probe) {
		int newX = probe.getX();
		int newY = probe.getY();
		/*switch (probe.getDirection()) {
			case Direction.N:
				newY++;
				break;
			case Direction.W:
				newX--;
				break;
			case Direction.S:
				newY--;
				break;
			case Direction.E:
				newX++;
				break;
		}
		probe.setX(newX);
		probe.setY(newY);*/
	}

	private void turnProbeLeft(Probe probe) {
		//char newDirection = Direction.N;
		/*switch (probe.getDirection()) {
			case Direction.N:
				newDirection = Direction.W;
				break;
			case Direction.W:
				newDirection = Direction.S;
				break;
			case Direction.S:
				newDirection = Direction.E;
				break;
			case Direction.E:
				newDirection = Direction.N;
				break;
		}
		probe.setDirection(newDirection);*/
	}
	
	private void turnProbeRight(Probe probe) {
		/*char newDirection = Direction.N;
		switch (probe.getDirection()) {
			case Direction.N:
				newDirection = Direction.E;
				break;
			case Direction.E:
				newDirection = Direction.S;
				break;
			case Direction.S:
				newDirection = Direction.W;
				break;
			case Direction.W:
				newDirection = Direction.N;
				break;
		}
		System.out.println(newDirection);
		probe.setDirection(newDirection);*/
		
	}
	
	private List<Probe> convertAndMoveProbes(InputDTO input, Planet planet) {
		return input.getProbes()
						.stream().map(probeDto -> {
							Probe probe = convertProbe(probeDto, planet);
							moveProbeWithAllCommands(probe, probeDto);
							return probe;
						}).collect(Collectors.toList());
	}

	private void moveProbeWithAllCommands(Probe probe, ProbeDTO probeDTO) {
		for (char command : probeDTO.getCommands().toCharArray()) {
			applyCommandToProbe(probe, command);
		}
	}
	
	private Probe convertProbe(ProbeDTO probeDto, Planet planet) {
		/*Probe probe = new Probe();
		probe.setPlanet(planet);
		probe.setX(probeDto.getX());
		probe.setY(probeDto.getY());
		probe.setDirection(probeDto.getDirection());
		return probe;*/
		return null;
	}
	
	private Planet concertPlanet(InputDTO input) {
	/*	Planet planet = new Planet();
		planet.setHeight(input.getHeight());
		planet.setWidth(input.getWidth());
		return planet;*/
		return null;
	}
}
