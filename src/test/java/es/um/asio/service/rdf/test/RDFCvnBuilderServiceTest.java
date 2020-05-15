package es.um.asio.service.rdf.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.apache.jena.graph.Node;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import es.um.asio.domain.cvn.CvnAuthorBean;
import es.um.asio.domain.cvn.CvnBoolean;
import es.um.asio.domain.cvn.CvnFamilyNameBean;
import es.um.asio.domain.cvn.CvnItemBean;
import es.um.asio.domain.cvn.CvnRootBean;
import es.um.asio.service.rdf.RDFCvnBuilderService;
import es.um.asio.service.rdf.RDFDatasetBuilderService;
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
    
    @MockBean
    private RDFDatasetBuilderService rdfDatasetBuilderService;
    
    @Before
    public void setUp() {
        Mockito.when(urisGenerator.rootUri()).thenReturn("http://dummy.org");
        Mockito.when(urisGenerator.createResourceID(Mockito.any())).thenReturn("http://dummy.org/resourceID");
        Mockito.when(urisGenerator.createPropertyURI(Mockito.any(),Mockito.any())).thenReturn("http://www.w3.org/2001/asio-rdf/3.0#");
        Mockito.when(urisGenerator.createResourceTypeURI(Mockito.any())).thenAnswer(invocation -> {
                                                    return "http://dummy.org/".concat(invocation.getArgument(0)); });
    }
       
    @Test
    public void whenCreateResourceOf_SimpleCvnBeanResource_thenGenerateRdf() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        SimpleCvnBeanDummy simpleCvnBean = new SimpleCvnBeanDummy();
        simpleCvnBean.setCode("00001");
        simpleCvnBean.setFoo("foo value");
        Model model =  ModelFactory.createDefaultModel();
        
        rdfCvnBuilderService.createResource(model, simpleCvnBean, StringUtils.EMPTY);
        
        ModelAssert.assertThat(model).hasStatementsSize(2);
        ModelAssert.assertThat(model).containsPredicate("http://www.w3.org/2001/asio-rdf/3.0#foo").withValue("foo value");       
        ModelAssert.assertThat(model).containsPredicate("http://www.w3.org/2001/asio-rdf/3.0#code").withValue("00001");  
    }
    
    @Test
    public void whenCreateResourceOf_SimpleCvnBeanWithEmptyAndNullProperties_thenGenerateRdf() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        SimpleCvnBeanDummy simpleCvnBean = new SimpleCvnBeanDummy();
        simpleCvnBean.setCode("");
        simpleCvnBean.setFoo(null);
        Model model =  ModelFactory.createDefaultModel();
        
        rdfCvnBuilderService.createResource(model, simpleCvnBean, StringUtils.EMPTY);
        
        ModelAssert.assertThat(model).hasStatementsSize(1);
        ModelAssert.assertThat(model).containsPredicate("http://www.w3.org/2001/asio-rdf/3.0#code").withValue(StringUtils.EMPTY);  
        ModelAssert.assertThat(model).noContainsPredicate("http://www.w3.org/2001/asio-rdf/3.0#foo");
    }
    
    
    @Test
    public void whenCreateResourceOf_ComplexCvnBean_thenGenerateRdf() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
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
        rdfCvnBuilderService.createResource(model, complexCvnBean, StringUtils.EMPTY);       
        
        // Assert             
        ModelAssert.assertThat(model).hasStatementsSize(5);        
        ModelAssert.assertThat(model).containsPredicate("http://www.w3.org/2001/asio-rdf/3.0#simpleCvnBean").withBlankNodeValue(); 
        ModelAssert.assertThat(model).containsPredicate("http://www.w3.org/2001/asio-rdf/3.0#foo").withBlankNodeSubject().withValue("foo value"); 
        ModelAssert.assertThat(model).containsPredicate("http://www.w3.org/2001/asio-rdf/3.0#code").withBlankNodeSubject().withValue("00002"); 
        ModelAssert.assertThat(model).containsPredicate("http://www.w3.org/2001/asio-rdf/3.0#bar").withValue("bar value");
        ModelAssert.assertThat(model).containsPredicate("http://www.w3.org/2001/asio-rdf/3.0#code").withValue("00001");
     
        // Assert - Relationships
        Statement complexCvnBean_simpleCvnBean = getStatement(model, "simpleCvnBean");
        Statement complexCvnBean_simpleCvnBean_foo = getStatement(model, "foo");
        Statement complexCvnBean_simpleCvnBean_code = getStatement(model, "code", "00002");
        
        Node simpleCvnNode = complexCvnBean_simpleCvnBean.getObject().asNode();
        assertThat(complexCvnBean_simpleCvnBean_foo.getSubject().asNode()).isEqualTo(simpleCvnNode);
        assertThat(complexCvnBean_simpleCvnBean_code.getSubject().asNode()).isEqualTo(simpleCvnNode);
    }
    
  
    @Test
    public void whenCreateResourceOf_CvnBeanWithInheritance_thenGenerateRdf() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        // inherits from ParentCvnBeanDummy
        ChildCvnBeanDummy childCvnBean = new ChildCvnBeanDummy();
        childCvnBean.setCode("00001");
        childCvnBean.setParent("parent value");
        childCvnBean.setChild("child value");
        Model model =  ModelFactory.createDefaultModel();
        
        rdfCvnBuilderService.createResource(model, childCvnBean, StringUtils.EMPTY);        
        
        ModelAssert.assertThat(model).hasStatementsSize(3);                
        ModelAssert.assertThat(model).containsPredicate("http://www.w3.org/2001/asio-rdf/3.0#code").withValue("00001");
        ModelAssert.assertThat(model).containsPredicate("http://www.w3.org/2001/asio-rdf/3.0#parent").withValue("parent value");       
        ModelAssert.assertThat(model).containsPredicate("http://www.w3.org/2001/asio-rdf/3.0#child").withValue("child value");       
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
        rdfCvnBuilderService.createResource(model, cvnBeanWithCvnBeanCollection, StringUtils.EMPTY);        
 
        // Assert        
        ModelAssert.assertThat(model).hasStatementsSize(9);        
        ModelAssert.assertThat(model).containsPredicate("http://www.w3.org/2001/asio-rdf/3.0#code").withValue("00003");
        ModelAssert.assertThat(model).containsPredicate("http://www.w3.org/2001/asio-rdf/3.0#simpleCvnBeans").withBlankNodeValue().hasStatementsSize(3);
        ModelAssert.assertThat(model).containsPredicate("http://www.w3.org/1999/02/22-rdf-syntax-ns#type").withValue("http://www.w3.org/1999/02/22-rdf-syntax-ns#Bag");
        ModelAssert.assertThat(model).containsPredicate("http://www.w3.org/2001/asio-rdf/3.0#code").withValue("00001");
        ModelAssert.assertThat(model).containsPredicate("http://www.w3.org/2001/asio-rdf/3.0#foo").withValue("foo value 1");
        ModelAssert.assertThat(model).containsPredicate("http://www.w3.org/2001/asio-rdf/3.0#code").withValue("00002");
        ModelAssert.assertThat(model).containsPredicate("http://www.w3.org/2001/asio-rdf/3.0#foo").withValue("foo value 2");

        // Assert - Relationships
        var parentSubject = getStatement(model, "http://www.w3.org/2001/asio-rdf/3.0#code", "00003").getSubject();
        var parentSimpleCvnBeansValue = getStatement(model, parentSubject, "http://www.w3.org/2001/asio-rdf/3.0#simpleCvnBeans").getResource();
        
        ModelAssert.assertThat(model).containsPredicate("http://www.w3.org/2001/asio-rdf/3.0#simpleCvnBeans").withSubjectNode(parentSubject).hasStatementsSize(1);
        ModelAssert.assertThat(model).containsPredicate("http://www.w3.org/2001/asio-rdf/3.0#simpleCvnBeans").withSubjectNode(parentSimpleCvnBeansValue).hasStatementsSize(2);
        ModelAssert.assertThat(model).containsPredicate("http://www.w3.org/1999/02/22-rdf-syntax-ns#type").withSubjectNode(parentSimpleCvnBeansValue).hasStatementsSize(1);
    }
    
    @Test
    public void whenCreateResourceOf_CvnBeanWithEmptyCollectionOfSimpleCvnBean_thenGenerateRdf() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        // Arrange
        CvnBeanWithCvnBeanCollectionDummy cvnBeanWithCvnBeanCollection = new CvnBeanWithCvnBeanCollectionDummy();
        cvnBeanWithCvnBeanCollection.setSimpleCvnBeans(Arrays.asList());
        Model model =  ModelFactory.createDefaultModel();
        
        // Act
        rdfCvnBuilderService.createResource(model, cvnBeanWithCvnBeanCollection, StringUtils.EMPTY);

        // Assert        
        ModelAssert.assertThat(model).hasStatementsSize(0);  
    }
    
    @Test
    public void CvnRootBeanToRdf_ApprovalTest() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        CvnRootBean cvnRootBean = givenACvnRootBean();
        String rdfTurtleExpected = givenACvnRootBeanRdfTurtle();
                
        Model model = rdfCvnBuilderService.createRDF(cvnRootBean).getModel();
        
        StringWriter out = new StringWriter();       
        RDFDataMgr.write(out, model, RDFFormat.TURTLE);
        String rdfTurtleResult = out.toString();

       assertThat(rdfTurtleResult).isEqualTo(rdfTurtleExpected);
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
    
    
    private CvnRootBean givenACvnRootBean() {        
        CvnRootBean cvnRootBean = new CvnRootBean();        
        CvnItemBean cvnItemBean = new CvnItemBean();
        CvnBoolean cvnBoolean1 = new CvnBoolean();
        cvnBoolean1.setCode("00001");
        cvnBoolean1.setValue(true);
        CvnBoolean cvnBoolean2 = new CvnBoolean();
        cvnBoolean2.setCode("00002");
        cvnBoolean2.setValue(false);
        cvnItemBean.setCvnBoolean(Arrays.asList(cvnBoolean1, cvnBoolean2));
        
        CvnAuthorBean cvnAuthorBean = new CvnAuthorBean();
        cvnAuthorBean.setGivenName("Dummy name");
        CvnFamilyNameBean cvnFamilyNameBean = new CvnFamilyNameBean();
        cvnFamilyNameBean.setFirstFamilyName("First famility dummy name");
        cvnAuthorBean.setCvnFamilyNameBean(cvnFamilyNameBean);
        cvnItemBean.setCvnAuthorBean(Arrays.asList(cvnAuthorBean));
        
        cvnRootBean.setCvnItemBean(Arrays.asList(cvnItemBean));
        return cvnRootBean;
    }
    
    private String givenACvnRootBeanRdfTurtle() {
        return "<http://dummy.org/resourceID>\n" + 
                "        a       <http://dummy.org/es.um.asio.domain.cvn.CvnRootBean> ;\n" + 
                "        <http://www.w3.org/2001/asio-rdf/3.0#cvnItemBean>\n" + 
                "                [ a       <http://www.w3.org/1999/02/22-rdf-syntax-ns#Bag> ;\n" + 
                "                  <http://www.w3.org/2001/asio-rdf/3.0#cvnItemBean>\n" + 
                "                          [ <http://www.w3.org/2001/asio-rdf/3.0#cvnAuthorBean>\n" + 
                "                                    [ a       <http://www.w3.org/1999/02/22-rdf-syntax-ns#Bag> ;\n" + 
                "                                      <http://www.w3.org/2001/asio-rdf/3.0#cvnAuthorBean>\n" + 
                "                                              [ <http://www.w3.org/2001/asio-rdf/3.0#cvnFamilyNameBean>\n" + 
                "                                                        [ <http://www.w3.org/2001/asio-rdf/3.0#firstFamilyName>\n" + 
                "                                                                  \"First famility dummy name\" ] ;\n" + 
                "                                                <http://www.w3.org/2001/asio-rdf/3.0#givenName>\n" + 
                "                                                        \"Dummy name\"\n" + 
                "                                              ]\n" + 
                "                                    ] ;\n" + 
                "                            <http://www.w3.org/2001/asio-rdf/3.0#cvnBoolean>\n" + 
                "                                    [ a       <http://www.w3.org/1999/02/22-rdf-syntax-ns#Bag> ;\n" + 
                "                                      <http://www.w3.org/2001/asio-rdf/3.0#cvnBoolean>\n" + 
                "                                              [ <http://www.w3.org/2001/asio-rdf/3.0#code>\n" + 
                "                                                        \"00002\" ;\n" + 
                "                                                <http://www.w3.org/2001/asio-rdf/3.0#value>\n" + 
                "                                                        \"false\"\n" + 
                "                                              ] ;\n" + 
                "                                      <http://www.w3.org/2001/asio-rdf/3.0#cvnBoolean>\n" + 
                "                                              [ <http://www.w3.org/2001/asio-rdf/3.0#code>\n" + 
                "                                                        \"00001\" ;\n" + 
                "                                                <http://www.w3.org/2001/asio-rdf/3.0#value>\n" + 
                "                                                        \"true\"\n" + 
                "                                              ]\n" + 
                "                                    ]\n" + 
                "                          ]\n" + 
                "                ] .\n" +
                "";
    }
}
