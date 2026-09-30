package it.gruppoinit.pal.gp.core.features.nodopagamenti.upgr.migrazione;

import java.util.ArrayList;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;

public class UpgrDatiVecchiaCausaleBean {

    private Integer id;
    private String descrizione;
    private String codiceversamento;
    private String parametri;
    private String javaclass;
    private List<ChiaveValoreBean<String, String>> regCausaliParametri;

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

    public String getCodiceversamento() {

	return codiceversamento;
    }

    public void setCodiceversamento(String codiceversamento) {

	this.codiceversamento = codiceversamento;
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

    public List<ChiaveValoreBean<String, String>> getRegCausaliParametri() {

	if (this.regCausaliParametri == null) {
	    this.regCausaliParametri = new ArrayList<ChiaveValoreBean<String, String>>();
	}
	return regCausaliParametri;
    }

    public void setRegCausaliParametri(List<ChiaveValoreBean<String, String>> regCausaliParametri) {

	this.regCausaliParametri = regCausaliParametri;
    }
}
