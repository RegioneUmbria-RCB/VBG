package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.ProtTipidocumentoMetadatiDAO;
import it.gruppoinit.pal.gp.core.domain.ProtTipidocumentoMetadati;
import it.gruppoinit.pal.gp.core.domain.ProtTipidocumentoMetadatiId;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;

import java.util.List;

/**
 * 
 * @author
 */
public interface ProtTipidocumentoMetadatiService extends BaseService<ProtTipidocumentoMetadati, ProtTipidocumentoMetadatiId> {

    /**
     * @see ProtTipidocumentoMetadatiDAO#findAll(Integer, Integer)
     */
    public List<ProtTipidocumentoMetadati> findAll(Integer firstResult, Integer maxResult);

    public List<ProtTipidocumentoMetadati> findByProtTipiDocumento(Integer codiceProtTipoDocumento);

    public List<CodiceDescrizioneBean> findByTipoDocumentoCodice(String codiceTipoDocumento, String codiceComune, String software);
}
