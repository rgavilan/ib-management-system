package es.um.asio.service.notification.service.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.stereotype.Service;

import es.um.asio.abstractions.constants.Constants;
import es.um.asio.service.notification.service.NotificationService;

/**
 * The Class NotificationServiceImpl.
 */
@Service
public class NotificationServiceImpl implements NotificationService {

	private final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

	@Autowired
	private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

	/**
	 * Notification ETL. Logic:
	 * If the event is END_LINK_PLAIN we start the general-pojo queue
	 * When the general-pojo queue is idle we stop general-pojo queue and we start general-link-data queue 
	 * Finally when the general-link-data is idle we stop the general-link-data queue
	 * @param event the event
	 * @return the boolean
	 */
	@Override
	public Boolean notificationETL(final String event) {

		this.logger.warn("NOTIFICATION event {}", event);

		// we need to run asynchronously this code to avoid blocking in the ETL
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.submit(() -> {
			if (Constants.END_LINK_PLAIN.equals(event)) {
				this.logger.warn("Starting Pojo General queue");
				startPojoGeneralListener();
			}
		});

		return true;
	}

	/**
	 * Start pojo general listener.
	 */
	public void startPojoGeneralListener() {
		final MessageListenerContainer listenerContainer = this.kafkaListenerEndpointRegistry
				.getListenerContainer(Constants.POJO_FACTORY);
		if (listenerContainer != null) {
			listenerContainer.start();
			listenerContainer.setAutoStartup(true);
		}
	}

	/**
	 * Stop pojo general listener.
	 */
	public void stopPojoGeneralListener() {
		final MessageListenerContainer listenerContainer = this.kafkaListenerEndpointRegistry
				.getListenerContainer(Constants.POJO_FACTORY);
		if (listenerContainer != null) {
			listenerContainer.stop();
			listenerContainer.setAutoStartup(false);
		}
	}

	/**
	 * Start pojo general link listener.
	 */
	public void startPojoGeneralLinkListener() {
		final MessageListenerContainer listenerContainer = this.kafkaListenerEndpointRegistry
				.getListenerContainer(Constants.POJO_LINK_FACTORY);
		if (listenerContainer != null) {
			listenerContainer.start();
		}
	}

	/**
	 * Stop pojo general link listener.
	 */
	public void stopPojoGeneralLinkListener() {
		final MessageListenerContainer listenerContainer = this.kafkaListenerEndpointRegistry
				.getListenerContainer(Constants.POJO_LINK_FACTORY);
		if (listenerContainer != null) {
			listenerContainer.stop();
		}
	}
	
    /**
     * Gets the status ETL.
     *
     * @return the status ETL
     */
    @Override
    public Boolean isRunningQueues() {
        MessageListenerContainer pojoContainer = kafkaListenerEndpointRegistry
                .getListenerContainer(Constants.POJO_FACTORY);
        boolean pojo = pojoContainer.isRunning();

        MessageListenerContainer pojolinkContainer = kafkaListenerEndpointRegistry
                .getListenerContainer(Constants.POJO_LINK_FACTORY);
        boolean pojolink = pojolinkContainer.isRunning();

        if (pojo || pojolink) {
            return true;
        } else {
            return false;
        }
    }

}
