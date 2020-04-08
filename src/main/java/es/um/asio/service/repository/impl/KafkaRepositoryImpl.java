package es.um.asio.service.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import es.um.asio.service.repository.KafkaRepository;

/**
 * Kafka repository implementation for messages.
 */
@Component
public class KafkaRepositoryImpl implements KafkaRepository {
    
    /**
     * Kafka template.
     */
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    
    /**
     * Topic name
     */
    @Value("${app.kafka.management-topic-name}")
    private String managementTopicName;

    /**
     * {@inheritDoc}
     */
    @Override
    public void send(String message) {
        kafkaTemplate.send(managementTopicName, message);
    }

}
