package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.workflow;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.ConfigurazioneComunicazioniCommissioni;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.IComunicazioniToCommissioniService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.IWorkFlowStep;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.LetteraComunicazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoAllegatiDelDettaglioElaborati;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.IEventoMassiva.ContestoComunicazioneEnum;

@Service
public class CommissioniAllegatiCompilabiliServiceImpl implements IWorkFlowStep<ConfigurazioneComunicazioniCommissioni> {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(CommissioniAllegatiCompilabiliServiceImpl.class);
    private IEventPublisher publisher;
    private IComunicazioniMassiveDettaglioDAO comunicazioniDettaglioDAO;
    private IComunicazioniToCommissioniService comunicazioniToCommissioniService;

    @Autowired
    public CommissioniAllegatiCompilabiliServiceImpl(IEventPublisher publisher, IComunicazioniMassiveDettaglioDAO comunicazioniDettaglioDAO,
	    IComunicazioniToCommissioniService comunicazioniToCommissioniService) {

	this.comunicazioniDettaglioDAO = comunicazioniDettaglioDAO;
	this.publisher = publisher;
	this.comunicazioniToCommissioniService = comunicazioniToCommissioniService;
    }

    @Override
    public void elabora(int idDettaglioComunicazione, ConfigurazioneComunicazioniCommissioni configurazione) {

	try {
	    for (LetteraComunicazione lettera : configurazione.getLettereComunicazione()) {
		// 2.1 PER OGNI ALLEGATO COMPILABILE VA RICHIAMATO IL SERVIZIO CHE EFFETTUA LE SOSTITUZIONI DEL DOCUMENTo
		// recupera il dettaglio della commissione
		// 3 VERIFICA SE TRASFORMARE IN PDF IL DOCUMENTO
		// 3.1 SE TRASFORMA DEVE RICHIAMARE IL SERVIZIO FILECONVERTER PER CONVERTIRE I FILE NON PDF
		log.debug("elabora inserisco lettera {}, {}", lettera.getCodiceLettera(), configurazione.isTrasformaAllegatiCompilabiliInPdf());
		int codiceOggetto = this.comunicazioniToCommissioniService.generaLetteraAccompagnamentoCommissioniDettaglio(
			lettera.getCodiceLettera(), idDettaglioComunicazione, configurazione.isTrasformaAllegatiCompilabiliInPdf());
		comunicazioniDettaglioDAO.insertAllegato(idDettaglioComunicazione, codiceOggetto);
	    }
	    publisher.publish(new EventoAllegatiDelDettaglioElaborati(ContestoComunicazioneEnum.COMMISSIONI, idDettaglioComunicazione));
	} catch (Exception e) {
	    String messaggioErrore = "Si è verificato un errore nell'elaborazione degli allegati " + e.getMessage();
	    log.error(messaggioErrore, e);
	    comunicazioniDettaglioDAO.salvaErrore(idDettaglioComunicazione, messaggioErrore);
	    throw new RuntimeException(messaggioErrore);
	}
    }
}
