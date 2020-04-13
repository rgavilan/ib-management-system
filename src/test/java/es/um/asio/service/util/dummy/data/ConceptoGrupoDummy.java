package es.um.asio.service.util.dummy.data;

import es.um.asio.domain.DataSetData;
import es.um.asio.domain.gruposInvestigacion.ConceptoGrupo;
import es.um.asio.service.util.DatasetTypeTest;

public class ConceptoGrupoDummy extends ConceptoGrupo implements DummyData {

	private static final long serialVersionUID = -3887789868183420670L;

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
