package it.gruppoinit.pal.gp.core.domain.web.servizijson;

public class DettaglioAnagrafePFRestBean {

    private String nome;
    private String cognome;
    private String codice_fiscale;
    private String telefono;
    private String email;
    private String pec;
    private String comune_nascita;
    private String data_nascita;
    private DettaglioAnagrafeSedeRestBean residenza;
    private DettaglioAnagrafeSedeRestBean corrispondenza;

    public String getNome() {

	return nome;
    }

    public void setNome(String nome) {

	this.nome = nome;
    }

    public String getCognome() {

	return cognome;
    }

    public void setCognome(String cognome) {

	this.cognome = cognome;
    }

    public String getCodice_fiscale() {

	return codice_fiscale;
    }

    public void setCodice_fiscale(String codice_fiscale) {

	this.codice_fiscale = codice_fiscale;
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

    public String getComune_nascita() {

	return comune_nascita;
    }

    public void setComune_nascita(String comune_nascita) {

	this.comune_nascita = comune_nascita;
    }

    public String getData_nascita() {

	return data_nascita;
    }

    public void setData_nascita(String data_nascita) {

	this.data_nascita = data_nascita;
    }

    public DettaglioAnagrafeSedeRestBean getResidenza() {

	return residenza;
    }

    public void setResidenza(DettaglioAnagrafeSedeRestBean residenza) {

	this.residenza = residenza;
    }

    public DettaglioAnagrafeSedeRestBean getCorrispondenza() {

	return corrispondenza;
    }

    public void setCorrispondenza(DettaglioAnagrafeSedeRestBean corrispondenza) {

	this.corrispondenza = corrispondenza;
    }
}
