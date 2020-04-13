package es.um.asio.service.rdf.test;


import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.RDFFormat;
import org.junit.Test;
import org.junit.runner.RunWith;
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
   
	
	@Test
	public void createRDFConceptoGrupo() {
		GeneralBusEvent<InputData<DataSetData>> input = AsioMockupBuilder.createBusEventDataSet(DatasetTypeTest.CONCEPTO_GRUPO);
		ManagementBusEvent<Model> managementBusEvent = this.rdfService.createRDF(input);
		
		String result = RDFUtil.toString(managementBusEvent.getModel(), RDFFormat.TURTLE_PRETTY);
		
		System.out.println(result);
		
		// managementBusEvent.getModel().write(System.out);
	}
}
