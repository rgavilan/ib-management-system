package es.um.asio.service.rdf.impl;

import org.apache.jena.rdf.model.Model;
import org.springframework.stereotype.Service;

import es.um.asio.abstractions.domain.ManagementBusEvent;
import es.um.asio.domain.PojoData;
import es.um.asio.service.model.GeneralBusEvent;
import es.um.asio.service.rdf.RDFPojoBuilderService;
import es.um.asio.service.util.RDFUtil;

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
	public ManagementBusEvent inkoveBuilder(GeneralBusEvent<?> input) {
		ManagementBusEvent result = null;
		if (!(input.getData() instanceof PojoData)) {
			result = nextBuilder(input);
		}
		Model model = this.createRDF(input.retrieveInnerObj());

		result = new ManagementBusEvent(RDFUtil.toString(model), input.retrieveOperation());
		
		return result;
	}

	/**
	 * Next builder.
	 *
	 * @param input the input
	 * @return the model
	 */
	@Override
	public ManagementBusEvent nextBuilder(GeneralBusEvent<?> input) {
		return null;
	}

	@Override
	public Model createRDF(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

}
