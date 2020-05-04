package es.um.asio.service.util.asserts.rdf;


import java.util.List;
import java.util.stream.Collectors;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

public class ModelAssert extends AbstractAssert<ModelAssert, Model> {
 
    public static ModelAssert assertThat(Model actual) {
        return new ModelAssert(actual);
    }

    List<Statement> actualStatements;

    public ModelAssert(Model actual) {
        super(actual, ModelAssert.class);   
        this.actualStatements = actual.listStatements().toList();
    }
  
    
    public ModelAssert containsPredicate(String property) {        
        var statements =  this.actualStatements.stream().filter((st) -> st.getPredicate().toString().equals(property)).collect(Collectors.toList());
        if(statements.isEmpty()) {
            failWithMessage("Model not contains any Statement with %s property", property);
        }     
        this.actualStatements = statements;
        return this;
    }
    

    public ModelAssert withBlankNodeSubject() {
        var statements =  this.actualStatements.stream().filter((st) -> st.getSubject().asNode().isBlank()).collect(Collectors.toList());
        if(statements.isEmpty()) {
            failWithMessage("Model not contains any Statement Blank Node subject");
        }     
        this.actualStatements = statements;
        return this;
    }
  
    
    public ModelAssert withBlankNodeValue() {
         var statements = this.actualStatements.stream().filter((st) -> st.getObject().asNode().isBlank()).collect(Collectors.toList());
         if(statements.isEmpty()) {
             failWithMessage("Model not contains any Statement with Blank Node value");
         }
         this.actualStatements = statements;
         return this;
    }
    
    public ModelAssert withValue(String value) {
        var statements = this.actualStatements.stream().filter((st) -> st.getObject().toString().equals(value)).collect(Collectors.toList());
        if(statements.isEmpty()) {
            failWithMessage("Model not contains any Statement with %s value", value);
        }
        this.actualStatements = statements;
        return this;
   }
    
    public ModelAssert withSubjectNode(Resource resource) {
        var statements = this.actualStatements.stream().filter((st) -> st.getSubject().equals(resource)).collect(Collectors.toList());
        if(statements.isEmpty()) {
            failWithMessage("Model not contains any Statement with %s resource", resource);
        }
        this.actualStatements = statements;
        return this;
   }

    public ModelAssert hasStatementsSize(int size) {
        Assertions.assertThat(this.actualStatements.size()).isEqualTo(size);
        return this;
    }

    public void noContainsPredicate(String property) {        
        var statements =  this.actualStatements.stream().filter((st) -> st.getPredicate().toString().equals(property)).collect(Collectors.toList());
        if(!statements.isEmpty()) {
            failWithMessage("Model contains any Statement with %s property", property);
        }     
    }
    
    public void noContainsValue(String value) {        
        var statements =  this.actualStatements.stream().filter((st) -> st.getObject().toString().equals(value)).collect(Collectors.toList());
        if(!statements.isEmpty()) {
            failWithMessage("Model contains any Statement with %s value", value);
        }     
    }
}
