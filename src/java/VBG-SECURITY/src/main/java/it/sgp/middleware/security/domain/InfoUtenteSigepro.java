package it.sgp.middleware.security.domain;

public class InfoUtenteSigepro {

    private int codice;
    private String descrizione;
    private String password;
    private String userid;
    private ContestoEnum contesto;
    private String idcomune;
    private Integer livelloIdentificazione;

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public int getCodice() {

	return codice;
    }

    public void setCodice(int codice) {

	this.codice = codice;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public String getPassword() {

	return password;
    }

    public void setPassword(String password) {

	this.password = password;
    }

    public String getUserid() {

	return userid;
    }

    public void setUserid(String userid) {

	this.userid = userid;
    }

    public ContestoEnum getContesto() {

	return contesto;
    }

    public void setContesto(ContestoEnum contesto) {

	this.contesto = contesto;
    }

    public Integer getLivelloIdentificazione() {

	return livelloIdentificazione;
    }

    public void setLivelloIdentificazione(Integer livelloIdentificazione) {

	this.livelloIdentificazione = livelloIdentificazione;
    }
}
