package it.gruppoinit.pal.gp.core.domain.helper;

import java.util.ArrayList;
import java.util.List;

public class AllineamentoStradarioHelper {

    private String codicecomune;
    private String comune;
    private Integer numAggiornati;
    private Integer numAggiunti;
    private Boolean isErrore;
    private List<String> errori = new ArrayList<String>();

    public String getCodicecomune() {

	return codicecomune;
    }

    public void setCodicecomune(String codicecomune) {

	this.codicecomune = codicecomune;
    }

    public String getComune() {

	return comune;
    }

    public void setComune(String comune) {

	this.comune = comune;
    }

    public Integer getNumAggiornati() {

	return numAggiornati;
    }

    public void setNumAggiornati(Integer numAggiornati) {

	this.numAggiornati = numAggiornati;
    }

    public Integer getNumAggiunti() {

	return numAggiunti;
    }

    public void setNumAggiunti(Integer numAggiunti) {

	this.numAggiunti = numAggiunti;
    }

    public Boolean getIsErrore() {

	return isErrore;
    }

    public void setIsErrore(Boolean isErrore) {

	this.isErrore = isErrore;
    }

    public List<String> getErrori() {

	return errori;
    }

    public void setErrori(List<String> errori) {

	this.errori = errori;
    }
}
