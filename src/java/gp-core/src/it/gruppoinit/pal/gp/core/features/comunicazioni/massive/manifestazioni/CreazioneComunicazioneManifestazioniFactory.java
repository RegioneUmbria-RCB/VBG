package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreazioneComunicazioneManifestazioniFactory {

    @Autowired
    private CreazioneComunicazioneChiusuraGiornataServiceImpl comunicazioneChiusuraGiornataServiceImpl;
    @Autowired
    private CreazioneComunicazionePosizioneDebitoriaCreditoInsServiceImpl comunicazionePosizioneDebitoriaCreditoInsServiceImpl;

    public ICreazioneComunicazioneManifestazioni getImplementation(ConfigurazioneComunicazioniManifestazioni configurazione) {

	switch (configurazione.getTipoComunicazione()) {
	case CHIUSURA_GIORNATA:
	    return comunicazioneChiusuraGiornataServiceImpl;
	case APERTURA_POS_CREDITO_INSUFFICIENTE:
	    return comunicazionePosizioneDebitoriaCreditoInsServiceImpl;
	default:
	    break;
	}
	throw new IllegalArgumentException("Comunicazione di tipo " + configurazione.getTipoComunicazione() + " non implementata");
    }
}
