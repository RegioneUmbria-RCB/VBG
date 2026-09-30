package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.ProtTipidocumentoMetadati;
import it.gruppoinit.pal.gp.core.domain.ProtTipidocumentoMetadatiId;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;

import java.util.List;

/**
 * 
 * @author
 */
public interface ProtTipidocumentoMetadatiDAO extends BaseDAO<ProtTipidocumentoMetadati, ProtTipidocumentoMetadatiId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<ProtTipidocumentoMetadati> findAll(Integer firstResult, Integer maxResult);

    public List<CodiceDescrizioneBean> findByTipoDocumentoCodice(String codiceTipoDocumento, String codiceComune, String software);
}
