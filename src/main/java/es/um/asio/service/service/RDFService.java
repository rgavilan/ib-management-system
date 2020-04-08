package es.um.asio.service.service;

import org.apache.jena.rdf.model.Model;

import es.um.asio.service.model.BusEvent;

/**
 * The Interface RDFService.
 */
public interface RDFService {

	/**
	 * Convert.
	 *
	 * @param input the input
	 * @return the model
	 */
	Model createRDF(BusEvent<?> input);
}
