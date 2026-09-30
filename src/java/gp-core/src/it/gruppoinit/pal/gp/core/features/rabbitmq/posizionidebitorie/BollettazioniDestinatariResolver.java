package it.gruppoinit.pal.gp.core.features.rabbitmq.posizionidebitorie;

import it.gruppoinit.pal.gp.core.dao.helper.ImplementazioniEnum;
import it.gruppoinit.pal.gp.core.domain.DettPosizioneDebitoria;
import it.gruppoinit.pal.gp.core.features.oneri.IstanzeoneriService;
import it.gruppoinit.pal.gp.core.features.rabbitmq.model.CodiciFiscaliDestinatariBean;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.BollGestTestataService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzerichiedentiService;

public class BollettazioniDestinatariResolver extends DestinatariResolver implements IDestinatariDettPosizioneDebitoriaResolver {

    private DettPosizioneDebitoria dettPosizioneDebitoria;
    private BollGestTestataService bollGestTestataService;

    public BollettazioniDestinatariResolver(IstanzeoneriService istanzeoneriService, IstanzeService istanzeService,
	    IstanzerichiedentiService istanzerichiedentiService, AnagrafeService anagrafeService, BollGestTestataService bollGestTestataService,
	    DettPosizioneDebitoria dettPosizioneDebitoria) {

	super(istanzeService, istanzerichiedentiService, anagrafeService);
	this.dettPosizioneDebitoria = dettPosizioneDebitoria;
    }

    @Override
    public CodiciFiscaliDestinatariBean getDestinatariPersoneFisiche() {
	// Dalla posizione debitoria risale alla tabella dove è registrata la posizione stessa

	String implementazione = bollGestTestataService.findImplementazioneByPosDeb(dettPosizioneDebitoria.getId().getCodice());
	// 1. se bollettazione istanze allora prende le i codiciistanza raggruppati con nuova query su boll_gest_istanzeoneri 
	if (implementazione.equals(ImplementazioniEnum.ISTANZE.name())) {
	    Integer codiceIstanza = bollGestTestataService.findCodIstanzaByDettPosDebitoria(dettPosizioneDebitoria.getId().getCodice());
	    return calcolaDestinatari(codiceIstanza);
	    // 2. se bollettazione è dei mercati allora  torna new CodiciFiscaliDestinatariBean() in quanto non aggiungerebbe altre informazioni rispetto alla posizione creata
	} else if (implementazione.equals(ImplementazioniEnum.MERCATI.name())) {
	    return new CodiciFiscaliDestinatariBean();
	}
	return null;
    }
}
