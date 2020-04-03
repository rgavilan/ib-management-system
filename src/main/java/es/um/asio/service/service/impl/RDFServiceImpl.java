package es.um.asio.service.service.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.SKOS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.um.asio.service.model.BusEvent;
import es.um.asio.service.service.RDFService;
import es.um.asio.service.service.UrisMockService;

/**
 * The Class RDFServiceImpl.
 */
@Service
public class RDFServiceImpl implements RDFService {

	/** Logger. */
	private final Logger logger = LoggerFactory.getLogger(RDFServiceImpl.class);

	/** The Constant uri. */
	public static final String uri = "http://www.w3.org/2001/asio-rdf/3.0#";

	public static final String rootURI = "http://example.org";
	public static final String CHART_SET = java.nio.charset.StandardCharsets.UTF_8.toString();

	@Autowired
	private UrisMockService urisMockService;

	/**
	 * Gets the uri.
	 *
	 * @param entity the entity
	 * @return the uri
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	private String getIDResource(Object obj) throws Exception {

		StringBuilder result = new StringBuilder(rootURI);
		String valueField = StringUtils.EMPTY;
		
		for (Field field : obj.getClass().getDeclaredFields()) {
			result.append("/");
			valueField = BeanUtils.getSimpleProperty(obj, field.getName());
			result.append(URLEncoder.encode(valueField, CHART_SET));
		}

		return result.toString();
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
	 * Convert.
	 *
	 * @param input the input
	 * @return the model
	 */
	public Model convert(BusEvent<?> input) {
		logger.info("Convert event bus: " + input);

		Model model = ModelFactory.createDefaultModel();
		model.createProperty(rootURI);

		try {

			Object obj = input.retrieveInnerObj();

			// create the resource
			Resource resourceProperties = model.createResource(this.getIDResource(obj));

			Field[] fields = obj.getClass().getDeclaredFields();
			for (Field field : fields) {
				Property property = model.createProperty(this.getURI(field.getName()), field.getName());
				resourceProperties.addProperty(property, BeanUtils.getSimpleProperty(obj, field.getName()));

			}

			// we set the type
			Resource resourceClass = model.createResource(rootURI + "/" +obj.getClass().getSimpleName());
			model.add(resourceProperties, RDF.type, resourceClass);

		} catch (Exception e) {
			logger.error("Error creating resource from input: " + input);
		}

		return model;
	}
}
