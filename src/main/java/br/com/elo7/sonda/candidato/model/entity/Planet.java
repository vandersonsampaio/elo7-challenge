package br.com.elo7.sonda.candidato.model.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document("Planet")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Planet {

	@Id
	private Long id;

	@Getter
	@Indexed(unique = true)
	@EqualsAndHashCode.Include
	private String name;

	@Getter
	private int width;

	@Getter
	private int height;

	private int[][] map;

	@Getter
	private final List<Probe> probes;

	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private LocalDateTime registerTime;

	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private LocalDateTime updateTime;

	public Planet() {
		probes = new ArrayList<>();
	}

	public Planet(String name, int width, int height) {
		this();
		this.name = name;
		this.width = width;
		this.height = height;

		map = new int[width][height];
	}

	public void resizeDimentions(int width, int height) {
		if (this.height == height && this.width == width) {
			throw new RuntimeException();
		}

		if ((width < this.width || height < this.height) &&
				probes.stream().anyMatch(p -> p.getX() >= width || p.getY() >= height)) {
			throw new RuntimeException();
		}

		this.width = width;
		this.height = height;

		int[][] newMap = new int[width][height];
		probes.forEach(p -> newMap[p.getX()][p.getY()] = 1);

		map = newMap;
	}

	public Probe getProb(String name) {
		return probes.stream().filter(p -> p.getName().equals(name)).findAny().orElseThrow();
	}

	public void addProbe(Probe probe) {
		if (probes.stream().anyMatch(p -> p.equals(probe))) {
			throw new RuntimeException();
		}

		if (map[probe.getX()][probe.getY()] != 0) {
			throw new RuntimeException();
		}

		map[probe.getX()][probe.getY()] = 1;
		probes.add(probe);
	}

	public boolean positionEmpty(int x, int y) {
		return map[x][y] == 0;
	}
}
