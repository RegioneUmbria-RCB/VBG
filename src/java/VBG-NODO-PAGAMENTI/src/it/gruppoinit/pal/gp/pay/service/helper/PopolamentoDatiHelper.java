package it.gruppoinit.pal.gp.pay.service.helper;

import java.math.BigInteger;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.ws.schema.EsitoOperazionePosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.PosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.RegistrazioneContabileType;
import it.gruppoinit.pal.gp.pay.ws.schema.RiferimentoPosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPagamentoType;

public class PopolamentoDatiHelper {

    public PopolamentoDatiHelper() {

	// CLASSE DI UTILITA' PER ACCOMUNQRE METODI DI POPOLAMENTO BEAN
    }

    public static void completaDatiDaPosizioneDebitoria(EsitoOperazionePosizioneDebitoriaType esitoNodo, PayPosizioniDebitorie payPD,
	    Set<String> riferimentiClient) {

	esitoNodo.setIdPosizione(BigInteger.valueOf(payPD.getId().getCodice()));
	if (riferimentiClient != null && !riferimentiClient.isEmpty()) {
	    esitoNodo.getRiferimentoClient().addAll(riferimentiClient);
	}
	if (StringUtils.isNotBlank(payPD.getIuv())) {
	    esitoNodo.setIUV(payPD.getIuv());
	}
	if (StringUtils.isNotBlank(payPD.getCodiceAvviso())) {
	    esitoNodo.setCodiceAvviso(payPD.getCodiceAvviso());
	}
	if (StringUtils.isNotBlank(payPD.getQrCode())) {
	    esitoNodo.setQrCode(payPD.getQrCode());
	}
	if (StringUtils.isNotBlank(payPD.getDescrizioneCausale())) {
	    esitoNodo.setDescrizioneCausale(payPD.getDescrizioneCausale());
	}
	if (payPD.getDataRegistrazione() != null) {
	    esitoNodo.setDataRegistrazione(Utilities.getXMLGregorianCalendar(payPD.getDataRegistrazione()));
	}
	if (payPD.getDataScadenza() != null) {
	    esitoNodo.setDataScadenza(Utilities.getXMLGregorianCalendar(payPD.getDataScadenza()));
	}
	if (payPD.getRegistrazioneContabile() != null && payPD.getRegistrazioneContabile().getId() != null
		&& payPD.getRegistrazioneContabile().getId().getCodice() != null) {
	    esitoNodo.setIdRegistrazioneContabile(BigInteger.valueOf(payPD.getRegistrazioneContabile().getId().getCodice()));
	}
	if (payPD.recuperaStatoCorrente() != null && StringUtils.isNotBlank(payPD.recuperaStatoCorrente().getStato())) {
	    try {
		StatoPagamentoType status = StatoPagamentoType.fromValue(payPD.recuperaStatoCorrente().getStato());
		esitoNodo.setStato(status);
	    } catch (Exception e) {
		//nulla
	    }
	}
    }

    public static void completaDatiDaEsitoOperazioneDebitoriaType(EsitoOperazionePosizioneDebitoriaType esitoNodo,
	    EsitoOperazionePosizioneDebitoriaType esitoPsp) {

	if (esitoPsp.getIdPosizione() != null) {
	    esitoNodo.setIdPosizione(esitoPsp.getIdPosizione());
	}
	if (StringUtils.isNotBlank(esitoPsp.getIUV())) {
	    esitoNodo.setIUV(esitoPsp.getIUV());
	}
	if (StringUtils.isNotBlank(esitoPsp.getCodiceAvviso())) {
	    esitoNodo.setCodiceAvviso(esitoPsp.getCodiceAvviso());
	}
	if (StringUtils.isNotBlank(esitoPsp.getQrCode())) {
	    esitoNodo.setQrCode(esitoPsp.getQrCode());
	}
	if (StringUtils.isNotBlank(esitoPsp.getDescrizioneCausale())) {
	    esitoNodo.setDescrizioneCausale(esitoPsp.getDescrizioneCausale());
	}
	if (esitoPsp.getDataRegistrazione() != null) {
	    esitoNodo.setDataRegistrazione(esitoPsp.getDataRegistrazione());
	}
	if (esitoPsp.getDataScadenza() != null) {
	    esitoNodo.setDataScadenza(esitoPsp.getDataScadenza());
	}
	if (esitoPsp.getIdRegistrazioneContabile() != null) {
	    esitoNodo.setIdRegistrazioneContabile(esitoPsp.getIdRegistrazioneContabile());
	}
    }

    public static void completaDatiDaRiferimentoPosizioneDebitoriaType(EsitoOperazionePosizioneDebitoriaType esito,
	    RiferimentoPosizioneDebitoriaType refPos) {

	if (refPos.getIdPosizione() != null) {
	    esito.setIdPosizione(refPos.getIdPosizione());
	}
	if (StringUtils.isNotBlank(refPos.getIUV())) {
	    esito.setIUV(refPos.getIUV());
	}
	if (StringUtils.isNotBlank(refPos.getCodiceAvviso())) {
	    esito.setCodiceAvviso(refPos.getCodiceAvviso());
	}
	if (StringUtils.isNotBlank(refPos.getQrCode())) {
	    esito.setQrCode(refPos.getQrCode());
	}
	if (StringUtils.isNotBlank(refPos.getDescrizioneCausale())) {
	    esito.setDescrizioneCausale(refPos.getDescrizioneCausale());
	}
	if (refPos.getDataRegistrazione() != null) {
	    esito.setDataRegistrazione(refPos.getDataRegistrazione());
	}
	if (refPos.getDataScadenza() != null) {
	    esito.setDataScadenza(refPos.getDataScadenza());
	}
	if (refPos.getIdRegistrazioneContabile() != null) {
	    esito.setIdRegistrazioneContabile(refPos.getIdRegistrazioneContabile());
	}
	if (refPos.getRiferimentoClient().isEmpty()) {
	    esito.getRiferimentoClient().addAll(refPos.getRiferimentoClient());
	}
    }

    public static String getRiferimentoPosizioneDebitoria(PosizioneDebitoriaType pd, RegistrazioneContabileType rc, int numRata) {

	StringBuilder sb = new StringBuilder();
	if (pd.getRiferimentiClient().isEmpty()) {
	    sb = new StringBuilder("Debito ").append(rc.getDescrizione());
	    sb.append(", Rata n.").append(numRata);
	    return sb.toString();
	} else {
	    sb.append("Riferimenti: ");
	    for (String s : pd.getRiferimentiClient()) {
		sb.append(s).append(", ");
	    }
	    return sb.toString();
	}
    }
}
