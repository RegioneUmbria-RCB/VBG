package it.gruppoinit.pal.gp.core.features.protocollazione.logic;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.ProtocolloMezzi;
import it.gruppoinit.pal.gp.core.domain.ProtocolloModalitainvio;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.destinatari.IndirizzoMailResolver;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.destinatari.TipoDestinatarioEnum;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.protocollo.schemas.messages.ProtocolloAnagrafe;
import it.gruppoinit.protocollo.schemas.messages.ProtocolloComune;

public class ProtocolloAnagrafeBuilder {

    public ProtocolloAnagrafe build(Anagrafe anagrafe, Istanze istanza, ProtocolloMezzi mezzo, ProtocolloModalitainvio modalitaInvio,
	    Integer tipoGestionePec) {

	return build(anagrafe, istanza, mezzo, modalitaInvio, tipoGestionePec, null);
    }

    public ProtocolloAnagrafe build(Anagrafe anagrafe, Istanze istanza, ProtocolloMezzi mezzo, ProtocolloModalitainvio modalitaInvio,
	    Integer tipoGestionePec, String email) {

	ProtocolloAnagrafe retVal = new ProtocolloAnagrafe();
	if (anagrafe == null || anagrafe.getId() == null || anagrafe.getId().getCodice() == null) {
	    return retVal;
	}
	retVal.setCAP(anagrafe.getCap());
	retVal.setCITTA(anagrafe.getCitta());
	if (anagrafe.getComuneNascita() != null) {
	    retVal.setCODCOMNASCITA(anagrafe.getComuneNascita().getCodicecomune());
	    retVal.setCodiceIstatComNasc(anagrafe.getComuneNascita().getCodiceistat());
	    retVal.setCodiceStatoEsteroNasc(anagrafe.getComuneNascita().getCodicestatoestero());
	}
	retVal.setCODICEANAGRAFE(anagrafe.getId().getCodice().toString());
	retVal.setCODICEFISCALE(anagrafe.getCodicefiscale());
	if (anagrafe.getComuneResidenza() != null) {
	    ProtocolloComune comuneResidenza = new ProtocolloComune();
	    comuneResidenza.setCodiceIstat(anagrafe.getComuneResidenza().getCodiceistat());
	    comuneResidenza.setCodiceStatoEstero(anagrafe.getComuneResidenza().getCodicestatoestero());
	    comuneResidenza.setDenominazioneComune(anagrafe.getComuneResidenza().getComune());
	    comuneResidenza.setProvincia(anagrafe.getComuneResidenza().getProvincia());
	    comuneResidenza.setSiglaProvincia(anagrafe.getComuneResidenza().getSiglaprovincia());
	    retVal.setComuneResidenza(comuneResidenza);
	    retVal.setCOMUNERESIDENZA(anagrafe.getComuneResidenza().getCodicecomune());
	    retVal.setCodiceIstatComRes(anagrafe.getComuneResidenza().getCodiceistat());
	    retVal.setCodiceStatoEsteroRes(anagrafe.getComuneResidenza().getCodicestatoestero());
	}
	if (anagrafe.getDatanascita() != null) {
	    retVal.setDATANASCITA(Utilities.getXMLGregorianCalendar(anagrafe.getDatanascita()));
	}
	if (anagrafe.getDatanominativo() != null) {
	    retVal.setDATANOMINATIVO(Utilities.getXMLGregorianCalendar(anagrafe.getDatanominativo()));
	}
	retVal.setEMAIL(anagrafe.getEmail());
	retVal.setFAX(anagrafe.getFax());
	retVal.setINDIRIZZO(anagrafe.getIndirizzo());
	if (mezzo != null && StringUtils.isNotBlank(mezzo.getCodice())) {
	    retVal.setMezzo(mezzo.getCodice());
	}
	if (modalitaInvio != null && StringUtils.isNotBlank(modalitaInvio.getCodice())) {
	    retVal.setModalitaTrasmissione(modalitaInvio.getCodice());
	}
	retVal.setNOME(anagrafe.getNome());
	retVal.setNOMINATIVO(anagrafe.getNominativo());
	retVal.setPARTITAIVA(anagrafe.getPartitaiva());
	retVal.setPecAnagrafica(anagrafe.getPec());
	retVal.setPecProtocollazione(email);
	//Verifico la regola in verticalizzazione se arriva vuota email ( ad esempio da interfaccia se non compare il campo
	//per inserire la mail in fase di protocollazione
	if (StringUtils.isBlank(retVal.getPecProtocollazione())) {
	    TipoDestinatarioEnum tipoDestinatario = this.calcolaTipoDestinatario(anagrafe, istanza);
	    IndirizzoMailResolver mailResolver = new IndirizzoMailResolver(tipoGestionePec, istanza.getComune().getCodicecomune(),
		    istanza.getSoftware().getCodice());
	    retVal.setPecProtocollazione(mailResolver.getMailAnagrafe(anagrafe, istanza.getDomicilioElettronico(), tipoDestinatario));
	}
	retVal.setPROVINCIA(anagrafe.getProvincia());
	retVal.setSESSO(anagrafe.getSesso());
	retVal.setTELEFONO(anagrafe.getSesso());
	retVal.setTELEFONOCELLULARE(anagrafe.getTelefonocellulare());
	retVal.setTIPOANAGRAFE(anagrafe.getTipoanagrafe());
	if (anagrafe.getTitolo() != null) {
	    retVal.setTITOLO(anagrafe.getTitolo().getTitolo());
	}
	return retVal;
    }

    private TipoDestinatarioEnum calcolaTipoDestinatario(Anagrafe anagrafe, Istanze istanza) {

	Integer codiceRichiedente = (istanza != null && istanza.getRichiedente() != null && istanza.getRichiedente().getId() != null)
		? istanza.getRichiedente().getId().getCodice()
		: null;
	if (anagrafe.getId().getCodice().equals(codiceRichiedente)) {
	    return TipoDestinatarioEnum.RICHIEDENTE;
	}
	Integer codiceTitolareLegale = (istanza != null && istanza.getTitolarelegale() != null && istanza.getTitolarelegale().getId() != null)
		? istanza.getTitolarelegale().getId().getCodice()
		: null;
	if (anagrafe.getId().getCodice().equals(codiceTitolareLegale)) {
	    return TipoDestinatarioEnum.AZIENDA;
	}
	Integer codiceIntermediario = (istanza != null && istanza.getProfessionista() != null && istanza.getProfessionista().getId() != null)
		? istanza.getProfessionista().getId().getCodice()
		: null;
	if (anagrafe.getId().getCodice().equals(codiceIntermediario)) {
	    return TipoDestinatarioEnum.PROFESSIONISTA;
	}
	return TipoDestinatarioEnum.ALTRO_SOGGETTO;
    }
}
