package es.um.asio.service.listener;

import javax.jms.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.event.ListenerContainerIdleEvent;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.stereotype.Component;

import es.um.asio.abstractions.constants.Constants;
import es.um.asio.abstractions.domain.ManagementBusEvent;
import es.um.asio.domain.PojoData;
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

	/** The queue. */
	@Autowired
    private Queue queue;

    /** The jms template. */
    @Autowired
    private JmsTemplate jmsTemplate;

	@Autowired
	private RDFService rdfService;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
	
	private static Integer totalItems = 0;

	/**
	 * Method listening input topic name
	 * 
	 * @param message
	 */
	@KafkaListener(id = "pojoKafkaListenerContainerFactory", topics = "#{'${app.kafka.general-topic-name}'.split(',')}", autoStartup = "false", containerFactory = "pojoKafkaListenerContainerFactory", properties = {
			"spring.json.value.default.type:es.um.asio.domain.PojoData" })
	public void listen(final PojoData message) {
		// don't remove this line public void listen(ConsumerRecord<?, ?> cr)
		
		this.logger.error("Received message: {}", message);
	
		
		if (this.logger.isDebugEnabled()) {
			this.logger.debug("Received message: {}", message);
		}

		ManagementBusEvent managementBusEvent = rdfService.createRDF(new GeneralBusEvent<PojoData>(message));

		// we send the element to activeMQ
		jmsTemplate.convertAndSend(queue, managementBusEvent);
				
		totalItems++;	
	}

	@EventListener(condition = "event.listenerId.startsWith('pojoKafkaListenerContainerFactory-')")
	public void eventHandler(ListenerContainerIdleEvent event) {
		this.logger.warn("POJO-GENERAL No messages received for " + event.getIdleTime() + " milliseconds");		
		this.logger.warn("Total processed items: {}", totalItems);

		final MessageListenerContainer listenerPlainContainer = this.kafkaListenerEndpointRegistry
				.getListenerContainer(Constants.POJO_FACTORY);
		boolean isPlainRunning = listenerPlainContainer.isRunning();

		if (isPlainRunning && totalItems > 0) {
			this.notificationService.stopPojoGeneralListener();
			this.notificationService.startPojoGeneralLinkListener();
			totalItems = 0;
		}
	}
}
