package es.um.asio.service.rdf.impl;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.LinkedHashMap;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import es.um.asio.abstractions.domain.ManagementBusEvent;
import es.um.asio.domain.InputData;
import es.um.asio.service.model.GeneralBusEvent;
import es.um.asio.service.model.ModelWrapper;
import es.um.asio.service.rdf.RDFPojoBuilderService;
import es.um.asio.service.rdf.RDFPojoLinkBuilderService;
import es.um.asio.service.uris.URISGeneratorClient;
import es.um.asio.service.util.asserts.rdf.ModelAssert;

@RunWith(SpringRunner.class)
@ActiveProfiles("unit-test")
public class RDFPojoBuilderServiceImplTest {

	@Autowired
	private RDFPojoBuilderService rdfPojoBuilderService;

	@MockBean
	private URISGeneratorClient urisGenerator;

	@MockBean
	private RDFPojoLinkBuilderService rDFPojoLinkingBuilderService;

	@TestConfiguration
	static class RDFPojoBuilderServiceImplTestConfig {
		@Bean
		@Primary
		RDFPojoBuilderService RDFPojoBuilderService() {
			return new RDFPojoBuilderServiceImpl();
		}
	}

	@Before
	public void setUp() {
		Mockito.when(urisGenerator.rootUri()).thenReturn("http://dummy.org");
		Mockito.when(urisGenerator.createResourceID(Mockito.any())).thenReturn("http://dummy.org/resourceID");
		Mockito.when(urisGenerator.createPropertyURI(Mockito.any(), Mockito.any()))
				.thenReturn("http://www.w3.org/2001/asio-rdf/3.0#");
		Mockito.when(urisGenerator.createResourceTypeURI(Mockito.any())).thenAnswer(invocation -> {
			return "http://dummy.org/".concat(invocation.getArgument(0));
		});
	}

	@Test
	public void inkoveBuilder() {
		GeneralBusEvent input = new GeneralBusEvent<InputData>();
		ManagementBusEvent busEvent = rdfPojoBuilderService.inkoveBuilder(input);

		assertNotEquals("universidad", busEvent);
		assertNotEquals("grupoinvestigacion", busEvent);
		assertNotEquals("proyecto", busEvent);
	}

	@Test
	public void createRDF() throws JSONException {
		LinkedHashMap<String, Object> obj = new LinkedHashMap<String, Object>();
		obj.put("id", "SUBV");
		obj.put("codTipoFuente", "PGE");
		obj.put("@class", "FinanciacionProyecto");
		obj.put("tipoFinanciacion", "SUBVENCIÓN");

		obj.put("tipoFuente", "PRESUPUESTOS GENERALES DEL ESTADO");

		ModelWrapper model = rdfPojoBuilderService.createRDF(obj);

		ModelAssert.assertThat(model.getModel()).containsPredicate("http://www.w3.org/1999/02/22-rdf-syntax-ns#type")
				.withValue("http://dummy.org/FinanciacionProyecto");

	}

}
