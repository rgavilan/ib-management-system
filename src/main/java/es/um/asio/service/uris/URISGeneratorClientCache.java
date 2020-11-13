package es.um.asio.service.uris;

public interface URISGeneratorClientCache {

	String find(String key, String cacheName);

	/**
	 * Save in cache.
	 *
	 * @param key       the key
	 * @param value     the value
	 * @param cacheName the cache name
	 */
	void saveInCache(String key, String value, String cacheName);

}
