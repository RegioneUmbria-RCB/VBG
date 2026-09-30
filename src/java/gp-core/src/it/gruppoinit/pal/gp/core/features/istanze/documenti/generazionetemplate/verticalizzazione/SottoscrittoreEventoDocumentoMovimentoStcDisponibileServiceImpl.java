package it.gruppoinit.pal.gp.core.features.istanze.documenti.generazionetemplate.verticalizzazione;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.istanze.documenti.generazionetemplate.IApplicaQRCodeService;

@Service
public class SottoscrittoreEventoDocumentoMovimentoStcDisponibileServiceImpl implements IEventSubscriber<EventoDocumentoMovimentoStcDisponibile> {

    @Autowired
    private IApplicaQRCodeService applicaQRCodeService;

    @Override
    public void onEvent(EventoDocumentoMovimentoStcDisponibile e) {

	Integer codiceMovimento = e.getCodiceMovimento();
	this.applicaQRCodeService.applicaQRCode(codiceMovimento, null, false);
    }
}
