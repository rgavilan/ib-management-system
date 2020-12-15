package es.um.asio.service.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import es.um.asio.service.notification.service.NotificationService;

@RunWith(SpringRunner.class)
@ActiveProfiles("unit-test")
public class NotificationControllerTest {
	
	@Autowired
	private NotificationController notificationController;
	
	@MockBean
	private NotificationService notificationService;
	
	private static final String EVENT = "event";
	
	
	@TestConfiguration
	static class NotificationControllerTestConfig {
		@Bean
		NotificationController notificationController() {
			return new NotificationController();
		}
	}


	@Test
	public void notificationETL() {
		notificationController.notificationETL(EVENT);
		verify(notificationService, times(1)).notificationETL(EVENT);
	}
	

	@Test
	public void getStatusETL() {
		notificationController.getStatusETL();
		verify(notificationService, times(1)).isRunningQueues();
	}
}
