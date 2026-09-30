package it.gruppoinit.pal.gp.core.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipicontestoesportazione;

/**
 * 
 * @author
 */
public interface TipicontestoesportazioneDAO extends BaseDAO<Tipicontestoesportazione, PkId> {

    public List<Tipicontestoesportazione> findAll(Integer firstResult, Integer maxResult);

    public Tipicontestoesportazione findByCodice(String codiceTipoContesto);
}
