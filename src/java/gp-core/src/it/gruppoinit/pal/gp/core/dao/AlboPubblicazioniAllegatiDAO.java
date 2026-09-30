package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.AlboPubblicazioni;
import it.gruppoinit.pal.gp.core.domain.AlboPubblicazioniAllegati;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

public interface AlboPubblicazioniAllegatiDAO extends BaseDAO<AlboPubblicazioniAllegati, PkId> {

    /**
     * Recupero oggetto dalla tabella AlboPubblicazioneAllegati
     * 
     * @param codiceoggetto
     * @param codicepubblicazione
     * @return
     */
    public Oggetti findByPubblicazioneEOggetti(Oggetti oggetti, AlboPubblicazioni alboPubblicazioni);

    /**
     * 
     * @param alboPubblicazioni
     * @return una lista di albopubblicazioniAllegati ordinati per il campo ordine
     */
    public List<AlboPubblicazioniAllegati> findOrderByOrdine(AlboPubblicazioni alboPubblicazioni);
}
