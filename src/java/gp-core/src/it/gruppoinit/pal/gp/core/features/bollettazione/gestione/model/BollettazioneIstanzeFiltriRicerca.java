package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.model;

import java.util.List;

import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.IntervalloDate;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.RichiestaCalcoloBollettazioneIstanza;

public class BollettazioneIstanzeFiltriRicerca {

    private List<String> filtriScCodice;
    private List<Integer> filtriCodiceEndo;
    private List<Integer> filtriCausaleOnere;
    private List<String> filtriCodiceComune;
    private IntervalloDate intervalloDate;

    public static BollettazioneIstanzeFiltriRicerca fromRichiestaCalcoloBollettazioneIstanza(RichiestaCalcoloBollettazioneIstanza request) {

	BollettazioneIstanzeFiltriRicerca ret = new BollettazioneIstanzeFiltriRicerca();
	ret.filtriCausaleOnere = request.getFiltriCausaleOnere();
	ret.filtriCodiceComune = request.getFiltriCodiceComune();
	ret.filtriCodiceEndo = request.getFiltriCodiceEndo();
	ret.intervalloDate = request.getIntervalloDate();
	ret.filtriScCodice = request.getFiltriScCodice();
	return ret;
    }

    public List<String> getFiltriScCodice() {

	return filtriScCodice;
    }

    public List<Integer> getFiltriCodiceEndo() {

	return filtriCodiceEndo;
    }

    public List<Integer> getFiltriCausaleOnere() {

	return filtriCausaleOnere;
    }

    public List<String> getFiltriCodiceComune() {

	return filtriCodiceComune;
    }

    public IntervalloDate getIntervalloDate() {

	return intervalloDate;
    }
}
