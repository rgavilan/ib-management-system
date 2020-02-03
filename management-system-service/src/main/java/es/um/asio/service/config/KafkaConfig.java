package es.um.asio.service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * Kafka related configuration
 */
@EnableKafka
@Configuration
public class KafkaConfig {
    /**
     * General topic name.
     */
    @Value("app.kafka.general-topic-name")
    private String generalTopicName;

    /**
     * Input topic name.
     */
    @Value("app.kafka.management-topic-name")
    private String managementTopicName;

    /**
     * General topic.
     * 
     * @return
     */
    @Bean
    public NewTopic generalTopic() {
        return new NewTopic(this.generalTopicName, 1, (short) 1);
    }

    /**
     * Input topic.
     * 
     * @return
     */
    @Bean
    public NewTopic managementTopic() {
        return new NewTopic(this.managementTopicName, 1, (short) 1);
    }
}
