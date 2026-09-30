package it.gruppoinit.pal.gp.core.features.rabbitmq;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;

@Service
public class VerticalizzazioneRabbitMQServiceImpl implements IVerticalizzazioneRabbitMQService {

    @Autowired
    private VerticalizzazioniService service;
    public static final String NOME_VERTICALIZZAZIONE = "RABBITMQ";
    private static final String PAR_DATA_INIZIO_RICERCHE_NOTIFICHE = "DATA_INIZIO_RICERCHE_NOTIFICHE";

    @Override
    public boolean isAttiva() {

	return this.service.isAttiva(VerticalizzazioneRabbitMQServiceImpl.NOME_VERTICALIZZAZIONE);
    }

    @Override
    public Date dataInizioRicercheNotifiche() {

	Calendar c = Calendar.getInstance();
	c.set(Calendar.HOUR, 0);
	c.set(Calendar.MINUTE, 0);
	c.set(Calendar.SECOND, 0);
	return this.service.getDate(NOME_VERTICALIZZAZIONE, PAR_DATA_INIZIO_RICERCHE_NOTIFICHE, c.getTime());
    }
}
