package es.um.asio.service.rdf.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.LinkedHashMap;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import es.um.asio.abstractions.domain.ManagementBusEvent;
import es.um.asio.domain.InputData;
import es.um.asio.service.model.GeneralBusEvent;
import es.um.asio.service.model.ModelWrapper;
import es.um.asio.service.rdf.RDFPojoBuilderService;
import es.um.asio.service.uris.URISGeneratorClient;

@RunWith(SpringRunner.class)
@ActiveProfiles("unit-test")
public class RDFPojoBuilderServiceImplTest {
	
	@Autowired
    private RDFPojoBuilderService rdfPojoBuilderService;
	
	@MockBean
	private URISGeneratorClient urisGeneratorClient;


	@Configuration
    static class RDFDatasetBuilderServiceImplTestConfig {
        @Bean
        RDFPojoBuilderService RDFPojoBuilderService() {
            return new RDFPojoBuilderServiceImpl();
        }
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
		
		LinkedHashMap<String,Object> gi = new LinkedHashMap<String, Object>();
		gi.put("id", "1");
		
		LinkedHashMap<String,Object> obj = new LinkedHashMap<String, Object>();
		obj.put("id", "1");
		obj.put("nombre", "project-dummy");
		obj.put("clase", "proyecto");
		obj.put("grupoInvestigacion", gi);
				
		ModelWrapper model = rdfPojoBuilderService.createRDF(obj);
						
		assertEquals("http://hercules.org/um/es-ES/rec/proyecto/1", model.getModelId());
		
    }
	

}
