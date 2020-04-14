package es.um.asio.service.repository;

import es.um.asio.abstractions.domain.ManagementBusEvent;



/**
 * Repository for messages.
 */
public interface KafkaRepository {
    
    void send(ManagementBusEvent message);
}
