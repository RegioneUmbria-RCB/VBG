package it.gruppoinit.pal.gp.core.features.rabbitmq;

import it.gruppoinit.pal.gp.core.features.istanze.eventi.NotificaSoggettiIstanzaAggiornatiRequest;
import it.gruppoinit.pal.gp.core.features.movimenti.scadenze.rabbitmq.NotificaScadenzeRequest;

public interface CodaMessaggiRabbitService {

    void insertSoggettiAggiornati(NotificaSoggettiIstanzaAggiornatiRequest request);

    void insertNotificaPraticaNuova(Integer codiceIstanza);

    void insertNuovaComunicazioneUtente(Integer codiceIstanza);

    void insertMessaggioPraticaCancellata(String uuidIstanza);

    void insertCambioStato(Integer codiceIstanza);

    void insertScadenza(NotificaScadenzeRequest request);
}
