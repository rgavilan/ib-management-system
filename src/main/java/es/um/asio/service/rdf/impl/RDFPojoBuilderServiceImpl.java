package es.um.asio.service.rdf.impl;

import java.util.LinkedHashMap;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
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
import es.um.asio.domain.PojoData;
import es.um.asio.service.model.GeneralBusEvent;
import es.um.asio.service.model.ModelWrapper;
import es.um.asio.service.rdf.RDFPojoBuilderService;
import es.um.asio.service.uris.URISGeneratorClient;
import es.um.asio.service.util.RDFUtil;

/**
 * The Class RDFPojoBuilderServiceImpl.
 */
@Service
public class RDFPojoBuilderServiceImpl implements RDFPojoBuilderService {

	/** The Constant ETL_POJO_CLASS. */
	private static final String ETL_POJO_CLASS = "clase";

	/** The Constant ETL_POJO_ID. */
	private static final String ETL_POJO_ID = "id";

	/** The Constant HTTP_HERCULES_ORG_UM_ES_ES_REC. */
	private static final String HTTP_HERCULES_ORG_UM_ES_ES_REC = "http://hercules.org/um/es-ES/rec/";

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(RDFPojoBuilderServiceImpl.class);

	/** The uris generator client. */
	@Autowired
	private URISGeneratorClient urisGeneratorClient;

	/**
	 * Creates the.
	 *
	 * @param input the input
	 * @return the model
	 */
	@Override
	public ManagementBusEvent inkoveBuilder(final GeneralBusEvent<?> input) {
		ManagementBusEvent result = null;
		if (input.getData() instanceof PojoData) {
			final ModelWrapper model = this.createRDF(input.retrieveInnerObj());
			
			result = new ManagementBusEvent(model.getModelId(), RDFUtil.toString(model.getModel()),
					this.getClass(input.retrieveInnerObj()), input.retrieveOperation());
		} else {
			result = this.nextBuilder(input);
		}

		return result;
	}

	/**
	 * Next builder.
	 *
	 * @param input the input
	 * @return the model
	 */
	@Override
	public ManagementBusEvent nextBuilder(final GeneralBusEvent<?> input) {
		return null;
	}

	/**
	 * Creates the RDF.
	 *
	 * @param obj the obj
	 * @return the model wrapper
	 */
	@Override
	public ModelWrapper createRDF(final Object obj) {
		final ModelWrapper result = new ModelWrapper();

		final Model model = ModelFactory.createDefaultModel();
		model.createProperty(HTTP_HERCULES_ORG_UM_ES_ES_REC);

		try {
			// 1. create the resource
			final String className = (String) PropertyUtils.getProperty(obj, RDFPojoBuilderServiceImpl.ETL_POJO_CLASS);
			final String objectId = this.sanifyCheck(PropertyUtils.getProperty(obj, RDFPojoBuilderServiceImpl.ETL_POJO_ID));
			
			if (StringUtils.isBlank(objectId)) {
				throw new Exception("Pojo without identity");
			}
			
			final String modelId = RDFPojoBuilderServiceImpl.HTTP_HERCULES_ORG_UM_ES_ES_REC + className + "/" + objectId;
			final Resource resourceProperties = model.createResource(modelId);

			// 2. create the properties
			String propertyValue;
			String pojoNodeID;

			final LinkedHashMap inputPojo = ((LinkedHashMap) obj);
			final Set<String> keys = inputPojo.keySet();
			for (final String key : keys) {
				// we skip the clase field
				if (!RDFPojoBuilderServiceImpl.ETL_POJO_CLASS.equalsIgnoreCase(key)) {
					final Property property = model.createProperty(RDFPojoBuilderServiceImpl.HTTP_HERCULES_ORG_UM_ES_ES_REC + className + "/", key);

					final Object pojoNode = inputPojo.get(key);
					if (pojoNode instanceof LinkedHashMap) {
						// nested property
						pojoNodeID = ((LinkedHashMap) inputPojo.get(key)).get(RDFPojoBuilderServiceImpl.ETL_POJO_ID).toString();
						if(StringUtils.isNotBlank(pojoNodeID)) {
							resourceProperties.addProperty(property, RDFPojoBuilderServiceImpl.HTTP_HERCULES_ORG_UM_ES_ES_REC + StringUtils.capitalize(key) + "/" + pojoNodeID);
						} else {
							this.logger.error("Nested object with null id: " + pojoNode);
						}
					} else {
						// simple property
						propertyValue = inputPojo.get(key).toString();
						resourceProperties.addProperty(property, propertyValue);
					}
				}
			}

			// 3. we set the type
			final Resource resourceClass = model.createResource(RDFPojoBuilderServiceImpl.HTTP_HERCULES_ORG_UM_ES_ES_REC + className + "/");
			model.add(resourceProperties, RDF.type, resourceClass);

			// 4. we build the result model
			result.setModelId(modelId);
			result.setModel(model);

		} catch (final Exception e) {
			this.logger.error("Error creating resource from input: " + obj);
			this.logger.error("Error cause " + e.getMessage());
			e.printStackTrace();
		}

		return result;
	}
	
	/**
	 * Gets the class.
	 *
	 * @param obj the obj
	 * @return the class
	 */
	private String getClass(Object obj) {
		String result = StringUtils.EMPTY;
		try {
			result = (String) PropertyUtils.getProperty(obj, RDFPojoBuilderServiceImpl.ETL_POJO_CLASS);
		} catch (Exception e) {
			logger.error("Unknown class in object " + obj.toString());
		} 
		return result;
	}
	
	/**
	 * Sanify check.
	 *
	 * @param obj the obj
	 * @return the string
	 */
	private String sanifyCheck(Object obj) {
		String result = StringUtils.EMPTY;
		if( obj == null) {
			return result;
		}
		if (obj instanceof Number) {
			return ((Number) obj).toString();
		}
		if(obj instanceof String) {
			return (String) obj;
		}
		
		return result;
	}

}
