package es.um.asio.service.service.impl;

import org.apache.jena.rdf.model.Model;
import org.springframework.stereotype.Service;

import es.um.asio.service.model.BusEvent;
import es.um.asio.service.service.RDFPojoBuilderService;

/**
 * The Class RDFPojoBuilderServiceImpl.
 */
@Service
public class RDFPojoBuilderServiceImpl implements RDFPojoBuilderService {

	/**
	 * Creates the.
	 *
	 * @param input the input
	 * @return the model
	 */
	@Override
	public Model create(BusEvent<?> input) {
		// TODO implementation
		return null;
	}

	/**
	 * Next builder.
	 *
	 * @param input the input
	 * @return the model
	 */
	@Override
	public Model nextBuilder(BusEvent<?> input) {
		return null;
	}

}
