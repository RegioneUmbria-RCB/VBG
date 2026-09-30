package it.gruppoinit.pal.gp.pay.service.helper;

import javax.xml.bind.annotation.XmlElement;

public class InfoParameterBean {

    @XmlElement(name = "nome_parametro")
    private String nomeParametro;
    @XmlElement(name = "valore")
    private String valore;
    @XmlElement(name = "descrizione")
    private String descrizione;
    @XmlElement(name = "help")
    private String help;

    public String getNomeParametro() {

	return nomeParametro;
    }

    public void setNomeParametro(String nomeParametro) {

	this.nomeParametro = nomeParametro;
    }

    public String getValore() {

	return valore;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public String getHelp() {

	return help;
    }

    public void setHelp(String help) {

	this.help = help;
    }

    public InfoParameterBean() {

	super();
    }

    public InfoParameterBean(String nomeParametro, String valore, String descrizione, String help) {

	this();
	this.nomeParametro = nomeParametro;
	this.valore = valore;
	this.descrizione = descrizione;
	this.help = help;
    }
}
