package es.um.asio.service.uris.impl;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import es.um.asio.service.uris.URISGeneratorClient;

@Service
@ConditionalOnProperty(prefix = "app.generator-uris.mockup", name = "enabled", havingValue = "false", matchIfMissing = true)
public class URISGeneratorClientImpl implements URISGeneratorClient {

	@Override
	public String createResourceID(Object input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createPropertyURI(Object input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createResourceTypeURI(String className) {
		// TODO Auto-generated method stub
		return null;
	}

}
