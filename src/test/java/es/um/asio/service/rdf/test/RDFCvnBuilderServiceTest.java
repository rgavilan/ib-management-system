package es.um.asio.service.rdf.test;

import static org.assertj.core.api.Assertions.assertThat;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import org.apache.jena.graph.Node;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import es.um.asio.service.rdf.RDFCvnBuilderService;
import es.um.asio.service.rdf.impl.RDFCvnBuilderServiceImpl;
import es.um.asio.service.uris.URISGeneratorClient;
import es.um.asio.service.util.asserts.rdf.ModelAssert;
import es.um.asio.service.util.dummy.cvn.ChildCvnBeanDummy;
import es.um.asio.service.util.dummy.cvn.ComplexCvnBeanDummy;
import es.um.asio.service.util.dummy.cvn.CvnBeanWithCvnBeanCollectionDummy;
import es.um.asio.service.util.dummy.cvn.SimpleCvnBeanDummy;

@RunWith(SpringRunner.class)
public class RDFCvnBuilderServiceTest {
    
    @Configuration
    static class RDFCvnBuilderServiceTestConfig {
        @Bean
        RDFCvnBuilderService RDFCvnBuilderService() {
            return new RDFCvnBuilderServiceImpl();
        }
    }
    
    @Autowired
    private RDFCvnBuilderServiceImpl rdfCvnBuilderService;
    
    @MockBean
    private URISGeneratorClient urisGenerator;
    
    @Before
    public void setUp() {
        Mockito.when(urisGenerator.rootUri()).thenReturn("rootUri");
        Mockito.when(urisGenerator.createResourceID(Mockito.any())).thenReturn("resourceID");
        Mockito.when(urisGenerator.createPropertyURI(Mockito.any(),Mockito.any())).thenReturn("http://dummy.com/propertyUri#");
        Mockito.when(urisGenerator.createResourceTypeURI(Mockito.any())).thenReturn("resourceTypeID");
    }
    
    @Test
    public void whenCreateResourceOf_SimpleCvnBeanResource_thenGenerateRdf() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        SimpleCvnBeanDummy simpleCvnBean = new SimpleCvnBeanDummy();
        simpleCvnBean.setCode("00001");
        simpleCvnBean.setFoo("foo value");
        Model model =  ModelFactory.createDefaultModel();
        
        rdfCvnBuilderService.createResourceFromCvnBean(model, simpleCvnBean);
        
        ModelAssert.assertThat(model).hasStatementsSize(2);
        ModelAssert.assertThat(model).containsPredicate("http://dummy.com/propertyUri#foo").withValue("foo value");       
        ModelAssert.assertThat(model).containsPredicate("http://dummy.com/propertyUri#code").withValue("00001");  
    }
    
    @Test
    public void whenCreateResourceOf_ComplexCvnBeanResource_thenGenerateRdf() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        // Arrange
        SimpleCvnBeanDummy simpleCvnBean = new SimpleCvnBeanDummy();
        simpleCvnBean.setCode("00002");
        simpleCvnBean.setFoo("foo value");
        ComplexCvnBeanDummy complexCvnBean = new ComplexCvnBeanDummy();
        complexCvnBean.setCode("00001");
        complexCvnBean.setBar("bar value");
        complexCvnBean.setSimpleCvnBean(simpleCvnBean);        
        Model model =  ModelFactory.createDefaultModel();
        
        // Act
        rdfCvnBuilderService.createResourceFromCvnBean(model, complexCvnBean);       
        
        // Assert             
        ModelAssert.assertThat(model).hasStatementsSize(5);        
        ModelAssert.assertThat(model).containsPredicate("http://dummy.com/propertyUri#simpleCvnBean").withBlankNodeValue(); 
        ModelAssert.assertThat(model).containsPredicate("http://dummy.com/propertyUri#foo").withBlankNodeSubject().withValue("foo value"); 
        ModelAssert.assertThat(model).containsPredicate("http://dummy.com/propertyUri#code").withBlankNodeSubject().withValue("00002"); 
        ModelAssert.assertThat(model).containsPredicate("http://dummy.com/propertyUri#bar").withValue("bar value");
        ModelAssert.assertThat(model).containsPredicate("http://dummy.com/propertyUri#code").withValue("00001");
     
        // Assert - Relationships
        Statement complexCvnBean_simpleCvnBean = getStatement(model, "simpleCvnBean");
        Statement complexCvnBean_simpleCvnBean_foo = getStatement(model, "foo");
        Statement complexCvnBean_simpleCvnBean_code = getStatement(model, "code", "00002");
        
        Node simpleCvnNode = complexCvnBean_simpleCvnBean.getObject().asNode();
        assertThat(complexCvnBean_simpleCvnBean_foo.getSubject().asNode()).isEqualTo(simpleCvnNode);
        assertThat(complexCvnBean_simpleCvnBean_code.getSubject().asNode()).isEqualTo(simpleCvnNode);
    }
    
    
    @Test
    public void whenCreateResourceOf_CvnBeanResourceWithInheritance_thenGenerateRdf() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        // inherits from ParentCvnBeanDummy
        ChildCvnBeanDummy childCvnBean = new ChildCvnBeanDummy();
        childCvnBean.setCode("00001");
        childCvnBean.setParent("parent value");
        childCvnBean.setChild("child value");
        Model model =  ModelFactory.createDefaultModel();
        
        rdfCvnBuilderService.createResourceFromCvnBean(model, childCvnBean);        
        
        ModelAssert.assertThat(model).hasStatementsSize(3);                
        ModelAssert.assertThat(model).containsPredicate("http://dummy.com/propertyUri#code").withValue("00001");
        ModelAssert.assertThat(model).containsPredicate("http://dummy.com/propertyUri#parent").withValue("parent value");       
        ModelAssert.assertThat(model).containsPredicate("http://dummy.com/propertyUri#child").withValue("child value");       
    }
    
    @Test
    public void whenCreateResourceOf_CvnBeanWithCollectionOfSimpleCvnBean_thenGenerateRdf() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        // Arrange
        CvnBeanWithCvnBeanCollectionDummy cvnBeanWithCvnBeanCollection = new CvnBeanWithCvnBeanCollectionDummy();
        cvnBeanWithCvnBeanCollection.setCode("00003");
        SimpleCvnBeanDummy simpleCvnBean1 = new SimpleCvnBeanDummy();
        simpleCvnBean1.setCode("00001");
        simpleCvnBean1.setFoo("foo value 1");
        SimpleCvnBeanDummy simpleCvnBean2 = new SimpleCvnBeanDummy();
        simpleCvnBean2.setCode("00002");
        simpleCvnBean2.setFoo("foo value 2");
        cvnBeanWithCvnBeanCollection.setSimpleCvnBeans(Arrays.asList(simpleCvnBean1,simpleCvnBean2));
        Model model =  ModelFactory.createDefaultModel();
        
        // Act
        rdfCvnBuilderService.createResourceFromCvnBean(model, cvnBeanWithCvnBeanCollection);        
 
        // Assert        
        ModelAssert.assertThat(model).hasStatementsSize(9);        
        ModelAssert.assertThat(model).containsPredicate("http://dummy.com/propertyUri#code").withValue("00003");
        ModelAssert.assertThat(model).containsPredicate("http://dummy.com/propertyUri#simpleCvnBeans").withBlankNodeValue().hasStatementsSize(3);
        ModelAssert.assertThat(model).containsPredicate("http://www.w3.org/1999/02/22-rdf-syntax-ns#type").withValue("http://www.w3.org/1999/02/22-rdf-syntax-ns#Bag");
        ModelAssert.assertThat(model).containsPredicate("http://dummy.com/propertyUri#code").withValue("00001");
        ModelAssert.assertThat(model).containsPredicate("http://dummy.com/propertyUri#foo").withValue("foo value 1");
        ModelAssert.assertThat(model).containsPredicate("http://dummy.com/propertyUri#code").withValue("00002");
        ModelAssert.assertThat(model).containsPredicate("http://dummy.com/propertyUri#foo").withValue("foo value 2");

        // Assert - Relationships
        var parentSubject = getStatement(model, "http://dummy.com/propertyUri#code", "00003").getSubject();
        var parentSimpleCvnBeansValue = getStatement(model, parentSubject, "http://dummy.com/propertyUri#simpleCvnBeans").getResource();
        
        ModelAssert.assertThat(model).containsPredicate("http://dummy.com/propertyUri#simpleCvnBeans").withSubjectNode(parentSubject).hasStatementsSize(1);
        ModelAssert.assertThat(model).containsPredicate("http://dummy.com/propertyUri#simpleCvnBeans").withSubjectNode(parentSimpleCvnBeansValue).hasStatementsSize(2);
        ModelAssert.assertThat(model).containsPredicate("http://www.w3.org/1999/02/22-rdf-syntax-ns#type").withSubjectNode(parentSimpleCvnBeansValue).hasStatementsSize(1);
    }
    
    @Test
    public void whenCreateResourceOf_CvnBeanWithEmptyCollectionOfSimpleCvnBean_thenGenerateRdf() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        // Arrange
        CvnBeanWithCvnBeanCollectionDummy cvnBeanWithCvnBeanCollection = new CvnBeanWithCvnBeanCollectionDummy();
        cvnBeanWithCvnBeanCollection.setSimpleCvnBeans(Arrays.asList());
        Model model =  ModelFactory.createDefaultModel();
        // Act
        rdfCvnBuilderService.createResourceFromCvnBean(model, cvnBeanWithCvnBeanCollection);        
        print(model);

        // Assert        
        ModelAssert.assertThat(model).noContainsPredicate("http://dummy.com/propertyUri#simpleCvnBeans");
        ModelAssert.assertThat(model).noContainsValue("http://www.w3.org/1999/02/22-rdf-syntax-ns#Bag");
    }
    
    
    private static Statement getStatement(Model model, String propertyName, String propertyValue) {
        return model.listStatements().toList().stream().filter((st) -> st.getPredicate().toString().contains(propertyName) && st.getObject().toString().equals(propertyValue)).findFirst().get();
    }
    
    private static Statement getStatement(Model model, String propertyName) {
        return model.listStatements().toList().stream().filter((st) -> st.getPredicate().toString().contains(propertyName)).findFirst().get();
    }
    
    private static Statement getStatement(Model model, Resource subject, String propertyName) {
        return model.listStatements().toList().stream().filter((st) -> st.getSubject().equals(subject) && st.getPredicate().toString().contains(propertyName)).findFirst().get();
    }
     
    
    private void print(Model model) {
        
        StmtIterator iter = model.listStatements();
        
        // print out the predicate, subject and object of each statement
        while (iter.hasNext()) {
            Statement stmt      = iter.nextStatement();         // get next statement
            Resource  subject   = stmt.getSubject();   // get the subject
            Property  predicate = stmt.getPredicate(); // get the predicate
            RDFNode   object    = stmt.getObject();    // get the object
            
            System.out.print(subject.toString());
            System.out.print(" " + predicate.toString() + " ");
            if (object instanceof Resource) {
                System.out.print(object.toString());
            } else {
                // object is a literal
                System.out.print(" \"" + object.toString() + "\"");
            }
            System.out.println(" .");
        }
    }
    
}
