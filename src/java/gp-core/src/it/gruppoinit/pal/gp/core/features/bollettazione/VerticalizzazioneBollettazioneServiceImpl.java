package it.gruppoinit.pal.gp.core.features.bollettazione;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Service
public class VerticalizzazioneBollettazioneServiceImpl implements IVerticalizzazioneBollettazioneService {

    private VerticalizzazioniService service;

    @Autowired
    public VerticalizzazioneBollettazioneServiceImpl(VerticalizzazioniService service) {

	this.service = service;
    }

    public static final String NOME_VERTICALIZZAZIONE = "COMPORTAMENTI_BOLLETTAZIONE";
    private static final String PAR_MOSTRA_FUNZIONE_RETTIFICA = "MOSTRA_FUNZIONE_RETTIFICA";
    private static final String PAR_MOSTRA_FUNZIONE_AGGIUNGI_RIGA = "MOSTRA_FUNZIONE_AGGIUNGI_RIGA";
    private static final String PAR_MOSTRA_FUNZIONE_COM_MASSIVE = "MOSTRA_FUNZIONE_COM_MASSIVE";
    private static final String PAR_INVIO_POSIZIONI_DELAY = "INVIO_POSIZIONI_DELAY";

    @Override
    public boolean isAttiva() {

	return this.service.isAttiva(NOME_VERTICALIZZAZIONE);
    }

    @Override
    public boolean mostraFunzioneRettifica() {

	return this.service.getBoolean(NOME_VERTICALIZZAZIONE, PAR_MOSTRA_FUNZIONE_RETTIFICA, "S", true);
    }

    @Override
    public boolean mostraFunzioneAggiungiRiga() {

	return this.service.getBoolean(NOME_VERTICALIZZAZIONE, PAR_MOSTRA_FUNZIONE_AGGIUNGI_RIGA, "S", true);
    }

    @Override
    public boolean mostraFunzioneComunicazioniMassive() {

	return this.service.getBoolean(NOME_VERTICALIZZAZIONE, PAR_MOSTRA_FUNZIONE_COM_MASSIVE, "S", true);
    }

    @Override
    public long delayInvioPosizioni() {

	String value = StringUtils.trim(this.service.getString(NOME_VERTICALIZZAZIONE, PAR_INVIO_POSIZIONI_DELAY, "0"));
	if (Utilities.isInteger(value) && !value.equals("0")) {
	    long ret = Long.parseLong(value);
	    if (ret > 0) {
		return ret;
	    }
	}
	return 0L;
    }
}
