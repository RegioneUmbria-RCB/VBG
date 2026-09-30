package it.gruppoinit.pal.gp.core.features.nodopagamenti;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.DettPosizioneDaAllineare;
import it.gruppoinit.pal.gp.core.domain.DettPosizioneDebitoria;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.features.bollettazione.nodopagamenti.StatoPosizioneDebitoria;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria.DettPosizioneDebitoriaService;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.eventi.EventoPosizioneDebitoriaPagata;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.rest.ImportiResponseType;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.rest.PosizioneDebitoriaResponseType;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.verificastato.IIdPosizioneSuNodoPagamenti;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.verificastato.IdPosizioneSuNodoPagamenti;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.verificastato.VerificaStatoPosizioniDebitorie;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.verificastato.VerificaStatoSuNodoPagamentiServiceImpl;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;

@Service
public class AggiornaDettPosizioniDebitorieServiceImpl implements AggiornaDettPosizioniDebitorieService {

    private static final Logger log = LoggerFactory.getLogger(AggiornaDettPosizioniDebitorieServiceImpl.class);
    @Autowired
    private DettPosizioneDaAllineareDAO dettPosizioneDaAllineareDAO;
    @Autowired
    private DettPosizioneDebitoriaService dettPosizioneDebitoriaService;
    @Autowired
    private NodoPagamentiService nodoPagamentiService;
    @Autowired
    private IEventPublisher publisher;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;

    @Override
    public void aggiornaDettaglioPosizioniDebitorie(Integer numeroMassimoPos) {

	log.debug("aggiornaDettaglioPosizioniDebitorie inizio aggiornamento tabella posizioni debitorie");
	//Recuperiamo la lista di tutti i record di dett_posizioni_da_allineare
	List<DettPosizioneDaAllineare> dpda = dettPosizioneDaAllineareDAO.findAll(null, numeroMassimoPos);
	for (int i = 0; i < dpda.size(); i++) {
	    // PosizioneDebitoriaResponseType responseType = null;
	    DettPosizioneDaAllineare dettPosizioneDaAllineare = dpda.get(i);
	    Integer idPosDeb = dettPosizioneDaAllineare.getId().getFkDettposizionedebitoriaid();
	    try {
		this.aggiornaDettaglioPosizioneDebitoria(idPosDeb);
		// elimina il record da DETT_POSIZIONI_DA_ALLINEARE
		log.info("aggiornaDettaglioPosizioniDebitorie elimino il riferimento al dettaglio posizione debitoria {}", idPosDeb);
		dettPosizioneDaAllineareDAO.delete(dettPosizioneDaAllineare);
		dettPosizioneDaAllineareDAO.flush();
		dettPosizioneDaAllineareDAO.commit();
		dettPosizioneDaAllineareDAO.flush();
	    } catch (Exception e) {
		log.error("Errore nell recupero del DETT_POSIZIONE_DA_ALLINEARE con id {} a causa di {}, {}",
			new Object[] { idPosDeb, e.getMessage(), e });
	    }
	}
    }

    private void sollevaEventoPosizioneDebitoriaPagataConRiferimenti(Integer idPosDeb, DettPosizioneDebitoria dettaglio,
	    VerificaStatoPosizioniDebitorie statoPosizioniDebitoria) {

	if (statoPosizioniDebitoria.getDatiPagamento() != null) {
	    log.info("aggiornaDettaglioPosizioneDebitoria  posizione debitoria {} è pagata, sollevo evento EventoPosizioneDebitoriaPagata ",
		    idPosDeb);
	    EventoPosizioneDebitoriaPagata evento = EventoPosizioneDebitoriaPagata.fromDatiPagamento(statoPosizioniDebitoria.getDatiPagamento(),
		    dettaglio.getCfEnteCreditore(), false);
	    this.publisher.publish(evento);
	}
    }

    @Override
    public void aggiornaDettaglioPosizioneDebitoria(Integer idPosDeb) throws FunzioneBusinessRemotaException {

	DettPosizioneDebitoria dpd = dettPosizioneDebitoriaService.findById(new PkId(idPosDeb));
	// ISoftwareComuneData software = dettPosizioneDebitoriaService.getSoftwareAndcomuneFromDettPosizioneDebitoria(idPosDeb);
	ORMHelper.setSoftware(dpd.getCodiceSoftware());
	// Chiama il servizio rest del dettaglio delle posizioni debitorie 
	log.debug("aggiornaDettaglioPosizioneDebitoria recupero la posizione debitoria: {}", idPosDeb);
	String codiceComune = null;
	if (dpd.getComune() != null) {
	    codiceComune = dpd.getComune().getCodicecomune();
	}
	VerticalizzazioneNodoPagamentiServiceImpl verticalizzazioneNodoPagamentiServiceImpl = new VerticalizzazioneNodoPagamentiServiceImpl(
		verticalizzazioniService, codiceComune);
	Set<IIdPosizioneSuNodoPagamenti> posizioni = new HashSet<IIdPosizioneSuNodoPagamenti>();
	posizioni.add(new IdPosizioneSuNodoPagamenti(dpd.getCfEnteCreditore(), dpd.getIdPosizioneDebitoria()));
	List<VerificaStatoPosizioniDebitorie> statoPosizionis = new VerificaStatoSuNodoPagamentiServiceImpl(verticalizzazioneNodoPagamentiServiceImpl)
		.verificaStato(posizioni);
	if (statoPosizionis.isEmpty() || statoPosizionis.size() > 1) {
	    throw new BusinessValidationException("Tornati più di uno stato per la posizione debitoria " + idPosDeb);
	}
	VerificaStatoPosizioniDebitorie stato = statoPosizionis.get(0);
	// aggiorna la tabella dett_posizione_debitoria
	if (StringUtils.isBlank(dpd.getCodiceAvviso()) && StringUtils.isNotBlank(stato.getCodiceAvviso())) {
	    dpd.setCodiceAvviso(stato.getCodiceAvviso());
	}
	dpd.setDataRegistrazione(stato.getDataRegistrazione());
	if (StringUtils.isBlank(dpd.getDescrizioneCausale()) && StringUtils.isNotBlank(stato.getDescrizioneCausale())) {
	    dpd.setDescrizioneCausale(stato.getDescrizioneCausale());
	}
	if (dpd.getImportoIvato() == null) {
	    PosizioneDebitoriaResponseType responseType = nodoPagamentiService.dettaglioPosizioneDebitoria(idPosDeb);
	    BigDecimal importo = BigDecimal.ZERO;
	    for (ImportiResponseType impType : responseType.getImporti()) {
		importo = importo.add(impType.getImporto());
	    }
	    dpd.setImportoIvato(importo);
	}
	if (StringUtils.isBlank(dpd.getIuv()) && StringUtils.isNotBlank(stato.getIuv())) {
	    dpd.setIuv(stato.getIuv());
	}
	if (StringUtils.isBlank(dpd.getQrcode()) && StringUtils.isNotBlank(stato.getQrCode())) {
	    dpd.setQrcode(stato.getQrCode());
	}
	StatoPosizioneDebitoria stati = stato.getStatoAttuale();
	dpd.setStato(stati.getCodiceStato());
	dpd.setDataUltimoStato(stati.getDataRiferimentoStato());
	dpd.setDescStato(stati.getDescrizioneStato());
	log.info("aggiornaDettaglioPosizioneDebitoria aggiorno il dettaglio posizione debitoria {}", idPosDeb);
	dettPosizioneDebitoriaService.update(dpd);
	this.sollevaEventoPosizioneDebitoriaPagataConRiferimenti(idPosDeb, dpd, stato);
    }
}
