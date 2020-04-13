/*
 * 
 */
package es.um.asio.service.util.dummy.data;

import es.um.asio.domain.DataSetData;
import es.um.asio.service.util.DatasetTypeTest;

/**
 * The Interface DummyData.
 */
public interface DummyData {

	/**
	 * Creates the instance.
	 *
	 * @param type the type
	 * @return the data set data
	 */
	DataSetData createInstance(DatasetTypeTest type);
	
	/**
	 * Next.
	 *
	 * @param type the type
	 * @return the data set data
	 */
	DataSetData next(DatasetTypeTest type);
}
