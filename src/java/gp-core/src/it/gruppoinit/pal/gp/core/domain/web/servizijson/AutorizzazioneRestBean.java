package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import java.util.ArrayList;
import java.util.List;

public class AutorizzazioneRestBean {

    private Integer id;
    private String numero;
    private String data;
    private String dataChiusura;
    private String rilasciataDa;
    private String annotazioniSistema;
    private String annotazioniOperatore;
    private String dataAnzianita;
    private List<AnagraferestBean> coadiuvanti;
    private AnagraferestBean proprietario;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getNumero() {

	return numero;
    }

    public void setNumero(String numero) {

	this.numero = numero;
    }

    public String getData() {

	return data;
    }

    public void setData(String data) {

	this.data = data;
    }

    public String getDataChiusura() {

	return dataChiusura;
    }

    public void setDataChiusura(String dataChiusura) {

	this.dataChiusura = dataChiusura;
    }

    public String getRilasciataDa() {

	return rilasciataDa;
    }

    public void setRilasciataDa(String rilasciataDa) {

	this.rilasciataDa = rilasciataDa;
    }

    public String getAnnotazioniSistema() {

	return annotazioniSistema;
    }

    public void setAnnotazioniSistema(String annotazioniSistema) {

	this.annotazioniSistema = annotazioniSistema;
    }

    public String getAnnotazioniOperatore() {

	return annotazioniOperatore;
    }

    public void setAnnotazioniOperatore(String annotazioniOperatore) {

	this.annotazioniOperatore = annotazioniOperatore;
    }

    public List<AnagraferestBean> getCoadiuvanti() {

	if (coadiuvanti == null) {
	    coadiuvanti = new ArrayList<AnagraferestBean>();
	}
	return coadiuvanti;
    }

    public void setCoadiuvanti(List<AnagraferestBean> coadiuvanti) {

	this.coadiuvanti = coadiuvanti;
    }

    public AnagraferestBean getProprietario() {

	return proprietario;
    }

    public void setProprietario(AnagraferestBean proprietario) {

	this.proprietario = proprietario;
    }

    public String getDataAnzianita() {

	return dataAnzianita;
    }

    public void setDataAnzianita(String dataAnzianita) {

	this.dataAnzianita = dataAnzianita;
    }
}
