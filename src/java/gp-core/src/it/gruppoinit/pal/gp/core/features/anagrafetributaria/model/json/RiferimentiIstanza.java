package it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.json;

import javax.xml.bind.annotation.XmlElement;

public class RiferimentiIstanza {

    @XmlElement(name = "codice")
    private String codice;
    @XmlElement(name = "numero")
    private String numero;
    @XmlElement(name = "richiedente")
    private String richiedente;

    public String getCodice() {

	return codice;
    }

    public void setCodice(String codice) {

	this.codice = codice;
    }

    public String getNumero() {

	return numero;
    }

    public void setNumero(String numero) {

	this.numero = numero;
    }

    public String getRichiedente() {

	return richiedente;
    }

    public void setRichiedente(String richiedente) {

	this.richiedente = richiedente;
    }
}
