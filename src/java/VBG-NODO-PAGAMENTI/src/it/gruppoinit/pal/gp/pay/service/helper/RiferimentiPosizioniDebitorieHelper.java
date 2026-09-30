package it.gruppoinit.pal.gp.pay.service.helper;

import java.math.BigInteger;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.pay.ws.schema.ElencoDocumentiEsitoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoPosizioniDebitorieEsitoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoStatoPosizioniType;
import it.gruppoinit.pal.gp.pay.ws.schema.EsitoOperazionePosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.RiferimentoPosizioneDebitoriaType;

public class RiferimentiPosizioniDebitorieHelper {

    private List<? extends RiferimentoPosizioneDebitoriaType> riferimentiPosizioni;

    public RiferimentiPosizioniDebitorieHelper(ElencoPosizioniDebitorieEsitoType esiti) {

	super();
	this.riferimentiPosizioni = esiti.getEsitoPosizione();
    }

    public RiferimentiPosizioniDebitorieHelper(ElencoStatoPosizioniType stati) {

	super();
	this.riferimentiPosizioni = stati.getStatoPosizioni();
    }

    public RiferimentiPosizioniDebitorieHelper(ElencoDocumentiEsitoType esitiDoc) {

	super();
	this.riferimentiPosizioni = esitiDoc.getEsitoPosizione();
    }

    public RiferimentiPosizioniDebitorieHelper(List<EsitoOperazionePosizioneDebitoriaType> riferimenti) {

	super();
	this.riferimentiPosizioni = riferimenti;
    }

    public RiferimentoPosizioneDebitoriaType findRiferimentoPosizioneById(BigInteger idPos) {

	for (RiferimentoPosizioneDebitoriaType esito : this.riferimentiPosizioni) {
	    if (esito.getIdPosizione() != null && esito.getIdPosizione().equals(idPos)) {
		return esito;
	    }
	}
	return null;
    }

    public RiferimentoPosizioneDebitoriaType findRiferimentoPosizioneByIUV(String iuvPos) {

	for (RiferimentoPosizioneDebitoriaType esito : this.riferimentiPosizioni) {
	    if (StringUtils.defaultString(esito.getIUV()).equals(iuvPos)) {
		return esito;
	    }
	}
	return null;
    }

    public static String riferimentoPosizioneToString(RiferimentoPosizioneDebitoriaType rifPos) {

	StringBuilder sb = new StringBuilder("[");
	if (rifPos != null) {
	    sb.append("id=");
	    if (rifPos.getIdPosizione() != null) {
		sb.append(rifPos.getIdPosizione().intValue());
	    } else {
		sb.append("null");
	    }
	    sb.append(", iuv=").append(rifPos.getIUV());
	}
	sb.append("]");
	return sb.toString();
    }
}
