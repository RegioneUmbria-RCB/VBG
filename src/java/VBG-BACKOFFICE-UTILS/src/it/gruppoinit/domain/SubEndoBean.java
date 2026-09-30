package it.gruppoinit.domain;

import java.io.Serializable;

public class SubEndoBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 612109245037676502L;
    private Integer codiceinventario_d;
    private Integer flag_pubblica;
    private Integer flag_necessario;
    private String codiceComune;

    public SubEndoBean() {

	super();
    }

    public SubEndoBean(Integer codiceinventario_d, Integer flag_pubblica, Integer flag_necessario, String codiceComune) {

	this();
	this.codiceinventario_d = codiceinventario_d;
	this.flag_pubblica = flag_pubblica;
	this.flag_necessario = flag_necessario;
	this.codiceComune = codiceComune;
    }

    public Integer getCodiceinventario_d() {

	return codiceinventario_d;
    }

    public void setCodiceinventario_d(Integer codiceinventario_d) {

	this.codiceinventario_d = codiceinventario_d;
    }

    public Integer getFlag_pubblica() {

	return flag_pubblica;
    }

    public void setFlag_pubblica(Integer flag_pubblica) {

	this.flag_pubblica = flag_pubblica;
    }

    public Integer getFlag_necessario() {

	return flag_necessario;
    }

    public void setFlag_necessario(Integer flag_necessario) {

	this.flag_necessario = flag_necessario;
    }

    public String getCodiceComune() {

	return codiceComune;
    }

    public void setCodiceComune(String codiceComune) {

	this.codiceComune = codiceComune;
    }
}
