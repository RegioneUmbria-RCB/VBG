package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.wf;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.DocumentiDaFirmare;
import it.gruppoinit.pal.gp.core.domain.MassiveDAllegati;
import it.gruppoinit.pal.gp.core.domain.MassiveDettaglio;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.ConfigurazioneComunicazioniCommissioni;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.workflow.CommissioniMettiAllaFirmaServiceImpl;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.IWorkFlowStep;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoFirmaComunicazioneAvviata;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.IEventoMassiva.ContestoComunicazioneEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.ConfigurazioniComunicazioneGen;
import it.gruppoinit.pal.gp.core.features.firmadigitale.DocumentiDaFirmareService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;

@Service
public class GenMettiAllaFirmaServiceImpl implements IWorkFlowStep<ConfigurazioniComunicazioneGen> {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(GenMettiAllaFirmaServiceImpl.class);
    private IEventPublisher publisher;
    private IComunicazioniMassiveDAO comunicazioniMassiveDAO;
    private IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO;
    private DocumentiDaFirmareService documentiDaFirmareService;
    private ResponsabiliService responsabiliService;
    private UserSecurityService userSecurityService;
    private OggettiService oggettiService;

    @Autowired
    public GenMettiAllaFirmaServiceImpl(IEventPublisher publisher, IComunicazioniMassiveDAO comunicazioniMassiveDAO,
	    IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO, DocumentiDaFirmareService documentiDaFirmareService,
	    ResponsabiliService responsabiliService, UserSecurityService userSecurityService, OggettiService oggettiService) {

	super();
	this.publisher = publisher;
	this.comunicazioniMassiveDAO = comunicazioniMassiveDAO;
	this.comunicazioniMassiveDettaglioDAO = comunicazioniMassiveDettaglioDAO;
	this.documentiDaFirmareService = documentiDaFirmareService;
	this.responsabiliService = responsabiliService;
	this.userSecurityService = userSecurityService;
	this.oggettiService = oggettiService;
    }
    
    
    @Override
    public void elabora(int idDettaglioComunicazione, ConfigurazioniComunicazioneGen configurazione) {

	MassiveDettaglio dettaglio = comunicazioniMassiveDettaglioDAO.getById(idDettaglioComunicazione);
	int idTestata = dettaglio.getMassiveTestata().getId().getCodice();
	log.debug("MettiAllaFirmaServiceImpl {} - {}", idTestata, idDettaglioComunicazione);
	// RECUPERA I FIRMATARI DALLA TABELLA MASSIVE_T_FIRMATARI
	List<Integer> firmatari = comunicazioniMassiveDAO.getFirmatariByIdTestata(idTestata);
	if (firmatari.isEmpty()) {
	    log.error("MettiAllaFirmaServiceImpl Non sono previsti firmatari {} - {}", idTestata, idDettaglioComunicazione);
	    throw new BusinessValidationException("Non sono previsti firmatari in questo passaggio.");
	}
	// FORSE NON SERVE ==> VERIFICA CHE NON ESISTANO DATI NELLA TABELLA DOCUMENTI_DA_FIRMARE CON QUEL CODICE_OGGETTO
	// RECUPERA GLI ALLEGATI DA MASSIVE_D_ALLEGATI
	log.debug("MettiAllaFirmaServiceImpl recupero la lista degli allegati {} - {}", idTestata, idDettaglioComunicazione);
	List<MassiveDAllegati> allegati = comunicazioniMassiveDettaglioDAO.getMassiveDAllegatiByIdDettaglio(idDettaglioComunicazione);
	// PER OGNI ALLEGATO E FIRMATARIO INSERISCE NELLA TABELLA DOCUMENTI_DA_FIRMARE
	Responsabili chiHaMessoAllaFirma = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	for (Integer idFirmatario : firmatari) {
	    for (MassiveDAllegati massiveDAllegati : allegati) {
		Oggetti o = oggettiService.findById(new PkId(massiveDAllegati.getCodiceOggetto()));
		log.debug("MettiAllaFirmaServiceImpl lista degli allegati {} - {} inserisco il doc {} per firmatario {} ",
			new Object[] { idTestata, idDettaglioComunicazione, massiveDAllegati.getCodiceOggetto(), idFirmatario });
		DocumentiDaFirmare d = new DocumentiDaFirmare();
		d.setRichiedente(chiHaMessoAllaFirma);
		d.setFirmatario(responsabiliService.findById(new PkId(idFirmatario)));
		d.setOggetti(o);
		documentiDaFirmareService.insert(d, false);
		comunicazioniMassiveDettaglioDAO.insertDocumentoDaFirmare(idDettaglioComunicazione, d.getId().getCodice());
	    }
	}
	log.debug("MettiAllaFirmaServiceImpl lancio l'evento ContestoComunicazioneEnum.COMMISSIONI, idDettaglio {} - {}", idTestata,
		idDettaglioComunicazione);
	// LANCIA L'EVENTO EventoComunicazioneProntaPerFirma
	publisher.publish(new EventoFirmaComunicazioneAvviata(configurazione.getContesto(), idDettaglioComunicazione));
	
    }

}
