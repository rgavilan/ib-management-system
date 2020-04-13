package es.um.asio.service.rdf.test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

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
	public void convertToRDF() {
		GeneralBusEvent<InputData<DataSetData>> input = new GeneralBusEvent<InputData<DataSetData>>();
		// this.rdfDatasetBuilderService.createRDF(input);
		this.rdfService.createRDF(input);
	}
}
