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

import es.um.asio.abstractions.domain.ManagementBusEvent;
import es.um.asio.domain.InputData;
import es.um.asio.service.model.GeneralBusEvent;
import es.um.asio.service.rdf.RDFDatasetBuilderService;
import es.um.asio.service.rdf.RDFPojoBuilderService;
import es.um.asio.service.uris.URISGeneratorClient;
import es.um.asio.service.util.RDFUtil;

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

	@Autowired
	private URISGeneratorClient urisGeneratorClient;

	
	/**
	 * Creates the.
	 *
	 * @param input the input
	 * @return the model
	 */
	public ManagementBusEvent inkoveBuilder(GeneralBusEvent<?> input) {
		ManagementBusEvent result = null;
		if (!(input.getData() instanceof InputData)) {
			result = nextBuilder(input);
		}
		Model model = this.createRDF(input.retrieveInnerObj());
		result = new ManagementBusEvent(RDFUtil.toString(model), input.retrieveOperation());
		
		return result;
	}

	/**
	 * Next builder.
	 *
	 * @param input the input
	 * @return the model
	 */
	@Override
	public ManagementBusEvent nextBuilder(GeneralBusEvent<?> input) {
		return rdfPojoBuilderService.inkoveBuilder(input);
	}

		
	/**
	 * Creates the RDF.
	 *
	 * @param obj the obj
	 * @return the model
	 */
	public Model createRDF(Object obj) {
		Model model = ModelFactory.createDefaultModel();
		model.createProperty(urisGeneratorClient.rootUri());

		try {
			// create the resource
			Resource resourceProperties = model.createResource(urisGeneratorClient.createResourceID(obj));
			
			// only the own fields
			Field[] fields = obj.getClass().getDeclaredFields();
			
			for (Field field : fields) {
				Property property = model.createProperty(urisGeneratorClient.createPropertyURI(obj, field.getName()), field.getName());
				resourceProperties.addProperty(property, BeanUtils.getSimpleProperty(obj, field.getName()));
			}

			// we set the type
			Resource resourceClass = model.createResource(urisGeneratorClient.createResourceTypeURI(obj.getClass().getName()));
			model.add(resourceProperties, RDF.type, resourceClass);

		} catch (Exception e) {
			logger.error("Error creating resource from input: " + obj);
		}

		return model;
	}

	
}
