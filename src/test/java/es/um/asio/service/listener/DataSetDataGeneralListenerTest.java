package es.um.asio.service.listener;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import es.um.asio.abstractions.domain.ManagementBusEvent;
import es.um.asio.domain.DataSetData;
import es.um.asio.domain.InputData;
import es.um.asio.service.kafka.KafkaService;
import es.um.asio.service.model.GeneralBusEvent;
import es.um.asio.service.rdf.RDFService;

@RunWith(SpringRunner.class)
@ActiveProfiles("unit-test")
public class DataSetDataGeneralListenerTest {
	
	@Autowired
	private DataSetDataGeneralListener dataGeneralListener;
	
	@MockBean
    private KafkaService kafkaService;
    
	@MockBean
    private RDFService rdfService;
	
	private final static InputData<DataSetData> MESSAGE = new InputData<>();
	
	
	@TestConfiguration
	static class DataSetDataGeneralListenerTestConfig {
		@Bean
		DataSetDataGeneralListener dataSetDataGeneralListener() {
			return new DataSetDataGeneralListener();
		}
	}
	

	@Test
	public void listen() {
		ManagementBusEvent managementBusEvent = new ManagementBusEvent();
		
		Mockito.when(rdfService.createRDF(Mockito.any())).thenReturn(managementBusEvent);
		dataGeneralListener.listen(MESSAGE);
		verify(rdfService, times(1)).createRDF(ArgumentMatchers.any(GeneralBusEvent.class));
		verify(kafkaService, times(1)).send(managementBusEvent);
	}
}
