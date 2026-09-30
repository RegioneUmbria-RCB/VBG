package it.gruppoinit.pal.gp.core.features.nodopagamenti.rest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "parametri")
public class ParametriType {

    @XmlElement(name = "nome_parametro")
    private String nomeParametro;
    @XmlElement(name = "valore")
    private String valore;
    @XmlElement(name = "descrizione")
    private String descrizione;
    @XmlElement(name = "help")
    private String help;

    public ParametriType() {

    }

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
}
