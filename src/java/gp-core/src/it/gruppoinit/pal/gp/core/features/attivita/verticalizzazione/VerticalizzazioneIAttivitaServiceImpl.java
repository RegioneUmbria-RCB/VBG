package it.gruppoinit.pal.gp.core.features.attivita.verticalizzazione;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiService;

@Service
public class VerticalizzazioneIAttivitaServiceImpl implements IVerticalizzazioneIAttivitaService {

    @Autowired
    private VerticalizzazioniService service;
    @Autowired
    private Dyn2CampiService dyn2CampiService;
    private static final String NOME_VERTICALIZZAZIONE = "I_ATTIVITA";
    private static final String PAR_AGGIORNADENOMINAZIONE = "AGGIORNADENOMINAZIONE";
    private static final String PAR_CAMPO_DYN_FINE_ATT = "CAMPO_DYN_FINE_ATT";
    private static final String PAR_GRUPPOSOFTWARE = "GRUPPOSOFTWARE";
    private static final String PAR_INVERTI_RICHIEDENTE_STORICO = "INVERTI_RICHIEDENTE_STORICO";
    private static final String PAR_NON_CONSIDERARE_ISTANZE_COLL = "NON_CONSIDERARE_ISTANZE_COLL";
    private static final String PAR_PRECOMPILAINDIRIZZOCIVICO = "PRECOMPILAINDIRIZZOCIVICO";
    private static final String PAR_QUERYDENOMINAZIONE = "QUERYDENOMINAZIONE";

    @Override
    public boolean isAttiva() {

	return this.service.isAttiva(VerticalizzazioneIAttivitaServiceImpl.NOME_VERTICALIZZAZIONE);
    }

    @Override
    public boolean isAggiornaDenominazione() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(
		    "La verticalizzazione " + VerticalizzazioneIAttivitaServiceImpl.NOME_VERTICALIZZAZIONE + " non è attiva.");
	}
	return this.service.getBoolean(VerticalizzazioneIAttivitaServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneIAttivitaServiceImpl.PAR_AGGIORNADENOMINAZIONE, "S", false);
    }

    @Override
    public Dyn2Campi getCampoDynFineAtt() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(
		    "La verticalizzazione " + VerticalizzazioneIAttivitaServiceImpl.NOME_VERTICALIZZAZIONE + " non è attiva.");
	}
	String nomeCampo = this.service.getString(VerticalizzazioneIAttivitaServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneIAttivitaServiceImpl.PAR_CAMPO_DYN_FINE_ATT);
	if (StringUtils.isEmpty(nomeCampo)) {
	    return null;
	}
	return this.dyn2CampiService.findByNomeCampo(nomeCampo);
    }

    @Override
    public String getGruppoSoftware() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(
		    "La verticalizzazione " + VerticalizzazioneIAttivitaServiceImpl.NOME_VERTICALIZZAZIONE + " non è attiva.");
	}
	return this.service.getString(VerticalizzazioneIAttivitaServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneIAttivitaServiceImpl.PAR_GRUPPOSOFTWARE);
    }

    @Override
    public boolean isInvertiRichiedenteStorico() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(
		    "La verticalizzazione " + VerticalizzazioneIAttivitaServiceImpl.NOME_VERTICALIZZAZIONE + " non è attiva.");
	}
	return this.service.getBoolean(VerticalizzazioneIAttivitaServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneIAttivitaServiceImpl.PAR_INVERTI_RICHIEDENTE_STORICO, "1", false);
    }

    @Override
    public boolean isNonConsiderareIstanzeCollegate() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(
		    "La verticalizzazione " + VerticalizzazioneIAttivitaServiceImpl.NOME_VERTICALIZZAZIONE + " non è attiva.");
	}
	return this.service.getBoolean(VerticalizzazioneIAttivitaServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneIAttivitaServiceImpl.PAR_NON_CONSIDERARE_ISTANZE_COLL, "1", false);
    }

    @Override
    public boolean isPrecompilaIndirizzoCivico() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(
		    "La verticalizzazione " + VerticalizzazioneIAttivitaServiceImpl.NOME_VERTICALIZZAZIONE + " non è attiva.");
	}
	return this.service.getBoolean(VerticalizzazioneIAttivitaServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneIAttivitaServiceImpl.PAR_PRECOMPILAINDIRIZZOCIVICO, "1", false);
    }

    @Override
    public String getQueryDenominazione() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(
		    "La verticalizzazione " + VerticalizzazioneIAttivitaServiceImpl.NOME_VERTICALIZZAZIONE + " non è attiva.");
	}
	return this.service.getString(VerticalizzazioneIAttivitaServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneIAttivitaServiceImpl.PAR_QUERYDENOMINAZIONE);
    }
}
