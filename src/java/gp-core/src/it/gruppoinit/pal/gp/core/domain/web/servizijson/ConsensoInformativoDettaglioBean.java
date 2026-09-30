package it.gruppoinit.pal.gp.core.domain.web.servizijson;

public class ConsensoInformativoDettaglioBean {

    private Integer codice;
    private String descrizione;
    private String contesto;
    private Boolean obbligatorio;
    private Integer ordine;
    private Integer versione;
    private String testo;
    private boolean flag_letto;

    public Integer getCodice() {

	return codice;
    }

    public void setCodice(Integer codice) {

	this.codice = codice;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public String getContesto() {

	return contesto;
    }

    public void setContesto(String contesto) {

	this.contesto = contesto;
    }

    public Integer getOrdine() {

	return ordine;
    }

    public void setOrdine(Integer ordine) {

	this.ordine = ordine;
    }

    public Integer getVersione() {

	return versione;
    }

    public void setVersione(Integer versione) {

	this.versione = versione;
    }

    public String getTesto() {

	return testo;
    }

    public void setTesto(String testo) {

	this.testo = testo;
    }

    public Boolean getObbligatorio() {

	return obbligatorio;
    }

    public void setObbligatorio(Boolean obbligatorio) {

	this.obbligatorio = obbligatorio;
    }

    public boolean getFlag_letto() {

	return flag_letto;
    }

    public void setFlag_letto(boolean flag_letto) {

	this.flag_letto = flag_letto;
    }
}
