package es.um.asio.service.rdf.test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import es.um.asio.service.rdf.RDFDatasetBuilderService;
import es.um.asio.service.rdf.RDFGeneratorIDService;
import es.um.asio.service.rdf.RDFPojoBuilderService;
import es.um.asio.service.rdf.RDFService;
import es.um.asio.service.rdf.impl.RDFDatasetBuilderServiceImpl;
import es.um.asio.service.rdf.impl.RDFGeneratorIDServiceImpl;
import es.um.asio.service.rdf.impl.RDFPojoBuilderServiceImpl;
import es.um.asio.service.rdf.impl.RDFServiceImpl;
import es.um.asio.service.uris.URISGeneratorClient;
import es.um.asio.service.uris.impl.URISGeneratorClientImpl;
import es.um.asio.service.uris.impl.URISGeneratorClientMockupImpl;

@RunWith(SpringRunner.class)
public class RDFServiceTest {

	@Autowired
	private RDFService rdfService;

	@TestConfiguration
	static class RDFServiceTestConfiguration {
		@Bean
		public RDFService rdfService() {
			return new RDFServiceImpl();
		}

		@Bean
		public RDFDatasetBuilderService rdfDatasetBuilderService() {
			return new RDFDatasetBuilderServiceImpl();
		}

		@Bean
		@ConditionalOnProperty(prefix = "app.generator-uris.mockup", name = "enabled", havingValue = "true", matchIfMissing = false)
		public URISGeneratorClient urisGeneratorClientMockup() {
			return new URISGeneratorClientMockupImpl();
		}
		
		@Bean
		@ConditionalOnProperty(prefix = "app.generator-uris.mockup", name = "enabled", havingValue = "false", matchIfMissing = true)
		public URISGeneratorClient urisGeneratorClient() {
			return new URISGeneratorClientImpl();
		}
		
		@Bean
		public RestTemplate restTemplate() {
			return new RestTemplate();
		}
		
		@Bean
		public RDFGeneratorIDService rdfGeneratorIDService() {
			return new RDFGeneratorIDServiceImpl();
		}

		@Bean
		public RDFPojoBuilderService rdfPojoBuilderService() {
			return new RDFPojoBuilderServiceImpl();
		}
	}
}
