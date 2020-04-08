package es.um.asio.service.rdf.impl;

import java.lang.reflect.Field;
import java.net.URLEncoder;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import es.um.asio.abstractions.Constants;
import es.um.asio.service.rdf.RDFGeneratorIDService;

/**
 * The Class GeneratorIDServiceImpl.
 */
@Service
public class RDFGeneratorIDServiceImpl implements RDFGeneratorIDService {

	/** Logger. */
	private final Logger logger = LoggerFactory.getLogger(RDFGeneratorIDServiceImpl.class);

	/** The Constant CHART_SET. */
	private static final String CHART_SET = java.nio.charset.StandardCharsets.UTF_8.toString();

	/** The Constant MAX_NUM_KEY_FIELDS. */
	private static final int MAX_NUM_KEY_FIELDS = 5;

	/** The Constant MAX_LENGTH_KEY_FIELDS. */
	private static final int MAX_LENGTH_KEY_FIELDS = 150;

	/**
	 * Generate resource ID.
	 *
	 * @param obj the obj
	 * @return the string
	 */
	@Override
	public String generateResourceID(final Object obj) {
		final StringBuilder result = new StringBuilder(Constants.ROOT_URI);

		Field field = null;
		String valueField = StringUtils.EMPTY;

		// we take the smallest value
		for (int i = 0; (i < obj.getClass().getDeclaredFields().length) && (i < MAX_NUM_KEY_FIELDS); i++) {

			field = obj.getClass().getDeclaredFields()[i];
			valueField = this.getSanityValue(obj, field.getName());

			if (StringUtils.isNotBlank(valueField)) {
				result.append("/");
				result.append(valueField);
			}
		}

		return result.toString();
	}

	/**
	 * Gets the sanity value.
	 *
	 * @param obj       the obj
	 * @param fieldName the field name
	 * @return the sanity value
	 */
	private String getSanityValue(final Object obj, final String fieldName) {
		String result = StringUtils.EMPTY;

		try {
			result = BeanUtils.getSimpleProperty(obj, fieldName);

			// we truncate the string to MAX_LENGTH_KEY_FIELDS value
			final int maxLimit = result.length() > MAX_LENGTH_KEY_FIELDS ? MAX_LENGTH_KEY_FIELDS : result.length();
			result = result.substring(0, maxLimit);

			// we skip strange characters
			result = URLEncoder.encode(result, CHART_SET);
		} catch (final Exception e) {
			this.logger.error("Error retrieving value " + fieldName + " from: " + obj.toString());
		}

		return result;
	}
}
