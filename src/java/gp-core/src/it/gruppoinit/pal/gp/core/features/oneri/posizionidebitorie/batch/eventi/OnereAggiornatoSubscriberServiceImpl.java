package it.gruppoinit.pal.gp.core.features.oneri.posizionidebitorie.batch.eventi;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.DettPosizioneDebitoria;
import it.gruppoinit.pal.gp.core.domain.Istanzeoneri;
import it.gruppoinit.pal.gp.core.domain.IstoneriDettPosizioni;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.oneri.DocumentiDaGenerare;
import it.gruppoinit.pal.gp.core.features.oneri.IstanzeoneriService;
import it.gruppoinit.pal.gp.core.features.oneri.eventi.EventoOnereIstanzaAggiornato;
import it.gruppoinit.pal.gp.core.features.oneri.posizionidebitorie.batch.IstanzeoneriPosdebBatchService;

@Service
public class OnereAggiornatoSubscriberServiceImpl implements IEventSubscriber<EventoOnereIstanzaAggiornato> {

    private IstanzeoneriService oneriService;
    private IstanzeoneriPosdebBatchService posizioniDebitorieBatchService;

    @Autowired
    public OnereAggiornatoSubscriberServiceImpl(IstanzeoneriPosdebBatchService posizioniDebitorieBatchService, IstanzeoneriService oneriService) {

	this.oneriService = oneriService;
	this.posizioniDebitorieBatchService = posizioniDebitorieBatchService;
    }

    @Override
    public void onEvent(EventoOnereIstanzaAggiornato e) {

	Istanzeoneri onere = this.oneriService.findById(e.getIdOnere());
	if (onere == null) {
	    throw new IllegalArgumentException("Non è stato possibile trovare un onere con id " + e.getIdOnere().toString());
	}
	boolean richiedeGenerazioneDocumentiPerPosizioneDebitoria = onere.richiedeGenerazioneDocumentiPerPosizioneDebitoria();
	Set<IstoneriDettPosizioni> pds = onere.getIstoneriDettPosizioni();
	for (IstoneriDettPosizioni ipd : pds) {
	    DettPosizioneDebitoria posizioneDebitoria = ipd.getDettPosizioneDebitoria();
	    if (posizioneDebitoria == null
		    || this.posizioniDebitorieBatchService.esisteConAltraPosizioneDebitoria(e.getIdOnere(), posizioneDebitoria.getId().getCodice())) {
		this.posizioniDebitorieBatchService.eliminaDaIdOnere(onere.getId().getCodice());
	    }
	    if (!richiedeGenerazioneDocumentiPerPosizioneDebitoria) {
		continue;
	    }
	    DocumentiDaGenerare documentiDaGenerare = onere.getTipicausalioneri().getDocumentiDaGenerare();
	    this.posizioniDebitorieBatchService.generaRichiestaDocumentiSeNonEsiste(onere.getId().getCodice(), posizioneDebitoria.getId().getCodice(),
		    documentiDaGenerare);
	}
    }
}
