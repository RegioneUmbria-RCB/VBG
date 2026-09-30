package it.gruppoinit.pal.gp.core.domain.web.servizijson;

public class DettaglioAnagrafePGRestBean {

    private String denominazione;
    private String codice_fiscale;
    private String partita_iva;
    private String telefono;
    private String email;
    private String pec;
    private String provincia_rea;
    private String numero_rea;
    private String data_rea;
    private DettaglioAnagrafeSedeRestBean sede_legale;
    private DettaglioAnagrafeSedeRestBean corrispondenza;

    public String getDenominazione() {

	return denominazione;
    }

    public void setDenominazione(String denominazione) {

	this.denominazione = denominazione;
    }

    public String getCodice_fiscale() {

	return codice_fiscale;
    }

    public void setCodice_fiscale(String codice_fiscale) {

	this.codice_fiscale = codice_fiscale;
    }

    public String getPartita_iva() {

	return partita_iva;
    }

    public void setPartita_iva(String partita_iva) {

	this.partita_iva = partita_iva;
    }

    public String getTelefono() {

	return telefono;
    }

    public void setTelefono(String telefono) {

	this.telefono = telefono;
    }

    public String getEmail() {

	return email;
    }

    public void setEmail(String email) {

	this.email = email;
    }

    public String getPec() {

	return pec;
    }

    public void setPec(String pec) {

	this.pec = pec;
    }

    public DettaglioAnagrafeSedeRestBean getSede_legale() {

	return sede_legale;
    }

    public void setSede_legale(DettaglioAnagrafeSedeRestBean sede_legale) {

	this.sede_legale = sede_legale;
    }

    public DettaglioAnagrafeSedeRestBean getCorrispondenza() {

	return corrispondenza;
    }

    public void setCorrispondenza(DettaglioAnagrafeSedeRestBean corrispondenza) {

	this.corrispondenza = corrispondenza;
    }

    public String getProvincia_rea() {

	return provincia_rea;
    }

    public void setProvincia_rea(String provincia_rea) {

	this.provincia_rea = provincia_rea;
    }

    public String getNumero_rea() {

	return numero_rea;
    }

    public void setNumero_rea(String numero_rea) {

	this.numero_rea = numero_rea;
    }

    public String getData_rea() {

	return data_rea;
    }

    public void setData_rea(String data_rea) {

	this.data_rea = data_rea;
    }
}
