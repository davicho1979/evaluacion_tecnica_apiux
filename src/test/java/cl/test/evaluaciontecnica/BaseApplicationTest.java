package cl.test.evaluaciontecnica;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import cl.EvaluacionTecnicaApplication;


@SpringBootTest(classes = {EvaluacionTecnicaApplication.class})
@AutoConfigureMockMvc
@TestPropertySource(locations = {"classpath:environment_testunits.properties"})
public class BaseApplicationTest {

	@Autowired
	protected MockMvc mockMvc;
	
	@MockBean
	protected RestTemplate restTemplate;

	protected MockRestServiceServer mockServer;

	@Before
	public void init() {
		mockServer = MockRestServiceServer.createServer(restTemplate);
	}
}
