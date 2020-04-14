package es.um.asio.service.util.dummy.data;

import es.um.asio.domain.DataSetData;
import es.um.asio.domain.gruposInvestigacion.ConceptoInvestigador;
import es.um.asio.service.util.test.DatasetTypeTest;

/**
 * The Class ConceptoInvestigadorDummy.
 */
public class ConceptoInvestigadorDummy extends ConceptoInvestigador implements DummyData {

	private static final long serialVersionUID = 1176647267460058490L;
	
	// vbles
    private long idPersona;
    private long numero;
    private String codTipoConcepto;
    private String texto;
    
	public ConceptoInvestigadorDummy() {
		super();
	}

	@Override
	public DataSetData createInstance(DatasetTypeTest type) {
		if(DatasetTypeTest.CONCEPTO_INVESTIGADOR == type) {
			// TODO populate the fields
			return this;
		}else {
			return next(type);
		}
	}

	@Override
	public DataSetData next(DatasetTypeTest type) {
		// Last element in chain responsibility
		return null;
	}

}
