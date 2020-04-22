package es.um.asio.service.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.StringWriter;

import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFLanguages;

/**
 * The Class RDFUtil.
 */
public final class RDFUtil {

	/** The Constant RDF_XML_ABBREV. */
	public static final String RDF_XML_ABBREV = "RDF/XML-ABBREV";

	/**
	 * Method to transform model to string
	 *
	 * @param model  the model
	 * @param format the format
	 * @return the string
	 */
	public static String toString(Model model) {
		String result = StringUtils.EMPTY;
		if (model != null) {
			String syntax = RDFUtil.RDF_XML_ABBREV;
			StringWriter out = new StringWriter();
			model.write(out, syntax);
			result = out.toString();
		}
		return result;
	}

	/**
	 * Method to transform string to model
	 *
	 * @param strModel the str model
	 * @return the model
	 */
	public static Model toObject(String strModel) {
		StringReader stringReader = new StringReader(strModel);

		Model modelFromString = ModelFactory.createDefaultModel();
		RDFDataMgr.read(modelFromString, stringReader, null, RDFLanguages.RDFXML);

		return modelFromString;
	}

	/**
	 * Clone model.
	 *
	 * @param model the model
	 * @return the model
	 */
	public static Model cloneModel(Model model) {
		Model result = ModelFactory.createDefaultModel();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		model.write(out);
		ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
		result.read(in, null);
		return result;
	}
	
	/**
	 * Gets the name space from path removing the last parameter <br/>
	 * Examples: <br/>
	 * Input: http://hercules.org/um/es-ES/rec/ContratoProyecto/ <br/>
	 * Output: http://hercules.org/um/es-ES/rec/ <br/><br/>
		
	 * Input: http://hercules.org/um/es-ES/rec/ContratoProyecto <br/>
	 * Output: http://hercules.org/um/es-ES/rec/ <br/><br/>
		
	 * Input: https://hercules.org/um/es-ES/rec/ContratoProyecto/ <br/>
	 * Output: https://hercules.org/um/es-ES/rec/ <br/><br/>
		
	 * Input: https://hercules.org/um/es-ES/rec/ContratoProyecto <br/>
	 * Output: https://hercules.org/um/es-ES/rec/ <br/><br/>
	 *
	 * @param path the path
	 * @return the name space from path
	 */
	public static String getNameSpaceFromPath(String path) {
        if (path == null )
            return null;
        else {
        	String[] pathProtocol = path.split("/"); 
            String tempPath = path.replace('/', ' ');
            String[] pathParts = tempPath.split(" ");
            
            StringBuilder strBuilder = new StringBuilder();
            strBuilder.append(pathProtocol[0]).append("//");
            for(int i=2; i< pathParts.length-1 ; i++) {
            	strBuilder.append(pathParts[i]).append("/");
            }
            return strBuilder.toString();
        } 
    }
}
