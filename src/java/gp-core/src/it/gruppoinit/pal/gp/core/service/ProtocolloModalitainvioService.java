package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.ProtocolloModalitainvioDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ProtocolloModalitainvio;

import java.util.List;

/**
 * 
 * @author
 */
public interface ProtocolloModalitainvioService extends BaseService<ProtocolloModalitainvio, PkId> {

    /**
     * @see ProtocolloModalitainvioDAO#findAll(Integer, Integer)
     */
    public List<ProtocolloModalitainvio> findAll(Integer firstResult, Integer maxResult);

    public ProtocolloModalitainvio findByCodiceModalitaAndComune(String codiceModalita, String codiceComune);

    public List<ProtocolloModalitainvio> findByComuneAndSoftware(String codicecomune, String software);

    public ProtocolloModalitainvio findByCodiceModalitaAndComuneAndSoftware(String codiceModalita, String codiceComune, String software);
}
