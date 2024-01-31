package br.com.elo7.sonda.candidato;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(locations = "classpath:application-it.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SevenMarsApplicationIT {

    @LocalServerPort
    protected int port;
}
