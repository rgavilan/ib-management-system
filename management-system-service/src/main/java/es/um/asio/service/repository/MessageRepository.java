package es.um.asio.service.repository;

/**
 * Repository for messages.
 */
public interface MessageRepository {
    /**
     * Save a message into the system
     *
     * @param message
     *            The message
     */
    void save(final String message);
}
