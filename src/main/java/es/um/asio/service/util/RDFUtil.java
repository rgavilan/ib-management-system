package es.um.asio.service.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.RDFFormat;
import org.apache.jena.riot.RDFWriter;

public final class RDFUtil {
	
	/**
	 * To string.
	 *
	 * @param model the model
	 * @param format the format
	 * @return the string
	 */
	public static String toString(Model model, RDFFormat format) {
		try(ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            RDFWriter.create().source(model).format(format).context(null).output(out);
            out.flush();
            return out.toString("UTF-8");
        } catch (IOException e) { throw new RuntimeException(e); }
	}
}
