package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ProtocolloTipidocumento;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface ProtocolloTipidocumentoDAO extends BaseDAO<ProtocolloTipidocumento, PkId> {

    public List<ProtocolloTipidocumento> findAll(Integer firstResult, Integer maxResult);
}
