package es.um.asio.service.kafka.impl;

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

import es.um.asio.abstractions.domain.ManagementBusEvent;
import es.um.asio.service.kafka.KafkaService;
import es.um.asio.service.repository.KafkaRepository;

@RunWith(SpringRunner.class)
@ActiveProfiles("unit-test")
public class KafkaServiceImplTest {
	
	@Autowired
	private KafkaService kafkaService;
	
	@MockBean
	private KafkaRepository kafkaRepository;
	
	@TestConfiguration
	static class KafkaServiceImplTestConfig {
		@Bean
		KafkaService kafkaService() {
			return new KafkaServiceImpl();
		}
	}
	
	
	@Test
	public void send() {
		ManagementBusEvent message = new ManagementBusEvent();
		kafkaService.send(message);
		verify(kafkaRepository, times(1)).send(message);
	}
	

}
