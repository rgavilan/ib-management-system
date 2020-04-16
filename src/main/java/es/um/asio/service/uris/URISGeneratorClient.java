package es.um.asio.service.uris;

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
	 * @return the string
	 */
	String createPropertyURI(Object input);
	
	/**
	 * Creates the resource type URI.
	 *
	 * @param className the class name
	 * @return the string
	 */
	String createResourceTypeURI(String className);
}
