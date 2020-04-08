package es.um.asio.service.rdf;

import org.apache.jena.rdf.model.Model;

import es.um.asio.service.model.GeneralBusEvent;
import es.um.asio.service.model.ManagementBusEvent;

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
	ManagementBusEvent<Model> createRDF(GeneralBusEvent<?> input);
}
