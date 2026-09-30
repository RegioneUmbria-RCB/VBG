package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.AlboPubblicazioniDAO;
import it.gruppoinit.pal.gp.core.domain.AlboPretorioFilter;
import it.gruppoinit.pal.gp.core.domain.AlboPubblicazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

public interface AlboPubblicazioniService extends BaseService<AlboPubblicazioni, PkId> {

    public List<AlboPubblicazioni> findAllMaxResult(AlboPretorioFilter alboPretorioFilter);

    public List<AlboPubblicazioni> findAllFilter(AlboPretorioFilter alboPretorioFilter);

    /**
     * @see AlboPubblicazioniDAO#findAll(Integer, Integer)
     */
    public List<AlboPubblicazioni> findAll(Integer firstResult, Integer maxResult);

    public List<AlboPubblicazioni> findPublicazioniValideAL(AlboPretorioFilter alboPretorioFilter);

    /**
     * Torna la lista delle AlboPubblicazioni di un'Amministrazione
     * 
     * @param codiceAmministrazione
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<AlboPubblicazioni> findByAmministrazioni(Integer codiceAmministrazione, Integer firstResult, Integer maxResult);
}
