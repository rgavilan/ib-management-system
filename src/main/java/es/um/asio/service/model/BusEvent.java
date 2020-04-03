package es.um.asio.service.model;

import es.um.asio.domain.InputData;
import es.um.asio.domain.Operation;
import es.um.asio.domain.PojoData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BusEvent<T extends Object> {
	/**
	 * Data set data.
	 */
	private T data;

	/**
	 * Retrieve operation.
	 *
	 * @return the operation
	 */
	public Operation retrieveOperation() {
		Operation result = Operation.INSERT;

		if (data instanceof InputData) {
			result = Operation.INSERT;
		}

		if (data instanceof PojoData) {
			result = ((PojoData<?>) data).getOperation();
		}
		return result;
	}
}
