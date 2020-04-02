package es.um.asio.service.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import es.um.asio.domain.PojoData;
import es.um.asio.domain.pojo.Pojo;

/**
 * General message listener for Pojo
 */
@Component
public class PojoGeneralListener {
	
	 /**
     * Logger
     */
    private final Logger logger = LoggerFactory.getLogger(PojoGeneralListener.class);
    
    /**
     * Method listening input topic name
     * 
     * @param message
     */
    @KafkaListener(topics = "#{'${app.kafka.general-topic-name}'.split(',')}", containerFactory = "pojoKafkaListenerContainerFactory")
    public void listen(final PojoData<Pojo> message) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Received message: {}", message);
        }

        // Cuando el mensaje sea recibido es preciso procesarlo
        // this.messageService.save(message);
    }

}
