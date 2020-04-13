package es.um.asio.service.rdf.impl;

import java.lang.reflect.Field;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.um.asio.abstractions.constants.Constants;
import es.um.asio.abstractions.domain.ManagementBusEvent;
import es.um.asio.domain.InputData;
import es.um.asio.service.model.GeneralBusEvent;
import es.um.asio.service.rdf.RDFDatasetBuilderService;
import es.um.asio.service.rdf.RDFGeneratorIDService;
import es.um.asio.service.rdf.RDFPojoBuilderService;

/**
 * The Class RDFDatasetBuilderServiceImpl.
 */
@Service
public class RDFDatasetBuilderServiceImpl  implements RDFDatasetBuilderService {
	/** Logger. */
	private final Logger logger = LoggerFactory.getLogger(RDFDatasetBuilderServiceImpl.class);

	/** The rdf pojo builder service. */
	@Autowired
	private RDFPojoBuilderService rdfPojoBuilderService;

	/** The Constant uri. */
	public static final String uri = "http://www.w3.org/2001/asio-rdf/3.0#";

	// @Autowired
	// private UrisGeneratorClient urisGeneratorClient;

	/** The generator ID service. */
	@Autowired
	private RDFGeneratorIDService generatorIDService;


	/**
	 * Creates the.
	 *
	 * @param input the input
	 * @return the model
	 */
	public ManagementBusEvent<Model> inkoveBuilder(GeneralBusEvent<?> input) {
		ManagementBusEvent<Model> result = null;
		if (!(input.getData() instanceof InputData)) {
			result = nextBuilder(input);
		}
		Model model = this.createRDF(input.retrieveInnerObj());
		result = new ManagementBusEvent<Model>(model, input.retrieveOperation());
		
		return result;
	}

	/**
	 * Next builder.
	 *
	 * @param input the input
	 * @return the model
	 */
	@Override
	public ManagementBusEvent<Model> nextBuilder(GeneralBusEvent<?> input) {
		return rdfPojoBuilderService.inkoveBuilder(input);
	}

	
	/**
	 * Gets the uri.
	 *
	 * @param property the property
	 * @return the uri
	 */
	private String getURI(String property) {
		// return this.urisMockService.getUri(property);
		return uri;
	}
	
	/**
	 * Creates the RDF.
	 *
	 * @param obj the obj
	 * @return the model
	 */
	public Model createRDF(Object obj) {
		Model model = ModelFactory.createDefaultModel();
		model.createProperty(Constants.ROOT_URI);

		try {
			// create the resource
			Resource resourceProperties = model.createResource(generatorIDService.generateResourceID(obj));
			
			// only the own fields
			Field[] fields = obj.getClass().getDeclaredFields();
			
			for (Field field : fields) {
				Property property = model.createProperty(this.getURI(field.getName()), field.getName());
				resourceProperties.addProperty(property, BeanUtils.getSimpleProperty(obj, field.getName()));
			}

			// we set the type
			Resource resourceClass = model.createResource(Constants.ROOT_URI + "/" + obj.getClass().getSimpleName());
			model.add(resourceProperties, RDF.type, resourceClass);

		} catch (Exception e) {
			logger.error("Error creating resource from input: " + obj);
		}

		return model;
	}

	
}
