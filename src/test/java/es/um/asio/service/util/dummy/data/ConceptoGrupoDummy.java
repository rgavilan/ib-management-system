package es.um.asio.service.util.dummy.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import es.um.asio.domain.DataSetData;
import es.um.asio.domain.gruposInvestigacion.ConceptoGrupo;
import es.um.asio.service.util.test.DatasetTypeTest;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ConceptoGrupoDummy extends ConceptoGrupo implements DummyData {
	
	// vbles
    private String idGrupoInvestigacion;
    private long numero;
    private String codTipoConcepto;
    private String texto;
	
	public ConceptoGrupoDummy() {
		super();
	}

	@Override
	public DataSetData createInstance(DatasetTypeTest type) {
		if(DatasetTypeTest.CONCEPTO_GRUPO == type) {
			ConceptoGrupoDummy data = new ConceptoGrupoDummy();
			data.setCodTipoConcepto("DESCRIPTORES");
			data.setNumero(5L);
			data.setIdGrupoInvestigacion("E0A6-01");
			data.setTexto("LENGUAJES PLASTICOS");
			return data;
		}else {
			return next(type);
		}
	}

	@Override
	public DataSetData next(DatasetTypeTest type) {
		// next element in responsibility chain
		return null;
	}
}
