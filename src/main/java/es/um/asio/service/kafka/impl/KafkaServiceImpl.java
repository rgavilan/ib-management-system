package es.um.asio.service.kafka.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.um.asio.abstractions.domain.ManagementBusEvent;
import es.um.asio.service.kafka.KafkaService;
import es.um.asio.service.repository.KafkaRepository;

@Service
public class KafkaServiceImpl implements KafkaService {
	
	 /**
     * Logger
     */
    private final Logger logger = LoggerFactory.getLogger(KafkaServiceImpl.class);
	
	@Autowired
	private KafkaRepository kafkaRepository;

	@Override
	public void send(ManagementBusEvent message) {
		this.logger.info("Sending message to kafka queue {}", message);
		this.kafkaRepository.send(message);		
	}
}
