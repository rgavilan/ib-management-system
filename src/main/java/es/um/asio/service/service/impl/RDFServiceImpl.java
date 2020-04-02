package es.um.asio.service.service.impl;

import java.lang.reflect.Field;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.VCARD;
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

	/**
     * Logger
     */
    private final Logger logger = LoggerFactory.getLogger(RDFServiceImpl.class);
    
    /**
     * Convert.
     *
     * @param input the input
     * @return the model
     */
    public Model convert(BusEvent input) {
    	logger.info("Convert event bus: " + input);
    	
    	Model model = ModelFactory.createDefaultModel();
    	Object obj = input.retrieveObject();
    	
    	// create the resource
    	Resource resource = model.createResource(this.getURI(obj));
    	
    	Field[] fields = obj.getClass().getDeclaredFields();
    	for(Field field: fields) {
    		try {
				resource.addProperty(VCARD.N, BeanUtils.getSimpleProperty(obj, field.getName()));
			} catch (Exception e) {
				logger.error("Error creating resource from input: " + input);
			} 
    	}

    	return model;
    }
    
    /**
     * Gets the uri.
     *
     * @param obj the obj
     * @return the uri
     */
    private String getURI(Object obj) {
    	return "http://example.org/" + obj.getClass().getSimpleName();
    }
}
