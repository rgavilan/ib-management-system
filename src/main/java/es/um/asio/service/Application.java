package es.um.asio.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import es.um.asio.service.config.ManagementSystemServiceConfig;

@SpringBootApplication
@EnableAutoConfiguration
@Import({ ManagementSystemServiceConfig.class })
@ComponentScan
public class Application {
    /**
     * Main method for embedded deployment.
     *
     * @param args
     *            the arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
