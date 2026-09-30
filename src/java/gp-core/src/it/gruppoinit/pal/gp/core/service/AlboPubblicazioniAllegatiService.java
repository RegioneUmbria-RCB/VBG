package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.AlboPubblicazioni;
import it.gruppoinit.pal.gp.core.domain.AlboPubblicazioniAllegati;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

public interface AlboPubblicazioniAllegatiService extends BaseService<AlboPubblicazioniAllegati, PkId> {

    /**
     * Recupero oggetto dalla tabella AlboPubblicazioneAllegati
     * 
     * @param codiceoggetto
     * @param codicepubblicazione
     * @return
     */
    public Oggetti findByPubblicazioneEOggetti(int codiceoggetto, int codicepubblicazione);

    /**
     * 
     * @param alboPubblicazioni
     * @return una lista di albopubblicazioniAllegati ordinati per il campo ordine
     */
    public List<AlboPubblicazioniAllegati> findOrderByOrdine(AlboPubblicazioni alboPubblicazioni);
}
