package es.um.asio.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.um.asio.service.notification.service.NotificationService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@RestController
@RequestMapping(NotificationController.Mappings.BASE)
public class NotificationController {
	
	@Autowired
	NotificationService notificationService;

	 @GetMapping(value = "/{event}")
	 public void notificationETL(@PathVariable("event") final String event) {
		 notificationService.notificationETL(event);	        
	 }
	 
	
	/**
     * Mappings.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    static final class Mappings {
        /**
         * Controller request mapping.
         */
        protected static final String BASE = "/etl-notifications";

    }
}
