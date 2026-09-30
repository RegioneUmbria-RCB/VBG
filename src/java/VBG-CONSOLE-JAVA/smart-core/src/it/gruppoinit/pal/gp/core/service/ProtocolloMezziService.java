package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.ProtocolloMezziDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ProtocolloMezzi;

import java.util.List;

/**
 * 
 * @author
 */
public interface ProtocolloMezziService extends BaseService<ProtocolloMezzi, PkId> {

    /**
     * @see ProtocolloMezziDAO#findAll(Integer, Integer)
     */
    public List<ProtocolloMezzi> findAll(Integer firstResult, Integer maxResult);

    public ProtocolloMezzi findByCodiceMezzoAndComune(String codiceMezzo, String codiceComune);

    public List<ProtocolloMezzi> findByComuneAndSoftware(String codicecomune, String software);

    public ProtocolloMezzi findByCodiceMezzoAndComuneAndSoftware(String mezzoDefaultCodice, String codiceComune, String software);
}
