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

@Service
public class NotificationServiceImpl implements NotificationService {

	private final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

	@Autowired
	private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

	@Override
	public Boolean notificationETL(final String event) {

		this.logger.error("NOTIFICATION event {}", event);

		ExecutorService executor = Executors.newSingleThreadExecutor();

		executor.submit(() -> {
			if (Constants.END_LINK_PLAIN.equals(event)) {
				this.logger.warn("starting Pojo General queue");
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

}
