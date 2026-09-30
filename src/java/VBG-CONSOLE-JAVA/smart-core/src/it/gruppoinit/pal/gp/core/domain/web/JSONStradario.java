package it.gruppoinit.pal.gp.core.domain.web;

public class JSONStradario {

    private String codice;
    private String circoscrizione;
    private String prefisso;
    private String descrizione;
    private String cap;
    private String locfraz;
    private String comune;
    private String descrizioneCompleta;

    public JSONStradario() {

	super();
    }

    public void setCodice(String codice) {

	this.codice = codice;
    }

    public String getCodice() {

	return codice;
    }

    public String getCircoscrizione() {

	return circoscrizione;
    }

    public void setCircoscrizione(String circoscrizione) {

	this.circoscrizione = circoscrizione;
    }

    public String getPrefisso() {

	return prefisso;
    }

    public void setPrefisso(String prefisso) {

	this.prefisso = prefisso;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public String getCap() {

	return cap;
    }

    public void setCap(String cap) {

	this.cap = cap;
    }

    public String getLocfraz() {

	return locfraz;
    }

    public void setLocfraz(String locfraz) {

	this.locfraz = locfraz;
    }

    public String getComune() {

	return comune;
    }

    public void setComune(String comune) {

	this.comune = comune;
    }

    public String getDescrizioneCompleta() {

	return descrizioneCompleta;
    }

    public void setDescrizioneCompleta(String descrizioneCompleta) {

	this.descrizioneCompleta = descrizioneCompleta;
    }
}
