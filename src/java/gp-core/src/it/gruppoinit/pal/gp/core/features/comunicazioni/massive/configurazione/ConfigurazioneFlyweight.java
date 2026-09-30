package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;

public class ConfigurazioneFlyweight {

    private String descrizione;
    private List<AllegatoComunicazione> allegatiFissi = new ArrayList<AllegatoComunicazione>();
    private List<LetteraComunicazione> lettereComunicazione = new ArrayList<LetteraComunicazione>();
    private ConfigurazioneMail configurazioneMail;
    private List<ParametroConfigurazioneComunicazione> parametri = new ArrayList<ParametroConfigurazioneComunicazione>();
    private List<Integer> soggettiFirmatari = new ArrayList<Integer>();
    private ParametriProtocollazione parametriProtocollazione;

    public List<AllegatoComunicazione> getAllegatiFissi() {

	return allegatiFissi;
    }

    public List<Integer> getSoggettiFirmatari() {

	return soggettiFirmatari;
    }

    public List<LetteraComunicazione> getLettereComunicazione() {

	return lettereComunicazione;
    }

    public ConfigurazioneMail getConfigurazioneMail() {

	return configurazioneMail;
    }

    public List<ParametroConfigurazioneComunicazione> getParametri() {

	return parametri;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void addParametro(String chiave, String valore) {

	ParametroConfigurazioneComunicazione par = new ParametroConfigurazioneComunicazione(chiave, valore);
	this.parametri.add(par);
    }

    public void addParametro(String chiave, int valore) {

	this.addParametro(chiave, String.valueOf(valore));
    }

    public void addParametro(String chiave, Boolean valore) {

	String valoreDB = BooleanUtils.isTrue(valore) ? "1" : "0"; // PER ORACLE JDBC QUANDO SI RECUPER UN VALORE CONSIDERATO BOOLEANO 
								   // DOVREBBE ESSER SALVATO COME 0 (FALSE) O 1 (TRUE) in quanto in qualche versione JDBC per Oracle se venisse salvato false o true
								   // viene rilanciata una eccezione di "SQL Error: 17059, SQLState: 99999 Fail to convert to internal representation"
								   // in quanto il driver specifico non riesce a converire dal valore string "false" al booleano false con 0 invece ci riesce
	this.addParametro(chiave, String.valueOf(valoreDB));
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public void setConfigurazioneMail(ConfigurazioneMail cfgMail) {

	this.configurazioneMail = new ConfigurazioneMail(cfgMail.getSenderAccount(), cfgMail.getIdMailTipo());
    }

    public ParametriProtocollazione getParametriProtocollazione() {

	return parametriProtocollazione;
    }

    public void setParametriProtocollazione(ParametriProtocollazione p) {

	if (p != null) {
	    this.parametriProtocollazione = new ParametriProtocollazione(p.getCodiceMailtipo(), p.getParametriPerEnte());
	}
    }
}
