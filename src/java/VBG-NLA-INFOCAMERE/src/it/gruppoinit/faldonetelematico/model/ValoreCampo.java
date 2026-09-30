package it.gruppoinit.faldonetelematico.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "nome", "valore", "subValoreCampo" })
@XmlRootElement(name = "valore_campo")
public class ValoreCampo {

    @XmlAttribute(name = "nome")
    String nome;
    @XmlAttribute(name = "valore")
    String valore;
    @XmlElement(name = "valore_campo")
    List<ValoreCampo> subValoreCampo;

    public void setNome(String nome) {

	this.nome = nome;
    }

    public String getNome() {

	return nome;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }

    public String getValore() {

	return valore;
    }

    public List<ValoreCampo> getSubValoreCampo() {

	return subValoreCampo;
    }

    public void setSubValoreCampo(List<ValoreCampo> subValoreCampo) {

	this.subValoreCampo = subValoreCampo;
    }
}