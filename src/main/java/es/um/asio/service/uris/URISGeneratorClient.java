package es.um.asio.service.uris;

import es.um.asio.abstractions.storage.StorageType;

// TODO: Auto-generated Javadoc
/**
 * The Interface URISGeneratorClient.
 */
public interface URISGeneratorClient {

	/**
	 * Creates the resource ID.
	 *
	 * @param input the input
	 * @return the string
	 */
	String createResourceID(Object input);
	
	
	/**
	 * Creates the property URI.
	 *
	 * @param input the input
	 * @param property the property
	 * @return the string
	 */
	String createPropertyURI(Object input, String property);
	
	/**
	 * Creates the resource type URI.
	 *
	 * @param className the class name
	 * @return the string
	 */
	String createResourceTypeURI(String className);
	
	
	/**
	 * Root uri.
	 *
	 * @return the string
	 */
	String rootUri();
	
	
	/**
	 * Gets the local storage uri.
	 *
	 * @param id the id
	 * @param className the class name
	 * @return the local storage uri
	 */
	String getLocalStorageUri(String id, String className);
	
}
