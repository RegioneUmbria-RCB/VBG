package it.gruppoinit.faldonetelematico.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "valori_campo", propOrder = { "valoreCampo" })
public class ValoriCampo {

    @XmlElement(name = "valore_campo")
    List<ValoreCampo> valoreCampo;

    public List<ValoreCampo> getValoreCampo() {

	return valoreCampo;
    }

    public void setValoreCampo(List<ValoreCampo> valoreCampo) {

	this.valoreCampo = valoreCampo;
    }
}