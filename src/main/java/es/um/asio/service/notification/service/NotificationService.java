package es.um.asio.service.notification.service;

public interface NotificationService {

	Boolean notificationETL(String event);

	void stopPojoGeneralListener();
	
	void startPojoGeneralLinkListener();
	
	void stopPojoGeneralLinkListener();
	
    Boolean isRunningQueues();
}
