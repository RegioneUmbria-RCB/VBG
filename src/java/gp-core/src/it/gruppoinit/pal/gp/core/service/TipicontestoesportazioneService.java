package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.TipicontestoesportazioneDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipicontestoesportazione;

/**
 * 
 * @author
 */
public interface TipicontestoesportazioneService extends BaseService<Tipicontestoesportazione, PkId> {

    /**
     * @see TipicontestoesportazioneDAO#findAll(Integer, Integer)
     */
    public List<Tipicontestoesportazione> findAll(Integer firstResult, Integer maxResult);

    public Tipicontestoesportazione findByCodice(String codiceTipoContesto);
}
