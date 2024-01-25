package br.com.elo7.sonda.candidato.model.enums;

public enum Direction {

	N(0),
	E(1),
	S(2),
	W(3);

	final int value;

	private static final Direction[] DIRECTIONS;

	Direction(int value) {
		this.value = value;
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
