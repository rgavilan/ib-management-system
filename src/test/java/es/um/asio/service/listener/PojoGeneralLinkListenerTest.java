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
import es.um.asio.domain.PojoLinkData;
import es.um.asio.service.model.GeneralBusEvent;
import es.um.asio.service.notification.service.NotificationService;
import es.um.asio.service.rdf.RDFService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { PojoGeneralLinkListenerTest.PojoGeneralLinkListenerTestConfig.class, PojoGeneralLinkListener.class })
@ActiveProfiles("unit-test")
public class PojoGeneralLinkListenerTest {
	
	@Autowired
	private PojoGeneralLinkListener pojoGeneralLinkListener;
	
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
	
	private final static PojoLinkData MESSAGE = new PojoLinkData();
	
	
	@TestConfiguration
	static class PojoGeneralLinkListenerTestConfig {
		@Bean
		PojoGeneralLinkListener pojoGeneralLinkListener() {
			return new PojoGeneralLinkListener();
		}
	}
	

	@Test
	public void listen() {
		ManagementBusEvent managementBusEvent = new ManagementBusEvent();
		
		Mockito.when(rdfService.createRDF(Mockito.any())).thenReturn(managementBusEvent);
		pojoGeneralLinkListener.listen(MESSAGE);
		verify(rdfService, times(1)).createRDF(ArgumentMatchers.any(GeneralBusEvent.class));
		verify(jmsTemplate, times(1)).convertAndSend(ArgumentMatchers.any(Queue.class), ArgumentMatchers.any(ManagementBusEvent.class));
	}
}
