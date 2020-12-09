package es.um.asio.service.runners.stepdefs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

import es.um.asio.service.model.ModelWrapper;
import es.um.asio.service.rdf.RDFPojoBuilderService;
import es.um.asio.service.rdf.RDFPojoLinkBuilderService;
import es.um.asio.service.rdf.impl.RDFPojoBuilderServiceImpl;
import es.um.asio.service.uris.URISGeneratorClient;
import es.um.asio.service.uris.impl.URISGeneratorClientImpl;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {
		ManagementSystemStepDefinitionsTest.MessageServiceConfiguration.class })
public class ManagementSystemStepDefinitionsTest {

	LinkedHashMap<String, Object> message;

	@Autowired
	private RDFPojoBuilderService rdfPojoBuilderService;

	@Autowired
	@MockBean
	private URISGeneratorClient urisGenerator;

	@MockBean
	private RDFPojoLinkBuilderService rDFPojoLinkingBuilderService;

	@MockBean
	private RestTemplate restTemplate;

	private static final String SPANISH = "@es";
	private static final String ENGLISH = "@en";
	private static final String FRENCH = "@fr";
	private static final String GERMAN = "@ge";

	@TestConfiguration
	static class MessageServiceConfiguration {
		@Bean
		@Primary
		RDFPojoBuilderService RDFPojoBuilderService() {
			return new RDFPojoBuilderServiceImpl();
		}

		@Bean
		@Primary
		URISGeneratorClient URISGeneratorClient() {
			return new URISGeneratorClientImpl();
		}
	}

	@Before
	public void setUp() {
		Mockito.when(urisGenerator.rootUri()).thenReturn("http://dummy.org");
		Mockito.when(urisGenerator.createResourceID(Mockito.any())).thenReturn("http://dummy.org/resourceID");
		Mockito.when(urisGenerator.createPropertyURI(Mockito.any(), Mockito.any()))
				.thenReturn("http://www.w3.org/2001/asio-rdf/3.0#");
		Mockito.when(urisGenerator.createResourceTypeURI(Mockito.any())).thenAnswer(invocation -> {
			return "http://dummy.org/";
		});
	}

	@Given("^a new message arrives to managementdata queue$")
	public void a_new_message_in_management_data_queue(DataTable entry) {

		List<Map<String, String>> list = entry.asMaps(String.class, String.class);

		LinkedHashMap<String, Object> obj = new LinkedHashMap<String, Object>();

		for (int i = 0; i < list.size(); i++) {
			obj.put(list.get(i).get("Property"), list.get(i).get("Value"));
		}
		message = obj;
	}

	@Then("^the management-system creates Project RDF object in Spanish language$")
	public void the_management_system_creates_project_RDF_object_spanish_language() {

		ModelWrapper model = rdfPojoBuilderService.createRDF(message);

		assertEquals(true, model.getModel().getGraph().toString().contains(SPANISH));
	}

	@Then("^the management-system creates Person RDF object in English language$")
	public void the_management_system_creates_person_RDF_object_in_english_language() {

		ModelWrapper model = rdfPojoBuilderService.createRDF(message);

		assertEquals(false, model.getModel().getGraph().toString().contains(ENGLISH));
	}

	@Then("^the management-system creates Articule RDF object in French language$")
	public void the_management_system_creates_person_RDF_object_in_french_language() {

		ModelWrapper model = rdfPojoBuilderService.createRDF(message);

		assertEquals(false, model.getModel().getGraph().toString().contains(FRENCH));
	}

	@Then("^the management-system creates Grant RDF object in German language$")
	public void the_management_system_creates_person_RDF_object_in_German_language() {

		ModelWrapper model = rdfPojoBuilderService.createRDF(message);

		assertEquals(false, model.getModel().getGraph().toString().contains(GERMAN));
	}

	@Then("^the management-system creates Activity RDF object$")
	public void the_management_system_creates_activity_RDF_object() {

		ModelWrapper model = rdfPojoBuilderService.createRDF(message);

		assertEquals(true, model.getModel().toString().contains("Actividad"));
	}

	@Then("^the management-system creates Project RDF object with nested objects$")
	public void the_management_system_creates_project_RDF_object_with_nested_objects() {
		LinkedHashMap<String, Object> gi = new LinkedHashMap<String, Object>();
		gi.put("id", "1");
		gi.put("@class", "grupoInvestigacion");
		message.put("grupoInvestigacion", gi);
		ModelWrapper model = rdfPojoBuilderService.createRDF(message);

		assertEquals(true, model.getModel().toString().contains("grupoInvestigacion"));
	}

	@Then("^the input processor sends xml file to Management-System$")
	public void the_input_processor_sends_xml_file_to_Management_System() {

	}

}
