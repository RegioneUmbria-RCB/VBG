package it.gruppoinit.pal.gp.core.features.istanze.datidinamici.elaborazionemassiva.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Statiistanza;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;
import it.gruppoinit.pal.gp.core.features.istanze.datidinamici.elaborazionemassiva.CreaTestataRequest;
import it.gruppoinit.pal.gp.core.service.StatiistanzaService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class ElaborazioniMassiveCreateCommand {

    private String descrizione;
    private IdentificativoDescrizioneBean dyn2Modellit;
    private IdentificativoDescrizioneBean tipologiaregistro;
    private CodiceDescrizioneBean statoistanza;
    private List<SchedaDinamicaModel> schedaDinamicaModels = new ArrayList<SchedaDinamicaModel>(10);
    private List<Integer> registri = new ArrayList<Integer>();
    private List<Integer> interventi = new ArrayList<Integer>();
    private List<CodiceDescrizioneBean> statiIstanzas = null;

    public ElaborazioniMassiveCreateCommand(StatiistanzaService statiistanzaService) {

	this.descrizione = "Elaborazione schede istanza del " + Utilities.getToday(false);
	List<Statiistanza> listaStati = statiistanzaService.findBySoftware(ORMHelper.getSoftware());
	this.statiIstanzas = new ArrayList<CodiceDescrizioneBean>();
	for (Statiistanza s : listaStati) {
	    CodiceDescrizioneBean cdb = new CodiceDescrizioneBean(s.getId().getCodicestato(), s.getStato());
	    statiIstanzas.add(cdb);
	}
	this.dyn2Modellit = new IdentificativoDescrizioneBean();
	this.tipologiaregistro = new IdentificativoDescrizioneBean();
	this.statoistanza = new CodiceDescrizioneBean();
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public IdentificativoDescrizioneBean getDyn2Modellit() {

	return dyn2Modellit;
    }

    public void setDyn2Modellit(IdentificativoDescrizioneBean dyn2Modellit) {

	this.dyn2Modellit = dyn2Modellit;
    }

    public IdentificativoDescrizioneBean getTipologiaregistro() {

	return tipologiaregistro;
    }

    public void setTipologiaregistro(IdentificativoDescrizioneBean tipologiaregistro) {

	this.tipologiaregistro = tipologiaregistro;
    }

    public CodiceDescrizioneBean getStatoistanza() {

	return statoistanza;
    }

    public void setStatoistanza(CodiceDescrizioneBean statoistanza) {

	this.statoistanza = statoistanza;
    }

    public List<Integer> getRegistri() {

	return registri;
    }

    public List<SchedaDinamicaModel> getSchedaDinamicaModels() {

	return schedaDinamicaModels;
    }

    public void setSchedaDinamicaModels(List<SchedaDinamicaModel> schedaDinamicaModels) {

	this.schedaDinamicaModels = schedaDinamicaModels;
    }

    public void setRegistri(List<Integer> registri) {

	this.registri = registri;
    }

    public List<Integer> getInterventi() {

	return interventi;
    }

    public void setInterventi(List<Integer> interventi) {

	this.interventi = interventi;
    }

    public void setStatiIstanzas(List<CodiceDescrizioneBean> statiIstanzas) {

	this.statiIstanzas = statiIstanzas;
    }

    public List<CodiceDescrizioneBean> getStatiIstanzas() {

	return statiIstanzas;
    }

    public CreaTestataRequest createRequest() {

	Set<Integer> interventis = new TreeSet<Integer>();
	for (Integer i : getInterventi()) {
	    interventis.add(i);
	}
	Set<SchedaDinamicaModel> sch = new TreeSet<SchedaDinamicaModel>(new SchedaDinamicaModelComparator());
	for (SchedaDinamicaModel i : getSchedaDinamicaModels()) {
	    sch.add(i);
	}
	Set<Integer> reg = new TreeSet<Integer>();
	for (Integer i : getRegistri()) {
	    reg.add(i);
	}
	CreaTestataRequest r = new CreaTestataRequest(interventis, this.descrizione, this.statoistanza.getCodice(), sch, reg);
	return r;
    }

    public String valida() {

	String err = "";
	if ((this.interventi == null || this.interventi.size() == 0)) {
	    err = "Non sono stati specificati interventi\n";
	}
	if ((this.schedaDinamicaModels == null || this.schedaDinamicaModels.size() == 0)) {
	    err += "Non sono stati specificate schede dinamiche (modelli)\n";
	}
	if (StringUtils.isBlank(this.descrizione)) {
	    err += "Descrizione Obbligatoria";
	}
	if (this.statoistanza == null || StringUtils.isBlank(this.statoistanza.getCodice())) {
	    err += "E' necessario impostare uno stato istanza\n";
	}
	return err;
    }
}
