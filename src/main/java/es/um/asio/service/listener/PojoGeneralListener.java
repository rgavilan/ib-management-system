package es.um.asio.service.listener;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.event.ListenerContainerIdleEvent;
import org.springframework.stereotype.Component;

import es.um.asio.abstractions.constants.Constants;
import es.um.asio.domain.PojoData;
import es.um.asio.service.kafka.KafkaService;
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

	/**
	 * Method listening input topic name
	 * 
	 * @param message
	 */
	@KafkaListener(id = "pojoKafkaListenerContainerFactory", topics = "#{'${app.kafka.general-topic-name}'.split(',')}", autoStartup = "false", containerFactory = "pojoKafkaListenerContainerFactory", properties = {
			"spring.json.value.default.type:es.um.asio.domain.PojoData" })
	public void listen(final PojoData message) {
		/*
		 * // public void listen(ConsumerRecord<?, ?> cr) { if
		 * (this.logger.isDebugEnabled()) { this.logger.debug("Received message: {}",
		 * message); }
		 * 
		 * ManagementBusEvent managementBusEvent = rdfService.createRDF(new
		 * GeneralBusEvent<PojoData>(message));
		 * 
		 * this.kafkaService.send(managementBusEvent);
		 */

		this.logger.warn("Pojo General item {}", message.getData());

	}

	@EventListener
	public void eventHandler(ListenerContainerIdleEvent event) {
		this.logger.warn("No messages received for " + event.getIdleTime() + " milliseconds");

		this.notificationService.stopPojoGeneralListener();
		
		this.notificationService.startPojoGeneralLinkListener();

	}

}
