package es.um.asio.service.error;

import java.util.List;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.ContainerAwareErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.serializer.DeserializationException;

public class KafkaErrorHandler implements ContainerAwareErrorHandler  {

	
	private final Logger logger = LoggerFactory.getLogger(KafkaErrorHandler.class);
	
	
	 @Override
	    public void handle(Exception thrownException, List<ConsumerRecord<?, ?>> records, Consumer<?, ?> consumer, MessageListenerContainer container) {
	        // doSeeks(records, consumer);
	        if (!records.isEmpty()) {
	            ConsumerRecord<?, ?> record = records.get(0);
	            String topic = record.topic();
	            long offset = record.offset();
	            int partition = record.partition();
	            if (thrownException.getCause() instanceof DeserializationException) {
	                DeserializationException exception = (DeserializationException) thrownException.getCause();
	                String malformedMessage = new String(exception.getData());
	                logger.error("Skipping message with topic: {} and offset: {} " +
	                        "- malformed message: {} , exception: {}", topic, offset, malformedMessage, exception.getLocalizedMessage());
	            } else {
	                logger.error("Skipping message with topic {} - offset {} - partition {} - exception {}", topic, offset, partition, thrownException.toString());
	            }
	        } else {
	            logger.error("Consumer exception - cause: {}", thrownException.getMessage());	            
	        }
	    }
	}