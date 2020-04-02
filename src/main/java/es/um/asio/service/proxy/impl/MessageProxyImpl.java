package es.um.asio.service.proxy.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.um.asio.service.proxy.MessageProxy;
import es.um.asio.service.service.MessageService;

/**
 * Proxy service implementation to handle message entity related operations
 */
@Service
public class MessageProxyImpl implements MessageProxy {

    /**
     * Service layer.
     */
    @Autowired
    private MessageService service;

    @Override
    public void save(final String message) {
        this.service.save(message);
    }

}
