package es.um.asio.service.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

/**
 * Service Spring configuration.
 */
@Configuration
@ComponentScan
@Profile("!unit-test")
@Import({KafkaConfig.class})
public class ManagementSystemServiceConfig {

}
