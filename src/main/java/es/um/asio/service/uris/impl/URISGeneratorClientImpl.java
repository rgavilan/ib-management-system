package es.um.asio.service.uris.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import es.um.asio.abstractions.constants.Constants;
import es.um.asio.service.uris.URISGeneratorClient;

@Service
@ConditionalOnProperty(prefix = "app.generator-uris.mockup", name = "enabled", havingValue = "false", matchIfMissing = true)
public class URISGeneratorClientImpl implements URISGeneratorClient {
	
	@Value("${app.generator-uris.endpoint-root-uri}")
    private String rootURIEndpoint;
	
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
	public String createResourceID(Object obj) {
		// FIXME remove it
		if("${app.generator-uris.endpoint-resource-id}".equals(resourceIdEndpoint)) {
			resourceIdEndpoint = "http://localhost:8080/uri-factory/canonical/resource";
		}
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(resourceIdEndpoint)
		        .queryParam(Constants.DOMAIN, Constants.DOMAIN_VALUE)
		        .queryParam(Constants.LANG, Constants.SPANISH_LANGUAGE)
		        .queryParam(Constants.SUBDOMAIN, Constants.SUBDOMAIN_VALUE);
		
		Map response = restTemplate.postForObject(builder.toUriString(), obj, Map.class);
		
		String result = response != null ? (String)response.get(Constants.CANONICAL_LANGUAGE_URI): null; 
		
		return result;
	}

	/**
	 * Creates the property URI.
	 *
	 * @param input the input
	 * @return the stringc
	 */
	@Override
	public String createPropertyURI(Object obj, String property, String resourceID) {
		// FIXME remove it
		if("${app.generator-uris.property}".equals(propertyEndpoint)) {
			propertyEndpoint = "http://localhost:8080/uri-factory/canonical/property";
		}
		
		HashMap input = new HashMap<>();
		input.put(Constants.OBJECT, obj);
		input.put(Constants.CLASS, obj.getClass().getName());
		input.put(Constants.PROPERTY, property);
		input.put(Constants.RESOURCE_ID, resourceID);
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(propertyEndpoint)
		        .queryParam(Constants.DOMAIN, Constants.DOMAIN_VALUE)
		        .queryParam(Constants.LANG, Constants.SPANISH_LANGUAGE)
		        .queryParam(Constants.SUBDOMAIN, Constants.SUBDOMAIN_VALUE);
		
		Map response = restTemplate.postForObject(builder.toUriString(), input, Map.class);
		
		String result = response != null ? (String)response.get(Constants.CANONICAL_LANGUAGE_URI): null; 
		
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
			resourceTypeEndpoint = "http://localhost:8080/uri-factory/canonical/entity";
		}
		
		HashMap input = new HashMap<>();
		input.put(Constants.CLASS, className);
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(resourceTypeEndpoint)
		        .queryParam(Constants.DOMAIN, Constants.DOMAIN_VALUE)
		        .queryParam(Constants.LANG, Constants.SPANISH_LANGUAGE)
		        .queryParam(Constants.SUBDOMAIN, Constants.SUBDOMAIN_VALUE);
		
		Map response = restTemplate.postForObject(builder.toUriString(), input, Map.class);
		
		String result = response != null ? (String)response.get(Constants.CANONICAL_LANGUAGE_URI): null; 
		
		return result;
	}

	@Override
	public String rootUri() {
		// FIXME remove it
		if("${app.generator-uris.endpoint-root-uri}".equals(rootURIEndpoint)) {
			rootURIEndpoint = "http://localhost:8080/uri-factory/root/uri";
		}
				
		return restTemplate.getForObject(rootURIEndpoint, String.class);
	}

}
