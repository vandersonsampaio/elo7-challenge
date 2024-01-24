package br.com.elo7.sonda.candidato.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.elo7.sonda.candidato.model.Probe;

@SpringBootTest
public class ProbeServiceTest {
	
	@Autowired
	private ProbeServiceOld subject;
	
	@Test
	public void should_change_probe_direction_from_N_To_W_when_receive_the_command_L() {
		Probe probe = new Probe();
		probe.setDirection('N');
		subject.applyCommandToProbe(probe, 'L');
		assertEquals('W', probe.getDirection());		
	}
	
	@Test
	public void should_change_probe_direction_from_W_To_S_when_receive_the_command_L() {
		Probe probe = new Probe();
		probe.setDirection('W');
		subject.applyCommandToProbe(probe, 'L');
		assertEquals('S', probe.getDirection());		
	}
	
	@Test
	public void should_change_probe_direction_from_S_To_E_when_receive_the_command_L() {
		Probe probe = new Probe();
		probe.setDirection('S');
		subject.applyCommandToProbe(probe, 'L');
		assertEquals('E', probe.getDirection());		
	}
	
	@Test
	public void should_change_probe_direction_from_E_To_N_when_receive_the_command_L() {
		Probe probe = new Probe();
		probe.setDirection('E');
		subject.applyCommandToProbe(probe, 'L');
		assertEquals('N', probe.getDirection());		
	}
	
	@Test
	public void should_change_probe_direction_from_N_To_E_when_receive_the_command_R() {
		Probe probe = new Probe();
		probe.setDirection('N');
		subject.applyCommandToProbe(probe, 'R');
		assertEquals('E', probe.getDirection());		
	}
	
	@Test
	public void should_change_probe_direction_from_E_To_S_when_receive_the_command_R() {
		Probe probe = new Probe();
		probe.setDirection('E');
		subject.applyCommandToProbe(probe, 'R');
		assertEquals('S', probe.getDirection());		
	}
	
	@Test
	public void should_change_probe_direction_from_S_To_W_when_receive_the_command_R() {
		Probe probe = new Probe();
		probe.setDirection('S');
		subject.applyCommandToProbe(probe, 'R');
		assertEquals('W', probe.getDirection());		
	}
	
	@Test
	public void should_change_probe_direction_from_W_To_N_when_receive_the_command_R() {
		Probe probe = new Probe();
		probe.setDirection('W');
		subject.applyCommandToProbe(probe, 'R');
		assertEquals('N', probe.getDirection());		
	}

	@Test
	public void should_change_probe_position_from_1_1_N_To_1_2_N_when_receive_the_command_M() {
		Probe probe = new Probe();
		probe.setX(1);
		probe.setY(1);
		probe.setDirection('N');
		subject.applyCommandToProbe(probe, 'M');
		assertEquals(2, probe.getY());
		assertEquals(1, probe.getX());
		assertEquals('N', probe.getDirection());
	}
	
	@Test
	public void should_change_probe_position_from_1_1_S_To_1_0_S_when_receive_the_command_M() {
		Probe probe = new Probe();
		probe.setX(1);
		probe.setY(1);
		probe.setDirection('S');
		subject.applyCommandToProbe(probe, 'M');
		assertEquals(0, probe.getY());
		assertEquals(1, probe.getX());
		assertEquals('S', probe.getDirection());
	}
	
	@Test
	public void should_change_probe_position_from_1_1_W_To_0_1_W_when_receive_the_command_M() {
		Probe probe = new Probe();
		probe.setX(1);
		probe.setY(1);
		probe.setDirection('W');
		subject.applyCommandToProbe(probe, 'M');
		assertEquals(0, probe.getX());
		assertEquals(1, probe.getY());
		assertEquals('W', probe.getDirection());
	}
	
	@Test
	public void should_change_probe_position_from_1_1_E_To_2_1_E_when_receive_the_command_M() {
		Probe probe = new Probe();
		probe.setX(1);
		probe.setY(1);
		probe.setDirection('E');
		subject.applyCommandToProbe(probe, 'M');
		assertEquals(2, probe.getX());
		assertEquals(1, probe.getY());
		assertEquals('E', probe.getDirection());
	}
}
