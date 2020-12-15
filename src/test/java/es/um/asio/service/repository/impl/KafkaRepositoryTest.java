package es.um.asio.service.repository.impl;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import es.um.asio.abstractions.domain.ManagementBusEvent;
import es.um.asio.service.repository.KafkaRepository;



@RunWith(SpringRunner.class)
@ActiveProfiles("unit-test")
public class KafkaRepositoryTest {
	
	@Autowired
	private KafkaRepository kafkaRepository;
	
	@MockBean
    private KafkaTemplate<String, ManagementBusEvent> kafkaTemplate;
	
	private static final String MANAGEMENT_TOPIC_NAME = "management-data";
	
	@TestConfiguration
	static class KafkaRepositoryImplTestConfig {
		@Bean
		KafkaRepository kafkaRepository() {
			return new KafkaRepositoryImpl();
		}
	}
	
	@Before
    public void setUp() {
        ReflectionTestUtils.setField(kafkaRepository, "managementTopicName", MANAGEMENT_TOPIC_NAME);
    }
	
	@Test
	public void send() {
		ManagementBusEvent message = new ManagementBusEvent();
		kafkaRepository.send(message);
		verify(kafkaTemplate, times(1)).send(MANAGEMENT_TOPIC_NAME, message);
	}

}
