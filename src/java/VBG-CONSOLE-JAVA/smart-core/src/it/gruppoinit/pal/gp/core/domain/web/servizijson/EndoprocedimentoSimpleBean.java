package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "procedimenti_collegati")
public class EndoprocedimentoSimpleBean {

    @XmlElement(name = "id")
    private Integer id;
    @XmlElement(name = "nome")
    private String nome;
    @XmlElement(name = "ordine")
    private int ordine;
    @XmlElement(name = "principale")
    private Boolean principale;
    @XmlElement(name = "regionale")
    private Boolean regionale;
    @XmlElement(name = "idcomune")
    private String idcomune;
    @XmlElement(name = "intervento")
    private boolean intervento;
    @XmlElement(name = "procedimenti_collegati")
    private List<EndoprocedimentoSimpleBean> procedimentiCollegati = new ArrayList<EndoprocedimentoSimpleBean>();

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getNome() {

	return nome;
    }

    public void setNome(String nome) {

	this.nome = nome;
    }

    public int getOrdine() {

	return ordine;
    }

    public void setOrdine(int ordine) {

	this.ordine = ordine;
    }

    public Boolean getPrincipale() {

	return principale;
    }

    public void setPrincipale(Boolean principale) {

	this.principale = principale;
    }

    public Boolean getRegionale() {

	return regionale;
    }

    public void setRegionale(Boolean regionale) {

	this.regionale = regionale;
    }

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public List<EndoprocedimentoSimpleBean> getProcedimentiCollegati() {

	return procedimentiCollegati;
    }

    public void setProcedimentiCollegati(List<EndoprocedimentoSimpleBean> procedimentiCollegati) {

	this.procedimentiCollegati = procedimentiCollegati;
    }

    public boolean isIntervento() {

	return intervento;
    }

    public void setIntervento(boolean intervento) {

	this.intervento = intervento;
    }
}
