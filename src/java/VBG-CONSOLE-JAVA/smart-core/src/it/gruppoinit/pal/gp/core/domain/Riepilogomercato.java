package it.gruppoinit.pal.gp.core.domain;

import java.util.List;

/**
 * 
 * @author gianpaolot Bean non appartente al dominio, utilizzato per la visulaizzazione di contabilità mercati
 */
public class Riepilogomercato {

    private Mercati mercati;
    private MercatiUso mercatiUso;
    private List<SituazioneContabile> situazionecontabileList;

    public Mercati getMercati() {

	return mercati;
    }

    public void setMercati(Mercati mercati) {

	this.mercati = mercati;
    }

    public List<SituazioneContabile> getSituazionecontabileList() {

	return situazionecontabileList;
    }

    public void setSituazionecontabileList(List<SituazioneContabile> situazionecontabileList) {

	this.situazionecontabileList = situazionecontabileList;
    }

    public void setMercatiUso(MercatiUso mercatiUso) {

	this.mercatiUso = mercatiUso;
    }

    public MercatiUso getMercatiUso() {

	return mercatiUso;
    }
}
