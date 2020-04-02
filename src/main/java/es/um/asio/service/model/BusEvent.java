package es.um.asio.service.model;

import java.lang.reflect.Field;

import es.um.asio.domain.Operation;
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
     * Retrieve object.
     *
     * @return the object
     */
    public Object retrieveObject() {
    	Object result = null;
    	try {
    		Field field = data.getClass().getDeclaredFields()[0];
    		// we need to set the modifier to public
    		field.setAccessible(true);
    		
			result = field.get(data);
		} catch (Exception e) {
			// FIXME
			e.printStackTrace();
		}
    	return result;
    }
    
    /**
     * Retrieve operation.
     *
     * @return the operation
     */
    public Operation retrieveOperation() {
    	Operation result; 
    	switch (data.getClass().getSimpleName()) {
		case "InputData":
			result = Operation.INSERT;			
			break;
		case "PojoData":
			// TODO
			result = Operation.INSERT;	
			break;

		default:
			result = Operation.INSERT;
			break;
		}
    	
    	return result;
    }
}
