package it.gruppoinit.pal.gp.core.features.oneri.posizionidebitorie.batch.eventi;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.Istanzeoneri;
import it.gruppoinit.pal.gp.core.domain.IstoneriDettPosizioni;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.oneri.DocumentiDaGenerare;
import it.gruppoinit.pal.gp.core.features.oneri.IstanzeoneriService;
import it.gruppoinit.pal.gp.core.features.oneri.eventi.EventoOnereIstanzaInserito;
import it.gruppoinit.pal.gp.core.features.oneri.posizionidebitorie.batch.IstanzeoneriPosdebBatchService;

@Service
public class OnereInseritoSubscriberServiceImpl implements IEventSubscriber<EventoOnereIstanzaInserito> {

    private IstanzeoneriService oneriService;
    private IstanzeoneriPosdebBatchService posizioniDebitorieBatchService;

    @Autowired
    public OnereInseritoSubscriberServiceImpl(IstanzeoneriPosdebBatchService posizioniDebitorieBatchService, IstanzeoneriService oneriService) {

	this.oneriService = oneriService;
	this.posizioniDebitorieBatchService = posizioniDebitorieBatchService;
    }

    @Override
    public void onEvent(EventoOnereIstanzaInserito e) {

	Istanzeoneri onere = this.oneriService.findById(e.getIdOnere());
	if (onere == null) {
	    throw new IllegalArgumentException("Non è stato possibile trovare un onere con id " + e.getIdOnere().toString());
	}
	if (!onere.richiedeGenerazioneDocumentiPerPosizioneDebitoria()) {
	    return;
	}
	Set<IstoneriDettPosizioni> posizioneDebitoria = onere.getIstoneriDettPosizioni();
	for (IstoneriDettPosizioni istoneriDettPosizioni : posizioneDebitoria) {
	    DocumentiDaGenerare documentiDaGenerare = onere.getTipicausalioneri().getDocumentiDaGenerare();
	    this.posizioniDebitorieBatchService.generaRichiestaDocumentiSeNonEsiste(onere.getId().getCodice(),
		    istoneriDettPosizioni.getDettPosizioneDebitoria().getId().getCodice(), documentiDaGenerare);
	}
    }
}
