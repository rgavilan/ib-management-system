package es.um.asio.service.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import es.um.asio.abstractions.domain.ManagementBusEvent;
import es.um.asio.domain.PojoLinkData;
import es.um.asio.service.kafka.KafkaService;
import es.um.asio.service.model.GeneralBusEvent;
import es.um.asio.service.rdf.RDFService;

/**
 * General message listener for Pojo
 */
@Profile("!unit-test")
@Component
public class PojoGeneralLinkListener {
	
	 /**
     * Logger
     */
    private final Logger logger = LoggerFactory.getLogger(PojoGeneralLinkListener.class);
    
    /**
     * Service to handle message entity related operations
     */
    @Autowired
    private KafkaService kafkaService;
    
    @Autowired
    private RDFService rdfService;
    
    /**
     * Method listening input topic name
     * 
     * @param message
     */
    @KafkaListener(id="pojoLinkKafkaListenerContainerFactory",topics = "#{'${app.kafka.general-link-topic-name}'.split(',')}",autoStartup = "false", containerFactory = "pojoLinkKafkaListenerContainerFactory", properties = {"spring.json.value.default.type:es.um.asio.domain.PojoLinkData"})
    public void listen(final PojoLinkData message) {
    	/*
    	 if (this.logger.isDebugEnabled()) {
             this.logger.debug("Received message: {}", message);
         }

    	 ManagementBusEvent managementBusEvent = rdfService.createRDF(new GeneralBusEvent<PojoLinkData>(message));
                       
    	 this.kafkaService.send(managementBusEvent);
    	 */
    	this.logger.warn("Pojo General Link item {}", message.getData());
    }

}
