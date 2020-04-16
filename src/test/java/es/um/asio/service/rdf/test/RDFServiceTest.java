package es.um.asio.service.rdf.test;

import static org.junit.Assert.assertTrue;

import java.io.StringReader;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import es.um.asio.abstractions.domain.ManagementBusEvent;
import es.um.asio.domain.DataSetData;
import es.um.asio.domain.InputData;
import es.um.asio.service.model.GeneralBusEvent;
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
import es.um.asio.service.util.RDFUtil;
import es.um.asio.service.util.dummy.data.ConceptoGrupoDummy;
import es.um.asio.service.util.test.AsioMockupBuilder;
import es.um.asio.service.util.test.DatasetTypeTest;

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
		@ConditionalOnProperty(prefix = "app.generator-uris.mockup", name = "enabled", havingValue = "true", matchIfMissing = true)
		public URISGeneratorClient urisGeneratorClientMockup() {
			return new URISGeneratorClientMockupImpl();
		}
		
		@Bean
		@ConditionalOnProperty(prefix = "app.generator-uris.mockup", name = "enabled", havingValue = "false", matchIfMissing = false)
		public URISGeneratorClient urisGeneratorClient() {
			return new URISGeneratorClientImpl();
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

	/**
	 * Read RDF concepto grupo from string.
	 */
	@Test
	public void readRDFConceptoGrupo() {
		
		 Model model = retrieveConceptoGrupo();
		 
		// we check the model's size
		Assert.assertEquals(5, model.size());
	}

	/**
	 * Creates the RDF concepto grupo.
	 */
	@Test
	public void createRDFConceptoGrupo() {

		GeneralBusEvent<InputData<DataSetData>> input = AsioMockupBuilder
				.createBusEventDataSet(DatasetTypeTest.CONCEPTO_GRUPO);

		ManagementBusEvent managementBusEvent = this.rdfService.createRDF(input);

		String strRDF = managementBusEvent.getModel();
		
		Model modelFromString = RDFUtil.toObject(strRDF);

		// we check the model's size
		Assert.assertEquals(5, modelFromString.size());
		
		Model conceptoGrupoModel = retrieveConceptoGrupo();

		// we check model from string and object they are the same
		assertTrue(modelFromString.isIsomorphicWith(conceptoGrupoModel));
	}
	
	/**
	 * Retrieve dummy concepto grupo.
	 *
	 * @return the model
	 */
	private Model retrieveConceptoGrupo() {
		StringReader s1 = new StringReader(ConceptoGrupoDummy.getRDF());
		Model model = ModelFactory.createDefaultModel();
		model.read(s1, null, "RDF/XML");
		
		return model;
	}

}
