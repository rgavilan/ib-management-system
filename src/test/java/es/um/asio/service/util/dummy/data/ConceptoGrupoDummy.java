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

	public static String getRDF() {
		return "<rdf:RDF\r\n" + "    xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\r\n"
				+ "    xmlns:j.0=\"http://example.org/\"\r\n"
				+ "    xmlns:j.1=\"http://www.w3.org/2001/asio-rdf/3.0#\">\r\n"
				+ "  <j.0:ConceptoGrupoDummy rdf:about=\"http://example.org/E0A6-01/5/DESCRIPTORES/LENGUAJES+PLASTICOS\">\r\n"
				+ "    <j.1:texto>LENGUAJES PLASTICOS</j.1:texto>\r\n"
				+ "    <j.1:codTipoConcepto>DESCRIPTORES</j.1:codTipoConcepto>\r\n"
				+ "    <j.1:numero>5</j.1:numero>\r\n"
				+ "    <j.1:idGrupoInvestigacion>E0A6-01</j.1:idGrupoInvestigacion>\r\n"
				+ "  </j.0:ConceptoGrupoDummy>\r\n" + "</rdf:RDF>";
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
