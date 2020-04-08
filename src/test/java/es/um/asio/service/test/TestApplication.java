package es.um.asio.service.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import es.um.asio.service.Application;
import es.um.asio.service.config.ManagementSystemServiceConfig;

@SpringBootApplication
@EnableAutoConfiguration
// @Import({ManagementSystemServiceConfig.class})
// @ComponentScan(value = {"es.um.asio.service","es.um.asio.service.proxy.impl"})
// @ComponentScan(basePackageClasses = Application.class)
@ComponentScan(value = {"es.um.asio.service"})
public class TestApplication {
    /**
     * Main method for embedded deployment.
     *
     * @param args
     *            the arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
