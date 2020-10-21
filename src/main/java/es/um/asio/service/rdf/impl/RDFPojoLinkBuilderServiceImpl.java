package es.um.asio.service.rdf.impl;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import es.um.asio.abstractions.domain.ManagementBusEvent;
import es.um.asio.abstractions.domain.Operation;
import es.um.asio.domain.PojoLinkData;
import es.um.asio.service.model.GeneralBusEvent;
import es.um.asio.service.model.ModelWrapper;
import es.um.asio.service.rdf.RDFPojoLinkBuilderService;

/**
 * The Class RDFPojoLinkBuilderServiceImpl.
 */
@Service
public class RDFPojoLinkBuilderServiceImpl implements RDFPojoLinkBuilderService {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(RDFPojoLinkBuilderServiceImpl.class);

	/** The Constant ETL_POJO_CLASS. */
	private static final String ETL_POJO_CLASS = "clase";

	/** The Constant ETL_POJO_ID. */
	private static final String ETL_POJO_ID = "id";

	@Override
	public ManagementBusEvent nextBuilder(final GeneralBusEvent<?> input) {
		return null;
	}

	@Override
	public ManagementBusEvent inkoveBuilder(final GeneralBusEvent<?> input) {
		ManagementBusEvent result = null;
		if (input.getData() instanceof PojoLinkData) {
			final ModelWrapper model = this.createRDF(input.retrieveInnerObj());

			result = new ManagementBusEvent(
					model.getModelId(), 
					StringUtils.EMPTY,
					model.getLinkedModel().toString(), 
					this.getClass(input.retrieveInnerObj()),	Operation.LINKED_INSERT);
		} else {
			result = this.nextBuilder(input);
		}
		return result;
	}

	@Override
	public ModelWrapper createRDF(final Object obj) {
		final ModelWrapper result = new ModelWrapper();

		String objectId;
		try {
			// model ID
			objectId = this.safetyCheck(PropertyUtils.getProperty(obj, RDFPojoLinkBuilderServiceImpl.ETL_POJO_ID));
			result.setModelId(objectId);
			
			// nested object
			result.setLinkedModel(obj);

		} catch (Exception e) {
			this.logger.error("Error creating resource from linking input: " + obj);
			this.logger.error("Error cause " + e.getMessage());
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * Gets the class.
	 *
	 * @param obj the obj
	 * @return the class
	 */
	private String getClass(final Object obj) {
		String result = StringUtils.EMPTY;
		try {
			result = (String) PropertyUtils.getProperty(obj, RDFPojoLinkBuilderServiceImpl.ETL_POJO_CLASS);
		} catch (final Exception e) {
			this.logger.error("Unknown class in object " + obj.toString());
		}
		return result;
	}

	/**
	 * Safety check.
	 *
	 * @param obj the obj
	 * @return the string
	 */
	private String safetyCheck(Object obj) {
		String result = StringUtils.EMPTY;
		if (obj == null) {
			return result;
		}
		if (obj instanceof Number) {
			return ((Number) obj).toString();
		}
		if (obj instanceof String) {
			return (String) obj;
		}

		return result;
	}

}
