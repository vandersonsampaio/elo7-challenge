package br.com.elo7.sonda.candidato.model.entity;

import lombok.Getter;

import java.util.List;

@Getter
public class Planet {

	private int id;

	private String name;
	private int width;
	private int height;

	private int[][] maps;

	private List<Probe> probs;
	
	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Planet) {
			return ((Planet) obj).id == this.id;
		}
		return false;
	}

	public void setId(int id) {
		this.id = id;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public void setHeight(int height) {
		this.height = height;
	}
}
