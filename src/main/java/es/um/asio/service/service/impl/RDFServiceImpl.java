package es.um.asio.service.service.impl;

import org.apache.jena.rdf.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.um.asio.service.model.BusEvent;
import es.um.asio.service.service.RDFDatasetBuilderService;
import es.um.asio.service.service.RDFService;

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
	public Model createRDF(BusEvent<?> input) {
		logger.info("Convert event bus: " + input);

		Model model = rdfDatasetBuilderService.create(input);
		// TODO print MODEL
		return model;
	}
}
