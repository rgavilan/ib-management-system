package es.um.asio.service.notification.service;

public interface NotificationService {

	public void notificationETL(String event);

	public Boolean isRunningQueues();
	
}
