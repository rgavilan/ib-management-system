package es.um.asio.service.util;

import es.um.asio.domain.DataSetData;
import es.um.asio.domain.InputData;
import es.um.asio.service.model.GeneralBusEvent;
import es.um.asio.service.util.dummy.data.ConceptoGrupoDummy;

/**
 * The Class AsioMockupBuilder.
 */
public class AsioMockupBuilder {
	
	/**
	 * Creates the bus event data set mockup.
	 *
	 * @return the general bus event
	 */
	public static GeneralBusEvent<InputData<DataSetData>> createBusEventDataSet(DatasetTypeTest type) {
		GeneralBusEvent<InputData<DataSetData>> result = new GeneralBusEvent<InputData<DataSetData>>();
		
		// chain of responsibility pattern
		ConceptoGrupoDummy conceptoGrupoDummy = (ConceptoGrupoDummy) new ConceptoGrupoDummy().createInstance(type);
		
		InputData<DataSetData> inputData = new InputData<DataSetData>(conceptoGrupoDummy);
		
		result.setData(inputData);
		
		return result;
	}
	
	
}
