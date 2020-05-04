package es.um.asio.service.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import es.um.asio.abstractions.domain.ManagementBusEvent;
import es.um.asio.domain.PojoData;
import es.um.asio.service.model.GeneralBusEvent;
import es.um.asio.service.rdf.RDFService;

/**
 * General message listener for Pojo
 */
@Component
public class PojoGeneralListener {
	
	 /**
     * Logger
     */
    private final Logger logger = LoggerFactory.getLogger(PojoGeneralListener.class);
    
    @Autowired
    private RDFService rdfService;
    
    /**
     * Method listening input topic name
     * 
     * @param message
     */
    @KafkaListener(topics = "#{'${app.kafka.general-topic-name}'.split(',')}", containerFactory = "pojoKafkaListenerContainerFactory")
    public void listen(final PojoData message) {
    	 if (this.logger.isDebugEnabled()) {
             this.logger.debug("Received message: {}", message);
         }

    	 ManagementBusEvent rdf = rdfService.createRDF(new GeneralBusEvent<PojoData>(message));
                       
         this.logger.info(rdf.toString());
    }

}
