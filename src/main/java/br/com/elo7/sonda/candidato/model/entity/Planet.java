package br.com.elo7.sonda.candidato.model.entity;

import br.com.elo7.sonda.candidato.model.expection.EntityNotFoundException;
import br.com.elo7.sonda.candidato.model.expection.MovementNotPermittedException;
import br.com.elo7.sonda.candidato.model.expection.ResizeMapException;
import br.com.elo7.sonda.candidato.model.expection.SpaceNotAvailableException;
import br.com.elo7.sonda.candidato.model.expection.UniqueEntityException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document("Planet")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Planet {

	@Id
	private String id;

	@Getter
	@Indexed(unique = true)
	@EqualsAndHashCode.Include
	@Field("Name")
	private String name;

	@Getter
	@Field("Width")
	private int width;

	@Getter
	@Field("Height")
	private int height;

	@Field("Map")
	private int[][] map;

	@Getter
	@Field("Probes")
	private List<Probe> probes;

	@Getter
	@Field("Register_Time")
	private LocalDateTime registerTime;

	@Getter
	@Field("Update_Time")
	private LocalDateTime updateTime;

	public Planet() {
		probes = new ArrayList<>();
		registerTime = LocalDateTime.now();
	}

	public Planet(String name, int width, int height) {
		this();
		this.name = name;
		this.width = width;
		this.height = height;

		map = new int[width][height];
	}

	public void resizeMap(int width, int height) {
		if (this.height == height && this.width == width) {
			throw new ResizeMapException(name, width, height);
		}

		if ((width < this.width || height < this.height) &&
				probes.stream().anyMatch(p -> p.getX() >= width || p.getY() >= height)) {
			throw new ResizeMapException(name, width, height);
		}

		this.width = width;
		this.height = height;

		int[][] newMap = new int[width][height];
		probes.forEach(p -> newMap[p.getX()][p.getY()] = 1);

		map = newMap;

		update();
	}

	public Probe getProb(String name) {
		return probes.stream().filter(p -> p.getName().equals(name)).findAny()
				.orElseThrow(() -> new EntityNotFoundException(Probe.class.getSimpleName(), name));
	}

	public void addProbe(Probe probe) {
		if (probes.stream().anyMatch(p -> p.equals(probe))) {
			throw new UniqueEntityException(Probe.class.getSimpleName(), probe.getName());
		}

		if (!positionEmpty(probe.getX(), probe.getY())) {
			throw new SpaceNotAvailableException(probe.getX(), probe.getY());
		}

		map[probe.getX()][probe.getY()] = 1;
		probes.add(probe);

		update();
	}

	public boolean positionEmpty(int x, int y) {
		return x < width && y < height && map[x][y] == 0;
	}

	public void departure(String probeName) {
		Probe probe = getProb(probeName);
		map[probe.getX()][probe.getY()] = 0;
		probes.remove(probe);

		update();
	}

	public void moveProbe(String probeName) {
		Probe probe = getProb(probeName);
		int x = probe.getX();
		int y = probe.getY();
		probe.move();

		if (probe.getX() >= width || probe.getY() >= height || probe.getX() < 0 || probe.getY() < 0) {
			throw new MovementNotPermittedException(probe.getX(), probe.getY());
		}

		if (!positionEmpty(probe.getX(), probe.getY())) {
			throw new SpaceNotAvailableException(probe.getX(), probe.getY());
		}

		map[probe.getX()][probe.getY()] = 1;
		map[x][y] = 0;

		update();
	}

	private void update() {
		updateTime = LocalDateTime.now();
	}
}
