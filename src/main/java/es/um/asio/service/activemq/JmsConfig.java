package es.um.asio.service.activemq;

import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@EnableJms
public class JmsConfig {

	@Value("${app.activemq.queue-name:default-queue-name}")
	private String queueName;
	
	@Bean
    public Queue queue(){
        return new ActiveMQQueue(queueName);
    }
}
