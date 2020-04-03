package es.um.asio.service.service.impl;

import java.lang.reflect.Field;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import es.um.asio.service.model.BusEvent;
import es.um.asio.service.service.RDFService;

/**
 * The Class RDFServiceImpl.
 */
@Service
public class RDFServiceImpl implements RDFService {

	/** Logger. */
    private final Logger logger = LoggerFactory.getLogger(RDFServiceImpl.class);
    
    /** The Constant uri. */
    public static final String uri ="http://www.w3.org/2001/asio-rdf/3.0#";
    
    
    /**
     * Gets the uri.
     *
     * @param entity the entity
     * @return the uri
     */
    private String getURI(String entity) {
    	return "http://example.org/" + entity;
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
    	Object obj = input.getData();
    	
    	// create the resource
    	Resource resource = model.createResource(this.getURI(obj.getClass().getSimpleName()));
    	
    	Field[] fields = obj.getClass().getDeclaredFields();
    	for(Field field: fields) {
    		try {
    			Property property = model.createProperty(uri,  field.getName() );
				resource.addProperty(property, BeanUtils.getSimpleProperty(obj, field.getName()));
			} catch (Exception e) {
				logger.error("Error creating resource from input: " + input);
			} 
    	}

    	return model;
    }
}
