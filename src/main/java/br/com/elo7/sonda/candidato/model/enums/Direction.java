package br.com.elo7.sonda.candidato.model.enums;

import lombok.Getter;

public enum Direction {

	N(0, 0, 1),
	E(1, 1, 0),
	S(2, 0, -1),
	W(3, -1, 0);

	final int value;
	@Getter
	final int movX;
	@Getter
	final int movY;

	private static final Direction[] DIRECTIONS;

	Direction(int value, int movX, int movY) {
		this.value = value;
		this.movX = movX;
		this.movY = movY;
	}

	static {
		DIRECTIONS = new Direction[values().length];

		for (Direction d: values()) {
			DIRECTIONS[d.value] = d;
		}
	}

	public static Direction nextElement(Direction current) {
		if (current.value + 1 == DIRECTIONS.length) {
			return DIRECTIONS[0];
		}

		return DIRECTIONS[current.value + 1];
	}

	public static Direction previousElement(Direction current) {
		if (current.value == 0) {
			return DIRECTIONS[DIRECTIONS.length - 1];
		}

		return DIRECTIONS[current.value - 1];
	}
}
