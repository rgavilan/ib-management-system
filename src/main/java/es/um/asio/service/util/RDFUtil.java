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
}
