package es.um.asio.service.uris.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import es.um.asio.service.uris.URISGeneratorClient;

@Service
@ConditionalOnProperty(prefix = "app.generator-uris.mockup", name = "enabled", havingValue = "false", matchIfMissing = true)
public class URISGeneratorClientImpl implements URISGeneratorClient {
	
	@Value("${app.generator-uris.endpoint-resource-id}")
    private String resourceIdEndpoint;
	
	@Value("${app.generator-uris.property}")
    private String propertyEndpoint;
	
	@Value("${app.generator-uris.resource-type}")
    private String resourceTypeEndpoint;
	
	 /**
     * Rest Template
     */
    @Autowired
    private RestTemplate restTemplate;


	/**
	 * Creates the resource ID.
	 *
	 * @param input the input
	 * @return the string
	 */
	@Override
	public String createResourceID(Object input) {
		// FIXME remove it
		if("${app.generator-uris.endpoint-resource-id}".equals(resourceIdEndpoint)) {
			resourceIdEndpoint = "http://localhost:8080/uri/resource-id";
		}
		String result = restTemplate.postForObject(resourceIdEndpoint, input, String.class);
		return result;
	}

	/**
	 * Creates the property URI.
	 *
	 * @param input the input
	 * @return the string
	 */
	@Override
	public String createPropertyURI(Object input) {
		// FIXME remove it
		if("${app.generator-uris.property}".equals(propertyEndpoint)) {
			propertyEndpoint = "http://localhost:8080/uri/property";
		}
		String result = restTemplate.postForObject(propertyEndpoint, input, String.class);
		return result;
	}

	/**
	 * Creates the resource type URI.
	 *
	 * @param className the class name
	 * @return the string
	 */
	@Override
	public String createResourceTypeURI(String className) {
		// FIXME remove it
		if("${app.generator-uris.resource-type}".equals(resourceTypeEndpoint)) {
			resourceTypeEndpoint = "http://localhost:8080/uri/resource-type";
		}
		String result = restTemplate.postForObject(resourceTypeEndpoint, className, String.class);
		return result;
	}

}
