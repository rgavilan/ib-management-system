package es.um.asio.service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Kafka admin related configuration
 */
@ConditionalOnProperty(prefix = "app.kafka", name = "create-topics", havingValue = "true", matchIfMissing = false)
@Configuration
public class KafkaAdminConfig {
    /**
     * General topic name.
     */
    @Value("${app.kafka.general-topic-name}")
    private String generalTopicName;

    /**
     * Input topic name.
     */
    @Value("${app.kafka.management-topic-name}")
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
