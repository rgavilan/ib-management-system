package es.um.asio.service.rdf.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.um.asio.abstractions.domain.ManagementBusEvent;
import es.um.asio.service.model.GeneralBusEvent;
import es.um.asio.service.rdf.RDFCvnBuilderService;
import es.um.asio.service.rdf.RDFService;

/**
 * The Class RDFServiceImpl.
 */
@Service
public class RDFServiceImpl implements RDFService {

	/** Logger. */
	private final Logger logger = LoggerFactory.getLogger(RDFServiceImpl.class);


	/** 
	 * The rdf cvn builder service.
	 */
	@Autowired
	private RDFCvnBuilderService rdfCvnBuilderService;


	/**
	 * Convert.
	 *
	 * @param input the input
	 * @return the model
	 */
	public  ManagementBusEvent createRDF(GeneralBusEvent<?> input) {
        logger.debug("Convert event bus: " + input);

		ManagementBusEvent result = rdfCvnBuilderService.inkoveBuilder(input);
		logger.info("Generated RDF: ");
		logger.info("modelId: " + result.getIdModel());
		logger.info("operation: " + result.getOperation());
		
		logger.info("GRAYLOG-MS Procesado RDF de tipo: " + result.getClassName());
				
		return result;
	}
}
