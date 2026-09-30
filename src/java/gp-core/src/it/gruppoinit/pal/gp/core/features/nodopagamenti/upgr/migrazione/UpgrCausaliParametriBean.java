package it.gruppoinit.pal.gp.core.features.nodopagamenti.upgr.migrazione;

public class UpgrCausaliParametriBean {

    private String idcomune;
    private Integer id;
    private String parametri;
    private String descrizione;
    private String javaclass;

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public String getParametri() {

	return parametri;
    }

    public void setParametri(String parametri) {

	this.parametri = parametri;
    }

    public String getJavaclass() {

	return javaclass;
    }

    public void setJavaclass(String javaclass) {

	this.javaclass = javaclass;
    }
}
