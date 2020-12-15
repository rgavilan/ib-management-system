package es.um.asio.service.listener;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import javax.jms.Queue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import es.um.asio.abstractions.domain.ManagementBusEvent;
import es.um.asio.domain.PojoData;
import es.um.asio.service.model.GeneralBusEvent;
import es.um.asio.service.notification.service.NotificationService;
import es.um.asio.service.rdf.RDFService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { PojoGeneralListenerTest.PojoGeneralListenerTestConfig.class, PojoGeneralListener.class })
@ActiveProfiles("unit-test")
public class PojoGeneralListenerTest {
	
	@Autowired
	private PojoGeneralListener pojoGeneralListener;
	
	@MockBean
	private JmsTemplate jmsTemplate;
    
	@MockBean
    private RDFService rdfService;
	
	@MockBean
	private Queue queue;
	
	@MockBean
	private NotificationService notificationService;

	@MockBean
	private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
	
	private final static PojoData MESSAGE = new PojoData();
	
	
	@TestConfiguration
	static class PojoGeneralListenerTestConfig {
		@Bean
		PojoGeneralListener pojoGeneralListener() {
			return new PojoGeneralListener();
		}
	}
	

	@Test
	public void listen() {
		ManagementBusEvent managementBusEvent = new ManagementBusEvent();
		
		Mockito.when(rdfService.createRDF(Mockito.any())).thenReturn(managementBusEvent);
		pojoGeneralListener.listen(MESSAGE);
		verify(rdfService, times(1)).createRDF(ArgumentMatchers.any(GeneralBusEvent.class));
		verify(jmsTemplate, times(1)).convertAndSend(ArgumentMatchers.any(Queue.class), ArgumentMatchers.any(ManagementBusEvent.class));
	}
}
