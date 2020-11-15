package es.um.asio.service.notification.service.impl;

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
	public void notificationETL(String event) {
		/*
		 * if(Constants.START_PLAIN.equals(event)){ startPojoGeneralListener(); } else
		 * if (Constants.END_PLAIN.equals(event)) { stopLinkPojoGeneralListener(); }
		 * else if(Constants.START_LINK_PLAIN.equals(event)) {
		 * startPojoGeneralLinkListener(); } else { stopPojoGeneralLinkListener(); }
		 */

		this.logger.error("NOTIFICATION event {}", event);

		/*
		if (Constants.END_LINK_PLAIN.equals(event)) {
			this.logger.error("starting Pojo General queue");
			startPojoGeneralListener();
			this.logger.error("processing Pojo General queue");
			while (this.isRunningQueues())
				;
			this.logger.error("stopping Pojo General queue");
			stopPojoGeneralListener();
			this.logger.error("starting Pojo General Link queue");
			startPojoGeneralLinkListener();
			this.logger.error("processing Pojo General Link queue");
			while (this.isRunningQueues())
				;
			this.logger.error("stopping Pojo General Link queue");
			stopPojoGeneralLinkListener();
		}
		*/
	}

	/**
	 * Start pojo general listener.
	 */
	public void startPojoGeneralListener() {
		MessageListenerContainer listenerContainer = kafkaListenerEndpointRegistry
				.getListenerContainer(Constants.POJO_FACTORY);
		if (listenerContainer != null)
			listenerContainer.start();
	}

	/**
	 * Stop pojo general listener.
	 */
	public void stopPojoGeneralListener() {
		MessageListenerContainer listenerContainer = kafkaListenerEndpointRegistry
				.getListenerContainer(Constants.POJO_FACTORY);
		if (listenerContainer != null)
			listenerContainer.stop();
	}

	/**
	 * Start pojo general link listener.
	 */
	public void startPojoGeneralLinkListener() {
		MessageListenerContainer listenerContainer = kafkaListenerEndpointRegistry
				.getListenerContainer(Constants.POJO_LINK_FACTORY);
		if (listenerContainer != null)
			listenerContainer.start();
	}

	/**
	 * Stop pojo general link listener.
	 */
	public void stopPojoGeneralLinkListener() {
		MessageListenerContainer listenerContainer = kafkaListenerEndpointRegistry
				.getListenerContainer(Constants.POJO_LINK_FACTORY);
		if (listenerContainer != null)
			listenerContainer.stop();
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
