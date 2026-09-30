package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Istanzeprocure;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeprocureDTO;
import it.gruppoinit.pal.gp.core.service.helper.TipoRicercaDocumentoEnum;

import java.util.List;

/**
 * 
 * @author
 */
public interface IstanzeprocureDAO extends BaseDAO<Istanzeprocure, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<Istanzeprocure> findAll(Integer firstResult, Integer maxResult);

    public List<IstanzeprocureDTO> findIstanzeprocureDTOByIstanza(Integer codiceIstanza);

    public List<IstanzeprocureDTO> findIstanzeprocureDTOByIstanza(Integer codiceIstanza, TipoRicercaDocumentoEnum tipoRicercaDocumentoEnum);
}
