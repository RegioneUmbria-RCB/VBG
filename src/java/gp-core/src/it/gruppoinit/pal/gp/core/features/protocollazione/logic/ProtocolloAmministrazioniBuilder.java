package it.gruppoinit.pal.gp.core.features.protocollazione.logic;

import it.gruppoinit.pal.gp.core.domain.AmministrProtocollo;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.ProtocolloMezzi;
import it.gruppoinit.pal.gp.core.domain.ProtocolloModalitainvio;
import it.gruppoinit.pal.gp.core.service.AmministrProtocolloService;
import it.gruppoinit.protocollo.schemas.messages.ProtocolloAmministrazioni;
import it.gruppoinit.protocollo.schemas.messages.ProtocolloComune;

public class ProtocolloAmministrazioniBuilder {

    private AmministrProtocolloService amministrazioniProtocolloService;

    public ProtocolloAmministrazioniBuilder(AmministrProtocolloService amministrazioniProtocolloService) {

	this.amministrazioniProtocolloService = amministrazioniProtocolloService;
    }

    public ProtocolloAmministrazioni build(Amministrazioni amministrazione, String codiceComune, String software) {

	return build(amministrazione, null, null, codiceComune, software);
    }

    public ProtocolloAmministrazioni build(Amministrazioni amministrazione, ProtocolloMezzi mezzo, ProtocolloModalitainvio modalitaInvio,
	    String codiceComune, String software) {

	ProtocolloAmministrazioni retVal = new ProtocolloAmministrazioni();
	if (amministrazione == null || amministrazione.getId() == null || amministrazione.getId().getCodice() == null) {
	    return retVal;
	}
	retVal.setAMMINISTRAZIONE(amministrazione.getAmministrazione());
	retVal.setCAP(amministrazione.getCap());
	retVal.setCITTA(amministrazione.getCitta());
	retVal.setCODICEAMMINISTRAZIONE(amministrazione.getId().getCodice().toString());
	retVal.setCodiceIPA(amministrazione.getCodiceIPA());
	if (amministrazione.getComune() != null) {
	    Comuni comune = amministrazione.getComune();
	    ProtocolloComune comuneResidenza = new ProtocolloComune();
	    comuneResidenza.setCodiceIstat(comune.getCodiceistat());
	    comuneResidenza.setCodiceStatoEstero(comune.getCodicestatoestero());
	    comuneResidenza.setDenominazioneComune(comune.getComune());
	    comuneResidenza.setProvincia(comune.getProvincia());
	    comuneResidenza.setSiglaProvincia(comune.getSiglaprovincia());
	    retVal.setComuneResidenza(comuneResidenza);
	}
	retVal.setEMAIL(amministrazione.getEmail());
	retVal.setFAX(amministrazione.getFax());
	retVal.setINDIRIZZO(amministrazione.getIndirizzo());
	if (mezzo != null) {
	    retVal.setMezzo(mezzo.getCodice());
	}
	if (modalitaInvio != null) {
	    retVal.setModalitaTrasmissione(modalitaInvio.getCodice());
	}
	retVal.setPARTITAIVA(amministrazione.getPartitaiva());
	retVal.setPEC(amministrazione.getPec());
	AmministrProtocollo datiUoRuolo = this.amministrazioniProtocolloService
		.findByAmministrazioneComuneESoftware(amministrazione.getId().getCodice(), codiceComune, software);
	if (datiUoRuolo != null) {
	    retVal.setPROTRUOLO(datiUoRuolo.getProtRuolo());
	    retVal.setPROTUO(datiUoRuolo.getProtUo());
	}
	retVal.setPROVINCIA(amministrazione.getProvincia());
	retVal.setSTCIDSPORTELLO(amministrazione.getStcIdsportello());
	retVal.setTELEFONO1(amministrazione.getTelefono1());
	retVal.setTELEFONO2(amministrazione.getTelefono2());
	retVal.setUFFICIO(amministrazione.getUfficio());
	return retVal;
    }
}
