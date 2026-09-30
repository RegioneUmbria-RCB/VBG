package it.gruppoinit.pal.gp.core.features.istanze.documenti.generazionetemplate.verticalizzazione;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.istanze.documenti.generazionetemplate.IApplicaQRCodeService;

@Service
public class SottoscrittoreEventoDocumentoIstanzaStcDisponibileServiceImpl implements IEventSubscriber<EventoDocumentoIstanzaStcDisponibile> {

    @Autowired
    private IApplicaQRCodeService applicaQRCodeService;

    @Override
    public void onEvent(EventoDocumentoIstanzaStcDisponibile e) {

	Integer codiceIstanza = e.getCodiceIStanza();
	applicaQRCodeService.applicaQRCode(codiceIstanza, null, true);
    }
}
