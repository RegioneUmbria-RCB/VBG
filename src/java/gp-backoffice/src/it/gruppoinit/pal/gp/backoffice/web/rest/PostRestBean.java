package it.gruppoinit.pal.gp.backoffice.web.rest;

import java.util.ArrayList;
import java.util.List;

public class PostRestBean {

    private Integer idGiornata;
    private Integer idPosteggio;
    private Integer idAutorizzazione;
    private Integer idAnagrafe;
    private String idCategoria;
    private List<Long> idPosteggi;

    public Integer getIdGiornata() {

	return idGiornata;
    }

    public void setIdGiornata(Integer idGiornata) {

	this.idGiornata = idGiornata;
    }

    public Integer getIdPosteggio() {

	return idPosteggio;
    }

    public void setIdPosteggio(Integer idPosteggio) {

	this.idPosteggio = idPosteggio;
    }

    public Integer getIdAutorizzazione() {

	return idAutorizzazione;
    }

    public void setIdAutorizzazione(Integer idAutorizzazione) {

	this.idAutorizzazione = idAutorizzazione;
    }

    public Integer getIdAnagrafe() {

	return idAnagrafe;
    }

    public void setIdAnagrafe(Integer idAnagrafe) {

	this.idAnagrafe = idAnagrafe;
    }

    public String getIdCategoria() {

	return idCategoria;
    }

    public void setIdCategoria(String idCategoria) {

	this.idCategoria = idCategoria;
    }

    public List<Long> getIdPosteggi() {

	if (idPosteggi == null) {
	    idPosteggi = new ArrayList<Long>();
	}
	return idPosteggi;
    }

    public void setIdPosteggi(List<Long> idPosteggi) {

	this.idPosteggi = idPosteggi;
    }
}
