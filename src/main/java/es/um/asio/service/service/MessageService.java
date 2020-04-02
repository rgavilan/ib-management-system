package es.um.asio.service.service;

/**
 * Service to handle message entity related operations
 */
public interface MessageService {
    /**
     * Save a message into the system
     *
     * @param message
     *            The message
     */
    void save(final String message);
}
