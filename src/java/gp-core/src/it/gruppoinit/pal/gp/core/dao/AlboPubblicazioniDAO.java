package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.AlboPretorioFilter;
import it.gruppoinit.pal.gp.core.domain.AlboPubblicazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

public interface AlboPubblicazioniDAO extends BaseDAO<it.gruppoinit.pal.gp.core.domain.AlboPubblicazioni, PkId> {

    public List<AlboPubblicazioni> findAllFilter(AlboPretorioFilter alboPretorioFilter);

    /**
     * Restituisce le Pubblicazioni (filtrando per idcomune e software) ordinandole per il campo codice ascendente
     */
    public List<AlboPubblicazioni> findAll(Integer firstResult, Integer maxResult);

    public List<AlboPubblicazioni> findPublicazioniValideAL(AlboPretorioFilter alboPretorioFilter);
}
