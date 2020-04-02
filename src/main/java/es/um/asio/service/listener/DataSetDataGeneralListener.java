package es.um.asio.service.listener;

import org.apache.jena.rdf.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import es.um.asio.domain.DataSetData;
import es.um.asio.domain.InputData;
import es.um.asio.service.model.BusEvent;
import es.um.asio.service.service.MessageService;
import es.um.asio.service.service.RDFService;

/**
 * General message listener for DataSetData
 */
@Component
public class DataSetDataGeneralListener {
    /**
     * Logger
     */
    private final Logger logger = LoggerFactory.getLogger(DataSetDataGeneralListener.class);

    /**
     * Service to handle message entity related operations
     */
    @Autowired
    private MessageService messageService;
    
    @Autowired
    private RDFService rdfService;

    /**
     * Method listening input topic name
     * 
     * @param message
     */
    @KafkaListener(topics = "#{'${app.kafka.general-topic-name}'.split(',')}", containerFactory = "dataSetDataKafkaListenerContainerFactory")
    public void listen(final InputData<DataSetData> message) {
    	
    	// INSERT operation by default
    	
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Received message: {}", message);
        }

        Model rdf = rdfService.convert(new BusEvent<InputData<DataSetData>>(message));
        
        rdf.write(System.out);
        
        this.logger.info(rdf.toString());
        // Cuando el mensaje sea recibido es preciso procesarlo
        // this.messageService.save(message);
    }
}