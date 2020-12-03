package es.um.asio.service.test.notification;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import es.um.asio.abstractions.constants.Constants;
import es.um.asio.service.notification.service.NotificationService;
import es.um.asio.service.notification.service.impl.NotificationServiceImpl;

@RunWith(SpringRunner.class)
@ActiveProfiles("unit-test")
public class NotificationControllerTest {

	@MockBean
	private NotificationService notificationService;

	@TestConfiguration
	static class NotificationControllerConfiguration {
		@Bean
		public NotificationService NotificationService() {
			return new NotificationServiceImpl();
		}
	}

	@Before
	public void testUp() {
		Mockito.when(notificationService.notificationETL(Constants.START_PLAIN)).thenAnswer(nvocation -> {
			return true;
		});

		// Mock
		Mockito.when(notificationService.isRunningQueues()).thenAnswer(invocation -> {
			return true;
		});
	}

	@Test
	public void nofificationETL() {

		boolean result = notificationService.notificationETL(Constants.START_PLAIN);

		assertEquals(true, result);

	}

	@Test
	public void isRunningQueues() {

		boolean result = notificationService.isRunningQueues();

		assertEquals(true, result);

	}

}
