package es.um.asio.service.uris.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import es.um.asio.abstractions.constants.Constants;
import es.um.asio.service.uris.URISGeneratorClientCache;

/**
 * The Class URISGeneratorClientCacheImpl.
 */
@Service
public class URISGeneratorClientCacheImpl implements URISGeneratorClientCache {

	private Map<String, String> cacheEntities;

	private Map<String, String> cacheProperties;
	
	
	 /**
 	 * Instantiates a new URIS generator client impl.
 	 */
 	public URISGeneratorClientCacheImpl() {
		this.cacheEntities = new HashMap<>();
		this.cacheProperties = new HashMap<>();
	}
 	
 	
 	/**
	  * Find.
	  *
	  * @param key the key
	  * @param cacheName the cache name
	  * @return the string
	  */
	 public String find(String key, String cacheName) {
 		return retrieveCache(cacheName).get(key);
 	}
 	
 	/**
	  * Save in cache.
	  *
	  * @param key the key
	  * @param value the value
	  * @param cacheName the cache name
	  */
	 public void saveInCache(String key, String value, String cacheName) {
 		retrieveCache(cacheName).put(key, value);
 	}
 	
 	/**
	  * Retrieve cache.
	  *
	  * @param cacheName the cache name
	  * @return the map
	  */
	 private Map<String, String> retrieveCache(String cacheName) {
 		switch (cacheName) {
		case Constants.CACHE_ENTITIES:
			return cacheEntities;
		case Constants.CACHE_PROPERTIES:
			return cacheProperties;
		default:
			return null;
		}
 	}
}
