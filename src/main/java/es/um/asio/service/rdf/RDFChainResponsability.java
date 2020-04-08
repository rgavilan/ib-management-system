package es.um.asio.service.rdf;

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
	Model inkoveBuilder(BusEvent<?> input);
	
	/**
	 * Next builder.
	 *
	 * @param input the input
	 * @return the model
	 */
	Model nextBuilder(BusEvent<?> input);
	
	/**
	 * Creates the RDF.
	 *
	 * @param obj the obj
	 * @return the model
	 */
	Model createRDF(Object obj);
}
