package es.um.asio.service.kafka;

import es.um.asio.abstractions.domain.ManagementBusEvent;


public interface KafkaService {
	 void send(ManagementBusEvent message);
}
