package es.um.asio.service.config;

import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import es.um.asio.domain.DataSetDataBase;

/**
 * Kafka related configuration
 */
@EnableKafka
@Configuration
public class KafkaConfig {
	/**
     * Kafka properties.
     */
    @Autowired
    private KafkaProperties kafkaProperties;

    /**
     * Input data consumer factory.
     * 
     * @return {@link ConsumerFactory}.
     */
    public ConsumerFactory<String, DataSetDataBase> inputDataConsumerFactory() {
    	
    	Map<String, Object> customerConfigMap = this.kafkaProperties.getConsumer().buildProperties();
    	customerConfigMap.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
    	customerConfigMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    	customerConfigMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
    	
    	return new DefaultKafkaConsumerFactory<>(customerConfigMap);
    }

    /**
     * Input kafka listener container factory.
     *
     * @return the concurrent kafka listener container factory
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, DataSetDataBase> inputKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, DataSetDataBase> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(this.inputDataConsumerFactory());
        return factory;
    }
}
