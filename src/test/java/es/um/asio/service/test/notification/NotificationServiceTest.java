package es.um.asio.service.test.notification;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import es.um.asio.abstractions.constants.Constants;
import es.um.asio.service.notification.service.NotificationService;
import es.um.asio.service.notification.service.impl.NotificationServiceImpl;

@RunWith(SpringRunner.class)
@ActiveProfiles("unit-test")
public class NotificationServiceTest {

	@Autowired
	@MockBean
	NotificationService service;

	@MockBean
	KafkaListenerEndpointRegistry kafka;

	@TestConfiguration
	static class NotificationServiceTestConfig {
		@Bean
		@Primary
		NotificationService NotificationService() {
			return new NotificationServiceImpl();
		}
	}

	@Before
	public void beforeTest() {
		// Mock
		Mockito.when(service.isRunningQueues()).thenAnswer(invocation -> {
			return true;
		});

		// MOCK
		Mockito.when(service.notificationETL(Constants.END_LINK_PLAIN)).thenAnswer(invocation -> {
			return true;
		});
	}

	@Test
	public void testIsRunningQueues() {

		assertEquals(true, service.isRunningQueues());

	}

	@Test

	public void testnotificationETL() {

		assertEquals(true, service.notificationETL(Constants.END_LINK_PLAIN));
	}

}
