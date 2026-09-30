package it.gruppoinit.pal.gp.core.features.protocollazione.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.ws.client.ProtocolloWSClient;
import it.gruppoinit.protocollo.schemas.messages.IProtocollazioneService;

@Component
public class ProtocolloWSFactory implements IProtocolloWSFactory {

    @Autowired
    private VerticalizzazioniService verticalizzazioniService;

    public IProtocollazioneService createPort() throws Exception {

	return new ProtocolloWSClient(verticalizzazioniService).getWsPort();
    }
}
