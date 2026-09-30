package it.gruppoinit.pal.gp.core.features.anagrafe.verticalizzazioni;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.areariservata.VerticalizzazioneAreaRiservataServiceImpl;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;

@Service
public class VertRicercaAnagrafeCollegataInternalServiceImpl implements IVertRicercaAnagrafeCollegataInternalService {

    @Autowired
    private VerticalizzazioniService service;

    @Override
    public boolean isAttiva() {

	return this.service.isAttiva(VerticalizzazioneAreaRiservataServiceImpl.NOME_VERTICALIZZAZIONE);
    }

    @Override
    public STRATEGIA_RICERCA getStrategiaRicerca() {

	String ret = this.service.getString(NOME_VERTICALIZZAZIONE, PAR_STRATEGIA_RICERCA, STRATEGIA_RICERCA.DEFAULT.name());
	return STRATEGIA_RICERCA.valueOf(ret.trim());
    }
}
