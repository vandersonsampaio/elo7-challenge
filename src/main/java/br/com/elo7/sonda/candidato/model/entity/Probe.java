package br.com.elo7.sonda.candidato.model.entity;

import br.com.elo7.sonda.candidato.model.enums.Direction;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Probe {

	@EqualsAndHashCode.Include
	private String name;

	private int x;

	private int y;

	private Direction direction;

	private LocalDateTime registerTime;

	private LocalDateTime updateTime;

	public Probe(String name, int x, int y, Direction direction) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.registerTime = LocalDateTime.now();
	}

	public void turnLeft() {
		direction = Direction.previousElement(direction);
	}

	public void turnRight() {
		direction = Direction.nextElement(direction);
	}

	public void move() {
		x += direction.getMovX();
		y += direction.getMovY();
	}

}
