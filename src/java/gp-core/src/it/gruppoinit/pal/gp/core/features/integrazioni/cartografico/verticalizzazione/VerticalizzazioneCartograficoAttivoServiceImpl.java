package it.gruppoinit.pal.gp.core.features.integrazioni.cartografico.verticalizzazione;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;

public class VerticalizzazioneCartograficoAttivoServiceImpl implements IVerticalizzazioneCartograficoAttivoService {

    private VerticalizzazioniService service;
    public static final String NOME_VERTICALIZZAZIONE = "CARTOGRAFICO_ATTIVO";
    private static final String MESSAGGIO_ERRORE = "La verticalizzazione " +
	    VerticalizzazioneCartograficoAttivoServiceImpl.NOME_VERTICALIZZAZIONE +
	    " non è attiva.";
    public static final String CONNETTORE = "CONNETTORE";
    public static final String SERVICE_URL = "SERVICE_URL";
    public static final String MODELLO_ALTRI_DATI = "MODELLO_ALTRI_DATI";
    public static final String CAMPO_ALTRI_DATI = "CAMPO_ALTRI_DATI";
    public static final String POSIZIONE_LATITUDINE = "POSIZIONE_LATITUDINE";
    public static final String POSIZIONE_LONGITUDINE = "POSIZIONE_LONGITUDINE";
    private boolean attiva = false;

    public VerticalizzazioneCartograficoAttivoServiceImpl(VerticalizzazioniService service) {

	this.service = service;
	this.attiva = this.isAttivaInternal();
    }

    @Override
    public boolean isAttiva() {

	return this.attiva;
    }

    @Override
    public String connettore() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(MESSAGGIO_ERRORE);
	}
	Verticalizzazioniparametri parametro = this.service.getVerticalizzazioniparametri(NOME_VERTICALIZZAZIONE, CONNETTORE);
	if (parametro != null) {
	    return parametro.getValore();
	}
	return null;
    }

    @Override
    public String serviceUrl() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(MESSAGGIO_ERRORE);
	}
	Verticalizzazioniparametri parametro = this.service.getVerticalizzazioniparametri(NOME_VERTICALIZZAZIONE, SERVICE_URL);
	if (parametro != null) {
	    return parametro.getValore();
	}
	return null;
    }

    private boolean isAttivaInternal() {

	return this.service.isAttiva(VerticalizzazioneCartograficoAttivoServiceImpl.NOME_VERTICALIZZAZIONE);
    }

    @Override
    public String modelloAltriDati() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(MESSAGGIO_ERRORE);
	}
	Verticalizzazioniparametri parametro = this.service.getVerticalizzazioniparametri(NOME_VERTICALIZZAZIONE, MODELLO_ALTRI_DATI);
	if (parametro != null) {
	    return parametro.getValore();
	}
	return null;
    }

    @Override
    public String campoAltriDati() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(MESSAGGIO_ERRORE);
	}
	Verticalizzazioniparametri parametro = this.service.getVerticalizzazioniparametri(NOME_VERTICALIZZAZIONE, CAMPO_ALTRI_DATI);
	if (parametro != null) {
	    return parametro.getValore();
	}
	return null;
    }

    @Override
    public int posizioneLatitudine() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(MESSAGGIO_ERRORE);
	}
	Verticalizzazioniparametri parametro = this.service.getVerticalizzazioniparametri(NOME_VERTICALIZZAZIONE, POSIZIONE_LATITUDINE);
	if (parametro != null && !StringUtils.isBlank(parametro.getValore())) {
	    return Integer.parseInt(parametro.getValore());
	}
	return 0;
    }

    @Override
    public int posizioneLongitudine() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(MESSAGGIO_ERRORE);
	}
	Verticalizzazioniparametri parametro = this.service.getVerticalizzazioniparametri(NOME_VERTICALIZZAZIONE, POSIZIONE_LONGITUDINE);
	if (parametro != null && !StringUtils.isBlank(parametro.getValore())) {
	    return Integer.parseInt(parametro.getValore());
	}
	return 1;
    }
}
