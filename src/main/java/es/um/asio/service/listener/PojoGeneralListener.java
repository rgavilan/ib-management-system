package es.um.asio.service.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.event.ListenerContainerIdleEvent;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.stereotype.Component;

import es.um.asio.abstractions.constants.Constants;
import es.um.asio.abstractions.domain.ManagementBusEvent;
import es.um.asio.domain.PojoData;
import es.um.asio.service.kafka.KafkaService;
import es.um.asio.service.model.GeneralBusEvent;
import es.um.asio.service.notification.service.NotificationService;
import es.um.asio.service.rdf.RDFService;

/**
 * General message listener for Pojo
 */
@Profile("!unit-test")
@Component
public class PojoGeneralListener {

	/**
	 * Logger
	 */
	private final Logger logger = LoggerFactory.getLogger(PojoGeneralListener.class);

	/**
	 * Service to handle message entity related operations
	 */
	@Autowired
	private KafkaService kafkaService;

	@Autowired
	private RDFService rdfService;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

	/**
	 * Method listening input topic name
	 * 
	 * @param message
	 */
	@KafkaListener(id = "pojoKafkaListenerContainerFactory", topics = "#{'${app.kafka.general-topic-name}'.split(',')}", autoStartup = "false", containerFactory = "pojoKafkaListenerContainerFactory", properties = {
			"spring.json.value.default.type:es.um.asio.domain.PojoData" })
	public void listen(final PojoData message) {
		// don't remove this line public void listen(ConsumerRecord<?, ?> cr)
		
		if (this.logger.isDebugEnabled()) {
			this.logger.debug("Received message: {}", message);
		}

		ManagementBusEvent managementBusEvent = rdfService.createRDF(new GeneralBusEvent<PojoData>(message));

		this.kafkaService.send(managementBusEvent);

	}

	@EventListener
	public void eventHandler(ListenerContainerIdleEvent event) {
		this.logger.warn("No messages received for " + event.getIdleTime() + " milliseconds");

		final MessageListenerContainer listenerPlainContainer = this.kafkaListenerEndpointRegistry
				.getListenerContainer(Constants.POJO_FACTORY);
		final MessageListenerContainer listenerLinkContainer = this.kafkaListenerEndpointRegistry
				.getListenerContainer(Constants.POJO_LINK_FACTORY);
		boolean isPlainRunning = listenerPlainContainer.isRunning();
		boolean isLinkRunning = listenerLinkContainer.isRunning();

		if (isPlainRunning) {
			this.notificationService.stopPojoGeneralListener();
			this.notificationService.startPojoGeneralLinkListener();
		}

		if (isLinkRunning) {
			this.notificationService.stopPojoGeneralListener();
			this.notificationService.stopPojoGeneralLinkListener();
		}
	}
}
