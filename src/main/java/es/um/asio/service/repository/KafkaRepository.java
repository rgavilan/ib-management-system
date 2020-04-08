package es.um.asio.service.repository;

/**
 * Repository for messages.
 */
public interface KafkaRepository {
    
    void send(final String message);
}
