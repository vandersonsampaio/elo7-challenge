package br.com.elo7.sonda.candidato.dto;

import java.util.List;

public class InputDTO {
	private int width; 
	private int height;
	private List<ProbeDTO> probes;

	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public List<ProbeDTO> getProbes() {
		return probes;
	}
	public void setProbes(List<ProbeDTO> probes) {
		this.probes = probes;
	}
}
