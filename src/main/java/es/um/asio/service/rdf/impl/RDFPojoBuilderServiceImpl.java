package es.um.asio.service.rdf.impl;

import org.springframework.stereotype.Service;

import es.um.asio.abstractions.domain.ManagementBusEvent;
import es.um.asio.domain.PojoData;
import es.um.asio.service.model.GeneralBusEvent;
import es.um.asio.service.model.ModelWrapper;
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
		ModelWrapper model = this.createRDF(input.retrieveInnerObj());

		result = new ManagementBusEvent(model.getModelId(), RDFUtil.toString(model.getModel()), input.retrieveOperation());
		
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
	public ModelWrapper createRDF(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

}
