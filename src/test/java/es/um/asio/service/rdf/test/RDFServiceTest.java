package es.um.asio.service.rdf.test;

import static org.junit.Assert.assertTrue;

import java.io.StringReader;
import java.io.StringWriter;

import org.apache.jena.atlas.lib.StrUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import org.apache.jena.riot.RDFLanguages;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import es.um.asio.service.util.RDFUtil;
import es.um.asio.service.util.test.AsioMockupBuilder;
import es.um.asio.service.util.test.DatasetTypeTest;

@RunWith(SpringRunner.class)
public class RDFServiceTest {

	/** Logger. */
	private final Logger logger = LoggerFactory.getLogger(RDFServiceTest.class);

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
		String x = "<rdf:RDF\r\n" + "    xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\r\n"
				+ "    xmlns:j.0=\"http://example.org/\"\r\n"
				+ "    xmlns:j.1=\"http://www.w3.org/2001/asio-rdf/3.0#\">\r\n"
				+ "  <j.0:ConceptoGrupoDummy rdf:about=\"http://example.org/E0A6-01/5/DESCRIPTORES/LENGUAJES+PLASTICOS\">\r\n"
				+ "    <j.1:texto>LENGUAJES PLASTICOS</j.1:texto>\r\n"
				+ "    <j.1:codTipoConcepto>DESCRIPTORES</j.1:codTipoConcepto>\r\n"
				+ "    <j.1:numero>5</j.1:numero>\r\n"
				+ "    <j.1:idGrupoInvestigacion>E0A6-01</j.1:idGrupoInvestigacion>\r\n"
				+ "  </j.0:ConceptoGrupoDummy>\r\n" + "</rdf:RDF>";
		{
			StringReader s = new StringReader(x);
			Model m = ModelFactory.createDefaultModel();
			RDFDataMgr.read(m, s, null, RDFLanguages.RDFXML);
		}
		StringReader s1 = new StringReader(x);
		Model model = ModelFactory.createDefaultModel();
		model.read(s1, null, "RDF/XML");

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

		ManagementBusEvent<Model> managementBusEvent = this.rdfService.createRDF(input);

		Model modelFromBusEvent = managementBusEvent.getModel();

		// we check the model's size
		Assert.assertEquals(5, modelFromBusEvent.size());

		String strRDF = RDFUtil.toString(modelFromBusEvent);

		Model modelFromString = RDFUtil.toObject(strRDF);

		// we check model from string and object they are the same
		assertTrue(modelFromString.isIsomorphicWith(modelFromBusEvent));
	}

}
