package es.um.asio.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

import es.um.asio.service.notification.service.NotificationService;
import es.um.asio.service.notification.service.impl.NotificationServiceImpl;

/**
 * Service Spring configuration.
 */
@Configuration
@ComponentScan
@Profile("!unit-test")
@Import({KafkaConfig.class})
public class ManagementSystemServiceConfig {

	
	@Bean
	public NotificationService notificationService() {
	    return new NotificationServiceImpl();
	}
}
