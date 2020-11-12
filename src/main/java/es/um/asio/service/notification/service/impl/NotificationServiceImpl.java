package es.um.asio.service.notification.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.stereotype.Service;

import es.um.asio.abstractions.constants.Constants;
import es.um.asio.service.notification.service.NotificationService;



@Service
public class NotificationServiceImpl implements NotificationService {
	
	
    @Autowired
    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

	@Override
	public void notificationETL(String event) {
		
		if(Constants.START_PLAIN.equals(event)){
			startPojoGeneralListener();
		} else if (Constants.END_PLAIN.equals(event)) {
			stopLinkPojoGeneralListener();
		} else if(Constants.START_LINK_PLAIN.equals(event)) {
			startPojoGeneralLinkListener();
		} else {
			stopPojoGeneralLinkListener();
		}		
	}
	
	
    /**
     * Método para arrancar el listener general link 
     */
    public void startPojoGeneralLinkListener() {
        MessageListenerContainer listenerContainer = kafkaListenerEndpointRegistry.getListenerContainer(Constants.POJO_LINK_FACTORY);
        if( listenerContainer != null )
        	listenerContainer.start();        
    }

    /**
     * Método para detener el listener general link 
     */
    public void stopPojoGeneralLinkListener() {
        MessageListenerContainer listenerContainer = kafkaListenerEndpointRegistry.getListenerContainer(Constants.POJO_LINK_FACTORY);
        if( listenerContainer != null )
        	listenerContainer.stop();        
    }
    
    /**
     * Método para arrancar el listener general 
     */
    public void startPojoGeneralListener () {
    	MessageListenerContainer listenerContainer = kafkaListenerEndpointRegistry.getListenerContainer(Constants.POJO_FACTORY);
        if( listenerContainer != null )
        	listenerContainer.start();     
    }

    /**
     * Método para detener el listener general 
     */
    public void stopLinkPojoGeneralListener () {
        MessageListenerContainer listenerContainer = kafkaListenerEndpointRegistry.getListenerContainer(Constants.POJO_FACTORY);
        if( listenerContainer != null )
        	listenerContainer.stop();        
    }

    /**
     * Método para calcular el estado del management system. Ver si esta ocupado o no por la ETL
     * 
     * @return true (libre) / false (ocupado)
     */

	@Override
	public Boolean getStatusETL() {
		 MessageListenerContainer pojolinkContainer = kafkaListenerEndpointRegistry.getListenerContainer(Constants.POJO_LINK_FACTORY);
		 boolean pojolink = pojolinkContainer.isRunning();
		 MessageListenerContainer pojoContainer = kafkaListenerEndpointRegistry.getListenerContainer(Constants.POJO_FACTORY);
		 boolean pojo = pojoContainer.isRunning();
		 
		 if(!pojo && !pojolink) {
			 return true;
		 }else {
			 return false;
		 }
	}


}
