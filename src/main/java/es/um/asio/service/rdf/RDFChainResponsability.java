package es.um.asio.service.rdf;

import org.apache.jena.rdf.model.Model;

import es.um.asio.abstractions.domain.ManagementBusEvent;
import es.um.asio.service.model.GeneralBusEvent;

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
	 ManagementBusEvent inkoveBuilder(GeneralBusEvent<?> input);
	
	/**
	 * Next builder.
	 *
	 * @param input the input
	 * @return the model
	 */
	 ManagementBusEvent nextBuilder(GeneralBusEvent<?> input);
	
	/**
	 * Creates the RDF.
	 *
	 * @param obj the obj
	 * @return the model
	 */
	Model createRDF(Object obj);
}
