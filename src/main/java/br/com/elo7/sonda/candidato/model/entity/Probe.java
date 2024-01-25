package br.com.elo7.sonda.candidato.model.entity;

import br.com.elo7.sonda.candidato.model.enums.Direction;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Probe {

	@Getter
	@EqualsAndHashCode.Include
	private String name;

	@Getter
	private int x;
	@Getter
	private int y;

	private Direction direction;

	private LocalDateTime registerTime;

	private LocalDateTime updateTime;

	public Probe(String name, int x, int y, Direction direction) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.direction = direction;
	}

	public void turnLeft() {
		direction = Direction.previousElement(direction);
	}

	public void turnRight() {
		direction = Direction.nextElement(direction);
	}

	public void move() {
		x += 1;
		y += 1;
	}

}
