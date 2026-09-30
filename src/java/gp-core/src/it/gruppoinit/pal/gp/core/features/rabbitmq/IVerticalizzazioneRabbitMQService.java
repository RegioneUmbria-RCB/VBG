package it.gruppoinit.pal.gp.core.features.rabbitmq;

import java.util.Date;

public interface IVerticalizzazioneRabbitMQService {

    boolean isAttiva();

    /**
     * Se non specificato riporta la data corrente
     * 
     * @return
     */
    Date dataInizioRicercheNotifiche();
}
