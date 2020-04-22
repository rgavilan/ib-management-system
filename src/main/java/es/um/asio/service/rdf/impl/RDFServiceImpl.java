package es.um.asio.service.rdf.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.um.asio.abstractions.domain.ManagementBusEvent;
import es.um.asio.service.model.GeneralBusEvent;
import es.um.asio.service.rdf.RDFDatasetBuilderService;
import es.um.asio.service.rdf.RDFService;

/**
 * The Class RDFServiceImpl.
 */
@Service
public class RDFServiceImpl implements RDFService {

	/** Logger. */
	private final Logger logger = LoggerFactory.getLogger(RDFServiceImpl.class);


	@Autowired
	private RDFDatasetBuilderService rdfDatasetBuilderService;


	/**
	 * Convert.
	 *
	 * @param input the input
	 * @return the model
	 */
	public  ManagementBusEvent createRDF(GeneralBusEvent<?> input) {
		logger.debug("Convert event bus: " + input);

		 ManagementBusEvent result = rdfDatasetBuilderService.inkoveBuilder(input);
		
		logger.debug("Generated RDF: ");
		logger.debug("modelId: " + result.getIdModel());
		logger.debug("operation: " + result.getOperation());
		logger.info(result.getModel());
				
		return result;
	}
}
