package es.um.asio.service.rdf.impl;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.util.PrintUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public Model createRDF(GeneralBusEvent<?> input) {
		logger.info("Convert event bus: " + input);

		Model model = rdfDatasetBuilderService.inkoveBuilder(input);
		
		logger.info("Generated RDF: ");
		model.write(System.out);
		
		return model;
	}
}
