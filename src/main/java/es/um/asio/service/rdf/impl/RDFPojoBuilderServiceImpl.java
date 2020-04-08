package es.um.asio.service.rdf.impl;

import org.apache.jena.rdf.model.Model;
import org.springframework.stereotype.Service;

import es.um.asio.domain.PojoData;
import es.um.asio.service.model.GeneralBusEvent;
import es.um.asio.service.rdf.RDFPojoBuilderService;

/**
 * The Class RDFPojoBuilderServiceImpl.
 */
@Service
public class RDFPojoBuilderServiceImpl implements RDFPojoBuilderService {

	/**
	 * Creates the.
	 *
	 * @param input the input
	 * @return the model
	 */
	@Override
	public Model inkoveBuilder(GeneralBusEvent<?> input) {
		Model result = null;
		if (!(input.getData() instanceof PojoData)) {
			result = nextBuilder(input);
		}
		result = this.createRDF(input.retrieveInnerObj());

		return result;
	}

	/**
	 * Next builder.
	 *
	 * @param input the input
	 * @return the model
	 */
	@Override
	public Model nextBuilder(GeneralBusEvent<?> input) {
		return null;
	}

	@Override
	public Model createRDF(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

}
