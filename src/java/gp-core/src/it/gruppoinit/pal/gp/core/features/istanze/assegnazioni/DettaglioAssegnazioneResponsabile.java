package it.gruppoinit.pal.gp.core.features.istanze.assegnazioni;

public class DettaglioAssegnazioneResponsabile {

    private Integer idTestataGruppoAssegnazione;
    private Integer codiceResponsabile;
    private Integer codiceIstanza;
    private String numeroIstanza;

    public Integer getIdTestataGruppoAssegnazione() {

	return idTestataGruppoAssegnazione;
    }

    public void setIdTestataGruppoAssegnazione(Integer idTestataGruppoAssegnazione) {

	this.idTestataGruppoAssegnazione = idTestataGruppoAssegnazione;
    }

    public Integer getCodiceResponsabile() {

	return codiceResponsabile;
    }

    public void setCodiceResponsabile(Integer codiceResponsabile) {

	this.codiceResponsabile = codiceResponsabile;
    }

    public Integer getCodiceIstanza() {

	return codiceIstanza;
    }

    public void setCodiceIstanza(Integer codiceIstanza) {

	this.codiceIstanza = codiceIstanza;
    }

    public String getNumeroIstanza() {

	return numeroIstanza;
    }

    public void setNumeroIstanza(String numeroIstanza) {

	this.numeroIstanza = numeroIstanza;
    }
}
