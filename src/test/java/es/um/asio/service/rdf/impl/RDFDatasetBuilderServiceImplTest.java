package es.um.asio.service.rdf.impl;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import es.um.asio.abstractions.domain.ManagementBusEvent;
import es.um.asio.domain.InputData;
import es.um.asio.service.model.GeneralBusEvent;
import es.um.asio.service.rdf.RDFDatasetBuilderService;
import es.um.asio.service.rdf.RDFPojoBuilderService;
import es.um.asio.service.uris.URISGeneratorClient;

@RunWith(SpringRunner.class)
@ActiveProfiles("unit-test")
public class RDFDatasetBuilderServiceImplTest {

	@Autowired
	private RDFDatasetBuilderService rdfDatasetBuilderService;

	@MockBean
	private RDFPojoBuilderService rdfPojoBuilderService;

	@MockBean
	private URISGeneratorClient urisGeneratorClient;

	@Configuration
	static class RDFDatasetBuilderServiceImplTestConfig {
		@Bean
		@Primary
		RDFDatasetBuilderService RDFCvnBuilderService() {
			return new RDFDatasetBuilderServiceImpl();
		}
	}

	@Test
	public void inkoveBuilder() {
		GeneralBusEvent input = new GeneralBusEvent<InputData>();
		ManagementBusEvent busEvent = rdfDatasetBuilderService.inkoveBuilder(input);
		assertNotEquals("actividad", busEvent);
	}
}
