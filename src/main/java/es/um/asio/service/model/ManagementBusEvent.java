package es.um.asio.service.model;

import es.um.asio.abstractions.domain.Operation;
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
// ** @JsonIgnoreProperties(ignoreUnknown=true)
public class ManagementBusEvent<Model> {

	// ** @JsonIgnoreProperties(ignoreUnknown=true)
	private Model model;
	
	private Operation operation;
}
