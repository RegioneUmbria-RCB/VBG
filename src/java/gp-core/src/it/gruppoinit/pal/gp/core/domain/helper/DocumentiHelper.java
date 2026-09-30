package it.gruppoinit.pal.gp.core.domain.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.gruppoinit.pal.gp.core.dao.helper.SituazioneAllegato;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;

/**
 * <pre>
 * La classe conterrà una serie di liste che raggruppao i documenti 
 * di una specifica entità (Es istanza, endoproc, movimenti.)
 * La struttura delle liste sarà del tipo :
 *    <b>List<ChiaveValoreBean<String, List<Movimentiallegati>>><br>
 *    Dove il valore 
 *    	1. String 			: sarà una descrizione della lista , esempio nome del movimento in questo caso
 *      2. List<Movimentiallegati>	: sarà la ista di oggetti specificati nell'argomento della struttura list
 *    
 * &#64;author gianpaolot
 * </pre>
 */
public class DocumentiHelper {

    // Liste utilizzate per recuperare tutti i tipi di allegati da presentare nella protocollazione,nell'invio mail, nell'invio stc
    private List<ChiaveValoreBean<String, List<IstanzeallegatiDTO>>> documentiEndoprocedimentiList = new ArrayList<ChiaveValoreBean<String, List<IstanzeallegatiDTO>>>();
    private List<ChiaveValoreBean<String, List<DocumentiistanzaDTO>>> documentiIstanzaList = new ArrayList<ChiaveValoreBean<String, List<DocumentiistanzaDTO>>>();
    private List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> documentiMovimentoList = new ArrayList<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>>();
    private List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> documentiAltriMovimentiList = new ArrayList<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>>();
    private List<ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>> documentiAnagrafeList = new ArrayList<ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>>();
    private List<ChiaveValoreBean<String, List<IstanzeprocureDTO>>> istanzeprocureList = new ArrayList<ChiaveValoreBean<String, List<IstanzeprocureDTO>>>();
    private List<ChiaveValoreBean<String, List<CdsattiDTO>>> cdsattiList = new ArrayList<ChiaveValoreBean<String, List<CdsattiDTO>>>();
    private Map<SituazioneAllegato, Integer> situazioniAllegati = new HashMap<SituazioneAllegato, Integer>();
    private String guidZipLogico;

    public List<ChiaveValoreBean<String, List<CdsattiDTO>>> getCdsattiList() {

	if (this.cdsattiList == null) {
	    this.cdsattiList = new ArrayList<ChiaveValoreBean<String, List<CdsattiDTO>>>();
	}
	return cdsattiList;
    }

    public void setCdsattiList(List<ChiaveValoreBean<String, List<CdsattiDTO>>> cdsattiList) {

	this.cdsattiList = cdsattiList;
    }

    public List<ChiaveValoreBean<String, List<IstanzeallegatiDTO>>> getDocumentiEndoprocedimentiList() {

	if (this.documentiEndoprocedimentiList == null) {
	    this.documentiEndoprocedimentiList = new ArrayList<ChiaveValoreBean<String, List<IstanzeallegatiDTO>>>();
	}
	return documentiEndoprocedimentiList;
    }

    public void setDocumentiEndoprocedimentiList(List<ChiaveValoreBean<String, List<IstanzeallegatiDTO>>> documentiEndoprocedimentiList) {

	this.documentiEndoprocedimentiList = documentiEndoprocedimentiList;
    }

    public List<ChiaveValoreBean<String, List<DocumentiistanzaDTO>>> getDocumentiIstanzaList() {

	if (this.documentiIstanzaList == null) {
	    this.documentiIstanzaList = new ArrayList<ChiaveValoreBean<String, List<DocumentiistanzaDTO>>>();
	}
	return documentiIstanzaList;
    }

    public void setDocumentiIstanzaList(List<ChiaveValoreBean<String, List<DocumentiistanzaDTO>>> documentiIstanzaList) {

	this.documentiIstanzaList = documentiIstanzaList;
    }

    public List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> getDocumentiMovimentoList() {

	if (this.documentiMovimentoList == null) {
	    this.documentiMovimentoList = new ArrayList<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>>();
	}
	return documentiMovimentoList;
    }

    public void setDocumentiMovimentoList(List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> documentiMovimentoList) {

	this.documentiMovimentoList = documentiMovimentoList;
    }

    public List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> getDocumentiAltriMovimentiList() {

	if (this.documentiAltriMovimentiList == null) {
	    this.documentiAltriMovimentiList = new ArrayList<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>>();
	}
	return documentiAltriMovimentiList;
    }

    public void setDocumentiAltriMovimentiList(List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> documentiAltriMovimentiList) {

	this.documentiAltriMovimentiList = documentiAltriMovimentiList;
    }

    public List<ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>> getDocumentiAnagrafeList() {

	if (this.documentiAnagrafeList == null) {
	    this.documentiAnagrafeList = new ArrayList<ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>>();
	}
	return documentiAnagrafeList;
    }

    public void setDocumentiAnagrafeList(List<ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>> documentiAnagrafeList) {

	this.documentiAnagrafeList = documentiAnagrafeList;
    }

    public List<ChiaveValoreBean<String, List<IstanzeprocureDTO>>> getIstanzeprocureList() {

	if (this.istanzeprocureList == null) {
	    this.istanzeprocureList = new ArrayList<ChiaveValoreBean<String, List<IstanzeprocureDTO>>>();
	}
	return istanzeprocureList;
    }

    public void setIstanzeprocureList(List<ChiaveValoreBean<String, List<IstanzeprocureDTO>>> istanzeprocureList) {

	this.istanzeprocureList = istanzeprocureList;
    }

    public Map<SituazioneAllegato, Integer> getSituazioniAllegati() {

	return situazioniAllegati;
    }

    public void setSituazioniAllegati(Map<SituazioneAllegato, Integer> situazioniAllegati) {

	this.situazioniAllegati = situazioniAllegati;
    }

    public String getGuidZipLogico() {

	return guidZipLogico;
    }

    public void setGuidZipLogico(String guidZipLogico) {

	this.guidZipLogico = guidZipLogico;
    }
}
