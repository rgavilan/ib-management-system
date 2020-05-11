package es.um.asio.service.test;

import java.nio.file.Paths;

import org.apache.jena.ontology.OntDocumentManager;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;

public class AntologyTest {

	public static void main(String[] args) {

		String currentPath = Paths.get("").toAbsolutePath().toString();

		OntModel m = ModelFactory.createOntologyModel();
		OntDocumentManager dm = m.getDocumentManager();
		
		dm.addAltEntry("http://hercules.org/um/ontology",
				"file:" + currentPath + "/src/test/java/es/um/asio/service/test/ontology.rdf");
		
		m.read("http://hercules.org/um/ontology");

		m.write(System.out, "turtle");

	}

}
