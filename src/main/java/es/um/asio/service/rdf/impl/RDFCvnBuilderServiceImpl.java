package es.um.asio.service.rdf.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
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
import es.um.asio.domain.InputData;
import es.um.asio.domain.cvn.CvnBean;
import es.um.asio.domain.cvn.CvnRootBean;
import es.um.asio.service.model.GeneralBusEvent;
import es.um.asio.service.model.ModelWrapper;
import es.um.asio.service.rdf.RDFCvnBuilderService;
import es.um.asio.service.rdf.RDFDatasetBuilderService;
import es.um.asio.service.uris.URISGeneratorClient;
import es.um.asio.service.util.RDFUtil;

@Service
public class RDFCvnBuilderServiceImpl implements RDFCvnBuilderService {
   
    /** 
     * Logger.     
    */
    private final Logger logger = LoggerFactory.getLogger(RDFCvnBuilderServiceImpl.class); 
    
    /**
     *  The rdf dataset builder service. 
    */
    @Autowired
    private RDFDatasetBuilderService rdfDatasetBuilderService;
    
    /** 
     * The uris generator client. 
    */
    @Autowired
    private URISGeneratorClient urisGeneratorClient;
    
    /**
     * Inkove builder.
     *
     * @param input the input
     * @return the management bus event
     */
    public ManagementBusEvent inkoveBuilder(GeneralBusEvent<?> input) {        
        if (canBeProcessed(input)) {
            ModelWrapper model = this.createRDF(input.retrieveInnerObj());
            return new ManagementBusEvent(model.getModelId(), RDFUtil.toString(model.getModel()), input.retrieveInnerObj().getClass().getSimpleName(), input.retrieveOperation());            
        }
      
        return nextBuilder(input);
    }
 
    /**
     * Next builder.
     *
     * @param input the input
     * @return the management bus event
     */
    @Override
    public ManagementBusEvent nextBuilder(GeneralBusEvent<?> input) {
        return rdfDatasetBuilderService.inkoveBuilder(input);
    } 
    
    /**
     * Can be processed.
     *
     * @param input the input
     * @return the boolean
     */
    private Boolean canBeProcessed(GeneralBusEvent<?> input) {
        return input.getData() instanceof InputData && ((InputData<?>)input.getData()).getData() instanceof CvnRootBean;
    }    
 
    /**
     * Creates the RDF.
     *
     * @param obj the obj
     * @return the model wrapper
     */
    public ModelWrapper createRDF(Object obj) {
        ModelWrapper result = new ModelWrapper();

        // 0. create Cvn model
        Model model = ModelFactory.createDefaultModel();
        model.createProperty(urisGeneratorClient.rootUri());

        try {

            // 1. create the resource
            String modelId = urisGeneratorClient.createResourceID(obj);
            Resource resourceProperties = createResource(model, obj, modelId);

            // 2. we set the type
            Resource resourceClass = model.createResource(urisGeneratorClient.createResourceTypeURI(obj.getClass().getName()));
            model.add(resourceProperties, RDF.type, resourceClass);

            // 4. we build the result model
            result.setModelId(modelId);
            result.setModel(model);

        } catch (Exception e) {
            logger.error("Error creating resource from input: " + obj);
            e.printStackTrace();
        }

        return result;
    }
    

    /**
     * Creates the resource from cvn bean.
     *
     * @param model the model
     * @param obj the obj
     * @param resourceID the resource ID
     * @return the resource
     * @throws IllegalAccessException the illegal access exception
     * @throws InvocationTargetException the invocation target exception
     * @throws NoSuchMethodException the no such method exception
     */
    public Resource createResource(Model model, Object obj, String resourceID) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        
        Resource resource = StringUtils.isEmpty(resourceID) ?  model.createResource():model.createResource(resourceID);
        
        List<Field> fields = this.getFields(obj.getClass());       
        for (Field field : fields) {            
            field.setAccessible(true);
            Object value = field.get(obj);      
            Property property = model.createProperty(urisGeneratorClient.createPropertyURI(obj, field.getName()), field.getName());
            
            if(value instanceof CvnBean) {                
                resource.addProperty(property, createResource(model,value, StringUtils.EMPTY));
                continue;
            }
            if(value instanceof Collection) {
                Collection<?> internalCvnBeans = (Collection<?>)value;               
                var bag = model.createBag();   
                
                if (internalCvnBeans != null) {
                    for (Object internalCvnBean : internalCvnBeans) {
                        bag.addProperty(property, createResource(model, internalCvnBean, StringUtils.EMPTY));
                    }
                }
                
                resource.addProperty(property, bag);
                continue;
            }
             
            String propertyValue = BeanUtils.getSimpleProperty(obj, field.getName());
            if(StringUtils.isEmpty(propertyValue)) {
                propertyValue = StringUtils.EMPTY;
            }
            resource.addProperty(property, propertyValue);
        }
 
        return resource;
    }  
    

    /**
     * Gets the fields.
     *
     * @param clazz the clazz
     * @return the fields
     */
    @SuppressWarnings("unchecked")
    private List<Field> getFields(Class<?> clazz){
        ArrayList<Field> fields = new ArrayList<>();
        
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));        
        if (CvnBean.class.isAssignableFrom(clazz.getSuperclass())) {
            fields.addAll(getFields((Class<CvnBean>)clazz.getSuperclass()));
        }
 
        return fields;
    }
    
}