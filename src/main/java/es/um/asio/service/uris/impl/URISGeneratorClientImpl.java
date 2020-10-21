package es.um.asio.service.uris.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import es.um.asio.abstractions.constants.Constants;
import es.um.asio.abstractions.storage.StorageType;
import es.um.asio.service.uris.URISGeneratorClient;
import es.um.asio.service.util.RDFUtil;

@Service
@ConditionalOnProperty(prefix = "app.generator-uris.mockup", name = "enabled", havingValue = "false", matchIfMissing = true)
public class URISGeneratorClientImpl implements URISGeneratorClient {
	
	@Value("${app.generator-uris.endpoint-root-uri}")
    private String rootURIEndpoint;
	
	@Value("${app.generator-uris.endpoint-resource-id}")
    private String resourceIdEndpoint;
	
	@Value("${app.generator-uris.endpoint-property}")
    private String propertyEndpoint;
	
	@Value("${app.generator-uris.endpoint-resource-type}")
    private String resourceTypeEndpoint;
	
	@Value("${app.generator-uris.endpoint-local-storage-uri}")
	private String localStorageUri;
	
	 /**
     * Rest Template
     */
    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

	/**
	 * Creates the resource ID.
	 *
	 * @param input the input
	 * @return the string
	 */
	@Override
	public String createResourceID(Object obj) {
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
	public String createPropertyURI(Object obj, String property) {
		HashMap input = new HashMap<>();
		input.put(Constants.OBJECT, obj);
		input.put(Constants.CLASS, obj.getClass().getName());
		input.put(Constants.PROPERTY, property);
				
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(propertyEndpoint)
		        .queryParam(Constants.DOMAIN, Constants.DOMAIN_VALUE)
		        .queryParam(Constants.LANG, Constants.SPANISH_LANGUAGE)
		        .queryParam(Constants.SUBDOMAIN, Constants.SUBDOMAIN_VALUE);
		
		Map response = restTemplate.postForObject(builder.toUriString(), input, Map.class);
		
		String result = response != null ? RDFUtil.getNameSpaceFromPath((String)response.get(Constants.CANONICAL_LANGUAGE_URI)): null; 
		
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
		return Constants.ROOT_URI; 
	}

	
	/**
	 * Gets the local storage uri.
	 *
	 * @param id the id
	 * @param className the class name
	 * @param language the language
	 * @param storage the storage
	 * @return the local storage uri
	 */
	@Override
	public String getLocalStorageUri(String id, String className) {
				
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(localStorageUri)
		        .queryParam(Constants.DOMAIN, Constants.DOMAIN_VALUE)
		        .queryParam(Constants.SUBDOMAIN, Constants.SUBDOMAIN_VALUE)
		        .queryParam(Constants.LANG, Constants.SPANISH_LANGUAGE)
		        .queryParam(Constants.TYPE_CODE, Constants.TYPE_REST)
		        .queryParam("entity", className)
		        .queryParam(Constants.REFERENCE, id)
		        .queryParam(Constants.STORAGE_NAME, StorageType.TRELLIS.name().toLowerCase());
		
		Map response = restTemplate.getForObject(builder.toUriString(), Map.class);
		String result = response != null ? (String)response.get(Constants.CANONICAL_LANGUAGE_URI): null; 
		
		return result;
	}

}
