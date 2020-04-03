package es.um.asio.service.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Service Spring configuration.
 */
@Configuration
@ComponentScan
@Import({KafkaConfig.class})
public class ManagementSystemServiceConfig {

}
