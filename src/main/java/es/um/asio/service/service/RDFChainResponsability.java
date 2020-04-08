package es.um.asio.service.service;

import org.apache.jena.rdf.model.Model;

import es.um.asio.service.model.BusEvent;

/**
 * The Interface RDFChainResponsability.
 */
public interface RDFChainResponsability {
	
	/**
	 * Creates the.
	 *
	 * @param input the input
	 * @return the model
	 */
	Model create(BusEvent<?> input);
	
	/**
	 * Next builder.
	 *
	 * @param input the input
	 * @return the model
	 */
	Model nextBuilder(BusEvent<?> input);
}
