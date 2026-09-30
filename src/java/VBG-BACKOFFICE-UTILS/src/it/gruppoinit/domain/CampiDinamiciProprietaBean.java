package it.gruppoinit.domain;

import java.io.Serializable;

public class CampiDinamiciProprietaBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6050258572513861187L;
    private String proprieta;
    private String valore;

    public CampiDinamiciProprietaBean() {

	super();
    }

    public CampiDinamiciProprietaBean(String proprieta, String valore) {

	this();
	this.proprieta = proprieta;
	this.valore = valore;
    }

    public String getProprieta() {

	return proprieta;
    }

    public void setProprieta(String proprieta) {

	this.proprieta = proprieta;
    }

    public String getValore() {

	return valore;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }
}
