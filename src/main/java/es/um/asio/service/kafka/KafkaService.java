package es.um.asio.service.kafka;

import org.apache.jena.rdf.model.Model;

import es.um.asio.service.model.ManagementBusEvent;

public interface KafkaService {
	 void send(ManagementBusEvent<Model> message);
}
