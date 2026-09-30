package it.gruppoinit.nlapec.daticert;

import java.util.ArrayList;
import java.util.Date;

public class PecDatiCertDati {

    private String gestoreEmittente;
    private Date data;
    private String identificativo;
    private String msgid;
    private String ricevuta;
    private ArrayList<PecDest> consegna = new ArrayList<PecDest>();
    private ArrayList<PecDest> ricezione = new ArrayList<PecDest>();
    private String erroreEsteso;

    public String getGestoreEmittente() {

	return gestoreEmittente;
    }

    public void setGestoreEmittente(String gestoreEmittente) {

	this.gestoreEmittente = gestoreEmittente;
    }

    public Date getData() {

	return data;
    }

    public void setData(Date data) {

	this.data = data;
    }

    public String getIdentificativo() {

	return identificativo;
    }

    public void setIdentificativo(String identificativo) {

	this.identificativo = identificativo;
    }

    public String getMsgid() {

	return msgid;
    }

    public void setMsgid(String msgid) {

	this.msgid = msgid;
    }

    public String getRicevuta() {

	return ricevuta;
    }

    public void setRicevuta(String ricevuta) {

	this.ricevuta = ricevuta;
    }

    public ArrayList<PecDest> getConsegna() {

	return consegna;
    }

    public void setConsegna(ArrayList<PecDest> consegna) {

	this.consegna = consegna;
    }

    public void addConsegna(PecDest d) {

	consegna.add(d);
    }

    public ArrayList<PecDest> getRicezione() {

	return ricezione;
    }

    public void setRicezione(ArrayList<PecDest> ricezione) {

	this.ricezione = ricezione;
    }

    public void addRicezione(PecDest d) {

	ricezione.add(d);
    }

    public String getErroreEsteso() {

	return erroreEsteso;
    }

    public void setErroreEsteso(String erroreEsteso) {

	this.erroreEsteso = erroreEsteso;
    }
}
