package it.gruppoinit.pal.gp.core.domain.web.servizijson;

public class DettaglioAnagrafeRestBean {

    private Integer id;
    private DettaglioAnagrafePFRestBean persona_fisica;
    private DettaglioAnagrafePGRestBean persona_giuridica;
    private boolean mail_verificata;
    private boolean procedura_verifica_in_corso;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public DettaglioAnagrafePFRestBean getPersona_fisica() {

	return persona_fisica;
    }

    public void setPersona_fisica(DettaglioAnagrafePFRestBean persona_fisica) {

	this.persona_fisica = persona_fisica;
    }

    public DettaglioAnagrafePGRestBean getPersona_giuridica() {

	return persona_giuridica;
    }

    public void setPersona_giuridica(DettaglioAnagrafePGRestBean persona_giuridica) {

	this.persona_giuridica = persona_giuridica;
    }

    public boolean getMail_verificata() {

	return mail_verificata;
    }

    public void setMail_verificata(boolean mail_verificata) {

	this.mail_verificata = mail_verificata;
    }

    public boolean getProcedura_verifica_in_corso() {

	return procedura_verifica_in_corso;
    }

    public void setProcedura_verifica_in_corso(boolean procedura_verifica_in_corso) {

	this.procedura_verifica_in_corso = procedura_verifica_in_corso;
    }
}
