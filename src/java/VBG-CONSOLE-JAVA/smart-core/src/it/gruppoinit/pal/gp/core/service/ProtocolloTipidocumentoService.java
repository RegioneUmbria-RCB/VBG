package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.ProtocolloTipidocumentoDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ProtocolloTipidocumento;

import java.util.List;

/**
 * 
 * @author
 */
public interface ProtocolloTipidocumentoService extends BaseService<ProtocolloTipidocumento, PkId> {

    /**
     * @see ProtocolloTipidocumentoDAO#findAll(Integer, Integer)
     */
    public List<ProtocolloTipidocumento> findAll(Integer firstResult, Integer maxResult);

    /**
     * Ritorna una lista di ProtocolloTipidocumento filtrati per descrizione (ilike) e ordinati per descriscrione
     * 
     * @param textToSearch
     * @return
     */
    public List<ProtocolloTipidocumento> findByDescrizione(String textToSearch);

    /**
     * Ritorna una lista di tutti i record filtrando per idcomune e ordinando per comune e software
     * 
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<ProtocolloTipidocumento> findAllOrederByComuneAndSoftware(Integer firstResult, Integer maxResult);
}
