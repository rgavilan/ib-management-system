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
import es.um.asio.domain.cvn.CvnBean;
import es.um.asio.domain.cvn.CvnItemBean;
import es.um.asio.domain.cvn.CvnRootBean;
import es.um.asio.service.model.GeneralBusEvent;
import es.um.asio.service.model.ModelWrapper;
import es.um.asio.service.rdf.RDFCvnBuilderService;
import es.um.asio.service.uris.URISGeneratorClient;
import es.um.asio.service.util.RDFUtil;

@Service
public class RDFCvnBuilderServiceImpl implements RDFCvnBuilderService {
   
    /** 
     * Logger.     
    */
    private final Logger logger = LoggerFactory.getLogger(RDFCvnBuilderServiceImpl.class); 
    
    /** 
     * The uris generator client. 
    */
    @Autowired
    private URISGeneratorClient urisGeneratorClient;
    
    public ManagementBusEvent inkoveBuilder(GeneralBusEvent<?> input) {
        ManagementBusEvent result = null;
        if (!(input.getData() instanceof CvnRootBean)) {
            result = nextBuilder(input);
        }
        ModelWrapper model = this.createRDF(input.retrieveInnerObj());
        result = new ManagementBusEvent(model.getModelId(), RDFUtil.toString(model.getModel()), input.retrieveInnerObj().getClass().getSimpleName(), input.retrieveOperation());
        
        return result;
    }
 
    @Override
    public ManagementBusEvent nextBuilder(GeneralBusEvent<?> input) {
        return null;
    }

    public ModelWrapper createRDF(Object obj) {
        return this.createRDF((CvnRootBean)obj);
    }
    
 
    public ModelWrapper createRDF(CvnRootBean cvnRootBean) {
        ModelWrapper result = new ModelWrapper();
        
        // 0. create Cvn model
        Model model = ModelFactory.createDefaultModel();        
        model.createProperty(urisGeneratorClient.rootUri());
        
        try {
            // 1. create the resource
            String modelId = urisGeneratorClient.createResourceID(cvnRootBean);
            Resource resourceProperties = model.createResource(modelId);
            
            // 2. add cvnItemBeans bag
            var cvnItemsBag = model.createBag();
            Property cvnItemBeansProperty = model.createProperty(urisGeneratorClient.createPropertyURI(cvnRootBean, "cvnItemBean"), "cvnItemBean");
            for (CvnItemBean cvnItemBean: cvnRootBean.getCvnItemBean()) {
                cvnItemsBag.addProperty(cvnItemBeansProperty, createResourceFromCvnBean(model, cvnItemBean));
            }
            
            resourceProperties.addProperty(cvnItemBeansProperty, cvnItemsBag);
            
            // 3. we set the type
            Resource resourceClass = model.createResource(urisGeneratorClient.createResourceTypeURI(cvnRootBean.getClass().getName()));
            model.add(resourceProperties, RDF.type, resourceClass);

            // 4. we build the result model
            result.setModelId(modelId);
            result.setModel(model);
            
            } catch (Exception e) {
                logger.error("Error creating resource from input: " + cvnRootBean);
                e.printStackTrace();
            }
        
        return result;
    }
    
    @SuppressWarnings("unchecked")
    public Resource createResourceFromCvnBean(Model model, CvnBean cvnBean) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Resource cvnBeanResource = model.createResource();
        
        List<Field> fields = this.getCvnBeanFields(cvnBean.getClass());       

        for (Field field : fields) {            
            field.setAccessible(true);
            Object value = field.get(cvnBean);           
            if(value == null) {
                continue;
            }
            
            Property property = model.createProperty(urisGeneratorClient.createPropertyURI(cvnBean, field.getName()), field.getName());
            
            if(value instanceof CvnBean) {                
                cvnBeanResource.addProperty(property, createResourceFromCvnBean(model,(CvnBean)value));
                continue;
            }
            if(value instanceof Collection) {
                Collection<CvnBean> internalCvnBeans = (Collection<CvnBean>)value;
                if(internalCvnBeans.isEmpty()) {
                    continue;
                }
                
                var cvnBeansBag = model.createBag();                
                for (CvnBean internalCvnBean : internalCvnBeans) {
                    cvnBeansBag.addProperty(property, createResourceFromCvnBean(model,internalCvnBean));                    
                }
                cvnBeanResource.addProperty(property, cvnBeansBag);
                continue;
            }
             
            String propertyValue = BeanUtils.getSimpleProperty(cvnBean, field.getName());
            cvnBeanResource.addProperty(property, propertyValue);
        }
 
        return cvnBeanResource;
    }
  

    @SuppressWarnings("unchecked")
    private <T extends CvnBean> List<Field> getCvnBeanFields(Class<T> cvnBeanClass){
        ArrayList<Field> cvnfields = new ArrayList<>();
        
        cvnfields.addAll(Arrays.asList(cvnBeanClass.getDeclaredFields()));        
        if (CvnBean.class.isAssignableFrom(cvnBeanClass.getSuperclass())) {
            cvnfields.addAll(getCvnBeanFields((Class<T>)cvnBeanClass.getSuperclass()));
        }
 
        return cvnfields;
    }
    
}