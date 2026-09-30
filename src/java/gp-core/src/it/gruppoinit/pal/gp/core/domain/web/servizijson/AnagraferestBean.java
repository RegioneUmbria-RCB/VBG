package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import org.apache.commons.lang.StringUtils;

public class AnagraferestBean {

    private Integer id;
    private String ragionesociale;
    private String indirizzo;
    private String codiceFiscale;
    private String partitaIva;
    private String rea;
    private String dataiscrrea;
    private String telefono;
    private String anagrafeTrovataInEnum;
    private String dataInizioAttivita;
    private String email;
    private String descrizioneCompleta;
    private String dataRegDitte;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getRagionesociale() {

	return ragionesociale;
    }

    public void setRagionesociale(String ragionesociale) {

	this.ragionesociale = ragionesociale;
    }

    public String getIndirizzo() {

	return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {

	this.indirizzo = indirizzo;
    }

    public String getCodiceFiscale() {

	return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {

	this.codiceFiscale = codiceFiscale;
    }

    public String getPartitaIva() {

	return partitaIva;
    }

    public void setPartitaIva(String partitaIva) {

	this.partitaIva = partitaIva;
    }

    public String getRea() {

	return rea;
    }

    public void setRea(String rea) {

	this.rea = rea;
    }

    public String getTelefono() {

	return telefono;
    }

    public void setTelefono(String telefono) {

	this.telefono = telefono;
    }

    public String getDataiscrrea() {

	return dataiscrrea;
    }

    public void setDataiscrrea(String dataiscrrea) {

	this.dataiscrrea = dataiscrrea;
    }

    public String getAnagrafeTrovataInEnum() {

	return anagrafeTrovataInEnum;
    }

    public void setAnagrafeTrovataInEnum(String anagrafeTrovataInEnum) {

	this.anagrafeTrovataInEnum = anagrafeTrovataInEnum;
    }

    public String getDataInizioAttivita() {

	return dataInizioAttivita;
    }

    public void setDataInizioAttivita(String dataInizioAttivita) {

	this.dataInizioAttivita = dataInizioAttivita;
    }

    public String getEmail() {

	return email;
    }

    public void setEmail(String email) {

	this.email = email;
    }

    public String getDataRegDitte() {

	return dataRegDitte;
    }

    public void setDataRegDitte(String dataRegDitte) {

	this.dataRegDitte = dataRegDitte;
    }

    public String getDescrizioneCompleta() {

	String s = getRagionesociale();
	if (StringUtils.isNotBlank(getPartitaIva())) {
	    s += " P.Iva: " + getPartitaIva();
	}
	if (StringUtils.isNotBlank(getCodiceFiscale())) {
	    s += " CF: " + getCodiceFiscale();
	}
	return s;
    }

    public void setDescrizioneCompleta(String descrizioneCompleta) {

	this.descrizioneCompleta = descrizioneCompleta;
    }
}
