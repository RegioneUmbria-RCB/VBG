package it.gruppoinit.pal.gp.core.features.autorizzazioni.subentri;

public class CheckSubentroRequest {

    private Integer codiceIstanzaSubentro;
    private Integer idAutConc;
    private DatiCausaliSubentroRequest datiCausali;

    public CheckSubentroRequest(Integer codiceIstanzaSubentro, Integer idAutConc, DatiCausaliSubentroRequest datiCausali) {

	this.codiceIstanzaSubentro = codiceIstanzaSubentro;
	this.idAutConc = idAutConc;
	this.datiCausali = datiCausali;
    }

    public Integer getCodiceIstanzaSubentro() {

	return codiceIstanzaSubentro;
    }

    public Integer getIdAutConc() {

	return idAutConc;
    }

    public DatiCausaliSubentroRequest getDatiCausali() {

	return datiCausali;
    }
}
