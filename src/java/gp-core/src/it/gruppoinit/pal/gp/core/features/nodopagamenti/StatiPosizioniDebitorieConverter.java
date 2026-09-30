package it.gruppoinit.pal.gp.core.features.nodopagamenti;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.paevolution.ws.pagamenti_types.StatoPagamentoType;

import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;

public class StatiPosizioniDebitorieConverter {

    public static final String IN_CORSO = "In corso";
    public static final String CONCLUSA_POSITIVAMENTE = "Conclusa positivamente";
    public static final String CONCLUSA_NEGATIVAMENTE = "Conclusa negativamente";

    public IdentificativoDescrizioneBean convertStato(String stato) {

	if (!StringUtils.defaultString(stato).trim().isEmpty()) {
	    if (stato.equalsIgnoreCase(StatoPagamentoType.ACQUISITO.name())
		    || stato.equalsIgnoreCase(StatoPagamentoType.ANNULLAMENTO_RICHIESTO.name())
		    || stato.equalsIgnoreCase(StatoPagamentoType.ATTIVATO_IN_PSP.name())
		    || stato.equalsIgnoreCase(StatoPagamentoType.PAGATO_OFFLINE_DA_ANNULLARE.name())
		    || stato.equalsIgnoreCase(StatoPagamentoType.TRASMESSO_A_PSP.name())) {
		return new IdentificativoDescrizioneBean(0, IN_CORSO);
	    } else if (stato.equalsIgnoreCase(StatoPagamentoType.PAGATO_OFFLINE_ANNULLATO.name())
		    || stato.equalsIgnoreCase(StatoPagamentoType.RENDICONTATO_DA_IC.name())
		    || stato.equalsIgnoreCase(StatoPagamentoType.NOTIFICATO_DA_PSP.name())) {
		return new IdentificativoDescrizioneBean(200, CONCLUSA_POSITIVAMENTE);
	    } else if (stato.equalsIgnoreCase(StatoPagamentoType.NON_ACQUISITO.name()) // 
		    || stato.equalsIgnoreCase(StatoPagamentoType.CON_ERRORE.name()) // 
		    || stato.equalsIgnoreCase(StatoPagamentoType.ANNULLATO.name())) {
		return new IdentificativoDescrizioneBean(500, CONCLUSA_NEGATIVAMENTE);
	    }
	}
	return new IdentificativoDescrizioneBean(-1, "Non definito " + stato); // non definito
    }

    public StatoPagamentoType[] getStatiPosizioniInCorso() {

	StatoPagamentoType[] ret = new StatoPagamentoType[5];
	ret[0] = StatoPagamentoType.ACQUISITO;
	ret[1] = StatoPagamentoType.ANNULLAMENTO_RICHIESTO;
	ret[2] = StatoPagamentoType.ATTIVATO_IN_PSP;
	ret[3] = StatoPagamentoType.PAGATO_OFFLINE_DA_ANNULLARE;
	ret[4] = StatoPagamentoType.TRASMESSO_A_PSP;
	return ret;
    }

    public boolean isStatoInCorso(String stato) {

	for (StatoPagamentoType s : this.getStatiPosizioniInCorso()) {
	    if (s.name().equalsIgnoreCase(stato)) {
		return true;
	    }
	}
	return false;
    }

    public StatoPagamentoType[] getStatiPosizioniChiusePositivamente() {

	StatoPagamentoType[] ret = new StatoPagamentoType[3];
	ret[0] = StatoPagamentoType.PAGATO_OFFLINE_ANNULLATO;
	ret[1] = StatoPagamentoType.RENDICONTATO_DA_IC;
	ret[2] = StatoPagamentoType.NOTIFICATO_DA_PSP;
	return ret;
    }

    public boolean isStatoChiusoPositivamente(String stato) {

	for (StatoPagamentoType s : this.getStatiPosizioniChiusePositivamente()) {
	    if (s.name().equalsIgnoreCase(stato)) {
		return true;
	    }
	}
	return false;
    }

    // TODO VERIDFICARE STATI NON CONCLUSIVI
    public StatoPagamentoType[] getStatiPosizioniNonConclusivi() {

	StatoPagamentoType[] ret = new StatoPagamentoType[7];
	ret[0] = StatoPagamentoType.ACQUISITO;
	ret[1] = StatoPagamentoType.ANNULLAMENTO_RICHIESTO;
	ret[2] = StatoPagamentoType.ATTIVATO_IN_PSP;
	ret[3] = StatoPagamentoType.PAGATO_OFFLINE_DA_ANNULLARE;
	ret[4] = StatoPagamentoType.TRASMESSO_A_PSP;
	ret[5] = StatoPagamentoType.CON_ERRORE;
	ret[6] = StatoPagamentoType.NON_ACQUISITO;
	return ret;
    }

    public String[] getDefaultStatiNonConclusiviString() {

	StatoPagamentoType[] statiPosizioniNonConclusivi = getStatiPosizioniNonConclusivi();
	String[] ret = new String[statiPosizioniNonConclusivi.length];
	for (int i = 0; i < statiPosizioniNonConclusivi.length; i++) {
	    ret[i] = statiPosizioniNonConclusivi[i].name();
	}
	return ret;
    }

    public String[] convertiStatiDaStringa(String commaSeparatiStatiDaVerificare) {

	if (StringUtils.isBlank(commaSeparatiStatiDaVerificare)) {
	    return null;
	}
	Set<String> valori = new HashSet<String>();
	String[] csv = commaSeparatiStatiDaVerificare.split(",");
	for (String s : csv) {
	    if (!StringUtils.defaultString(s).trim().isEmpty()) {
		String val = s.trim().toUpperCase();
		try {
		    StatoPagamentoType.valueOf(val); // da errore se non esiste uno stato specifico
		    valori.add(val);
		} catch (Exception e) {
		    // do nothing
		}
	    }
	}
	if (valori.isEmpty()) {
	    return null;
	}
	String[] res = new String[valori.size()];
	return valori.toArray(res);
    }

    public StatoPagamentoType[] getStatiPosizioniPagabili() {

	StatoPagamentoType[] ret = new StatoPagamentoType[3];
	ret[0] = StatoPagamentoType.ACQUISITO;
	ret[1] = StatoPagamentoType.ATTIVATO_IN_PSP;
	ret[2] = StatoPagamentoType.TRASMESSO_A_PSP;
	return ret;
    }

    public List<String> getStatiPosizioniAsStringList(StatoPagamentoType[] ret) {

	List<String> list = new ArrayList<String>();
	for (StatoPagamentoType s : ret) {
	    list.add(s.name());
	}
	return list;
    }

    public List<String> getElencoNomiStatiPerBlackList() {

	List<String> retVal = new ArrayList<String>();
	retVal.add(StatoPagamentoType.ACQUISITO.name());
	retVal.add(StatoPagamentoType.ATTIVATO_IN_PSP.name());
	retVal.add(StatoPagamentoType.TRASMESSO_A_PSP.name());
	return retVal;
    }

    public StatoPagamentoType[] convertiStatiDaStringaInEnumeration(String commaSeparatiStatiDaVerificare) {

	String[] valori = convertiStatiDaStringa(commaSeparatiStatiDaVerificare);
	if (valori == null || valori.length == 0) {
	    return null;
	}
	StatoPagamentoType[] ret = new StatoPagamentoType[valori.length];
	for (int i = 0; i < valori.length; i++) {
	    ret[i] = StatoPagamentoType.valueOf(valori[i]);
	}
	return ret;
    }

    public boolean rimuovibileDaBlackList(String statoVal) {

	return (statoVal.equals(StatoPagamentoType.ANNULLAMENTO_RICHIESTO.name()) || statoVal.equals(StatoPagamentoType.ANNULLATO.name())
		|| statoVal.equals(StatoPagamentoType.PAGATO_OFFLINE_ANNULLATO.name())
		|| statoVal.equals(StatoPagamentoType.PAGATO_OFFLINE_DA_ANNULLARE.name())
		|| statoVal.equals(StatoPagamentoType.RENDICONTATO_DA_IC.name()) || statoVal.equals(StatoPagamentoType.NOTIFICATO_DA_PSP.name()));
    }

    public boolean isStatoAnnullato(String statoVal) {

	return statoVal.equals(StatoPagamentoType.ANNULLAMENTO_RICHIESTO.name()) || statoVal.equals(StatoPagamentoType.ANNULLATO.name());
    }

    public boolean isStatoAnnullatoPagatoOffLine(String statoVal) {

	return statoVal.equals(StatoPagamentoType.PAGATO_OFFLINE_DA_ANNULLARE.name())
		|| statoVal.equals(StatoPagamentoType.PAGATO_OFFLINE_ANNULLATO.name());
    }

    public StatoPagamentoType[] getStatiPagamentoBlackListNonPagati() {

	StatoPagamentoType[] ret = new StatoPagamentoType[1];
	ret[0] = StatoPagamentoType.ATTIVATO_IN_PSP;
	return ret;
    }

    public StatoPagamentoType[] getStatiPagamentoCheNonSiPossonoCancellare() {

	StatoPagamentoType[] ret = new StatoPagamentoType[4];
	ret[0] = StatoPagamentoType.PAGATO_OFFLINE_DA_ANNULLARE;
	ret[1] = StatoPagamentoType.PAGATO_OFFLINE_ANNULLATO;
	ret[2] = StatoPagamentoType.RENDICONTATO_DA_IC;
	ret[3] = StatoPagamentoType.NOTIFICATO_DA_PSP;
	return ret;
    }

    public boolean isStatoAnnullamentoAmmesso(String stato) {

	//	StatoPagamentoType[] statiPagamentoCheNonSiPossonoCancellare = getStatiPagamentoCheNonSiPossonoCancellare();
	//	for (StatoPagamentoType statoPagamentoType : statiPagamentoCheNonSiPossonoCancellare) {
	//	    if (statoPagamentoType.name().equalsIgnoreCase(stato)) {
	//		return false;
	//	    }
	//	}
	StatoPagamentoType[] statiPagamentoCheSiPossonoCancellare = getStatiPosizioniPagabili();
	for (StatoPagamentoType statoPagamentoType : statiPagamentoCheSiPossonoCancellare) {
	    if (statoPagamentoType.name().equalsIgnoreCase(stato)) {
		return true;
	    }
	}
	return false;
    }

    public boolean isStatoPosizionePagabileEAttivate(String stato) {

	for (StatoPagamentoType s : this.getStatiPagamentoBlackListNonPagati()) {
	    if (s.name().equalsIgnoreCase(stato)) {
		return true;
	    }
	}
	return false;
    }
}
