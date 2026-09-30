package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.wf;

import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.MassiveDettaglio;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.IWorkFlowStep;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.LetteraComunicazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoAllegatiDelDettaglioElaborati;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.IEventoMassiva.ContestoComunicazioneEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.ConfigurazioniComunicazioneGen;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.IComunicazioniMassiveGenDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.IComunicazioniToGenService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.custom.IstanzeGroupEnum;


@Service
public class GenAllegatiCompilabiliServiceImpl implements IWorkFlowStep<ConfigurazioniComunicazioneGen> {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(GenAllegatiCompilabiliServiceImpl.class);
    private IEventPublisher publisher;
    private IComunicazioniMassiveDettaglioDAO comunicazioniDettaglioDAO;
    private IComunicazioniToGenService comunicazioniToGenService;
    private IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO;
    private IComunicazioniMassiveGenDAO comunicazioniMassiveGenDAO;

    @Autowired
    public GenAllegatiCompilabiliServiceImpl(IEventPublisher publisher, IComunicazioniMassiveDettaglioDAO comunicazioniDettaglioDAO,
	    IComunicazioniToGenService comunicazioniToGenService, IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO,
	    IComunicazioniMassiveGenDAO comunicazioniMassiveGenDAO) {

	super();
	this.publisher = publisher;
	this.comunicazioniDettaglioDAO = comunicazioniDettaglioDAO;
	this.comunicazioniToGenService = comunicazioniToGenService;
	this.comunicazioniMassiveDettaglioDAO = comunicazioniMassiveDettaglioDAO;
	this.comunicazioniMassiveGenDAO = comunicazioniMassiveGenDAO;
    }

    @Override
    public void elabora(int idDettaglioComunicazione, ConfigurazioniComunicazioneGen configurazione) {

	try {
	    
	    MassiveDettaglio dettaglio = comunicazioniMassiveDettaglioDAO.getById(idDettaglioComunicazione);
	    Integer codiceIstanza = null;
	    
	    if (configurazione.getContesto() == ContestoComunicazioneEnum.ISTANZE && IstanzeGroupEnum.ISTANZE.name()
		    .equals(comunicazioniMassiveGenDAO.getGroupTypeForIstanze(dettaglio.getMassiveTestata().getId().getCodice()))) {
		Istanze istanza = comunicazioniMassiveGenDAO.getIstanzaFromDettaglio(idDettaglioComunicazione);
		codiceIstanza = istanza.getId().getCodice();
	    }

	    for (LetteraComunicazione lettera : configurazione.getLettereComunicazione()) {
		// 2.1 PER OGNI ALLEGATO COMPILABILE VA RICHIAMATO IL SERVIZIO CHE EFFETTUA LE SOSTITUZIONI DEL DOCUMENTo
		// recupera il dettaglio della commissione
		// 3 VERIFICA SE TRASFORMARE IN PDF IL DOCUMENTO
		// 3.1 SE TRASFORMA DEVE RICHIAMARE IL SERVIZIO FILECONVERTER PER CONVERTIRE I FILE NON PDF
		log.debug("elabora inserisco lettera {}, {}", lettera.getCodiceLettera(), configurazione.isTrasformaAllegatiCompilabiliInPdf());
		int codiceOggetto = this.comunicazioniToGenService.generaLetteraAccompagnamentoCommissioniDettaglio(lettera.getCodiceLettera(),
			idDettaglioComunicazione, configurazione.isTrasformaAllegatiCompilabiliInPdf(), codiceIstanza);
		comunicazioniDettaglioDAO.insertAllegato(idDettaglioComunicazione, codiceOggetto);
	    }
	    publisher.publish(new EventoAllegatiDelDettaglioElaborati(configurazione.getContesto(), idDettaglioComunicazione));
	} catch (Exception e) {
	    String messaggioErrore = "Si è verificato un errore nell'elaborazione degli allegati " + e.getMessage();
	    log.error(messaggioErrore, e);
	    comunicazioniDettaglioDAO.salvaErrore(idDettaglioComunicazione, messaggioErrore);
	    throw new RuntimeException(messaggioErrore);
	}
    }
}
