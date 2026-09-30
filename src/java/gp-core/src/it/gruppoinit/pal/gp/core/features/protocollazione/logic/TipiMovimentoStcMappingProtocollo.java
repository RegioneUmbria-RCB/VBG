package it.gruppoinit.pal.gp.core.features.protocollazione.logic;

public class TipiMovimentoStcMappingProtocollo {

    private String flusso;
    private String tipoDocumento;
    private Integer oggettoMailTipo;
    private Integer amministrazioneMittente;

    public String getFlusso() {

	return flusso;
    }

    public void setFlusso(String flusso) {

	this.flusso = flusso;
    }

    public String getTipoDocumento() {

	return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {

	this.tipoDocumento = tipoDocumento;
    }

    public Integer getOggettoMailTipo() {

	return oggettoMailTipo;
    }

    public void setOggettoMailTipo(Integer oggettoMailTipo) {

	this.oggettoMailTipo = oggettoMailTipo;
    }

    public Integer getAmministrazioneMittente() {

	return amministrazioneMittente;
    }

    public void setAmministrazioneMittente(Integer amministrazioneMittente) {

	this.amministrazioneMittente = amministrazioneMittente;
    }
}
