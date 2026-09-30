package it.gruppoinit.pal.gp.pay.service.helper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.gruppoinit.pal.gp.pay.domain.PayRegcausaliParametri;
import it.gruppoinit.pal.gp.pay.domain.PayRegistrazioniCausali;
import it.gruppoinit.pal.gp.pay.parameters.DBParameter;
import it.gruppoinit.pal.gp.pay.parameters.IParameter;

public class InfoCausaleBean {

    private Integer id;
    private String descrizione;
    private String codiceVersamento;
    private Set<IParameter> params;

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

    public String getCodiceVersamento() {

	return codiceVersamento;
    }

    public void setCodiceVersamento(String codiceVersamento) {

	this.codiceVersamento = codiceVersamento;
    }

    public Set<IParameter> getParams() {

	if (this.params == null) {
	    this.params = new HashSet<>();
	}
	return params;
    }

    public void setParams(Set<IParameter> params) {

	this.params = params;
    }

    public static InfoCausaleBean fromPayRegistrazioniCausali(PayRegistrazioniCausali payRc) {

	InfoCausaleBean ret = new InfoCausaleBean();
	ret.setId(payRc.getId().getCodice());
	ret.setDescrizione(payRc.getDescrizione());
	ret.setCodiceVersamento(payRc.getCodiceVersamento());	
	Set<PayRegcausaliParametri> regParams = payRc.getRegParams();
	for (PayRegcausaliParametri payReg : regParams) {
	    ret.getParams().add(DBParameter.fromPayRegcausaliParametri(payReg));
	}
	return ret;
    }
}
