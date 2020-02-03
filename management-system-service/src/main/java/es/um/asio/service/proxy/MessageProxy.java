package es.um.asio.service.proxy;

/**
 * Proxy service to handle message entity related operations
 */
public interface MessageProxy {
    /**
     * Save a message into the system
     *
     * @param message
     *            The message
     */
    void save(final String message);
}
