package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.ProtTipidocumentoMetadatiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.ProtTipidocumentoMetadati;
import it.gruppoinit.pal.gp.core.domain.ProtTipidocumentoMetadatiId;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ProtTipidocumentoMetadatiService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class ProtTipidocumentoMetadatiServiceImpl extends BaseServiceImpl<ProtTipidocumentoMetadati, ProtTipidocumentoMetadatiId> implements
	ProtTipidocumentoMetadatiService {

    private ProtTipidocumentoMetadatiDAO prottipidocumentometadatiDAO;

    @Autowired
    public void setProtTipidocumentoMetadatiDAO(ProtTipidocumentoMetadatiDAO prottipidocumentometadatiDAO) {

	this.prottipidocumentometadatiDAO = prottipidocumentometadatiDAO;
    }

    @Override
    protected Class<ProtTipidocumentoMetadati> getEntityClass() {

	return ProtTipidocumentoMetadati.class;
    }

    @Override
    public List<ProtTipidocumentoMetadati> findAll(Integer firstResult, Integer maxResult) {

	return prottipidocumentometadatiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(ProtTipidocumentoMetadati entity) {

	if (validateEntity(entity)) {
	    prottipidocumentometadatiDAO.insert(entity);
	}
    }

    @Override
    public ProtTipidocumentoMetadati findById(ProtTipidocumentoMetadatiId id) {

	return prottipidocumentometadatiDAO.findById(id);
    }

    @Override
    public void update(ProtTipidocumentoMetadati entity) {

	if (validateEntity(entity)) {
	    prottipidocumentometadatiDAO.update(entity);
	}
    }

    @Override
    public void delete(ProtTipidocumentoMetadati entity) {

	if (isDeleteAllowed(entity)) {
	    prottipidocumentometadatiDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(ProtTipidocumentoMetadati entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// esempio:
	// if (entity.getList().size() > 0) {
	//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	// }
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<ProtTipidocumentoMetadati> findByProtTipiDocumento(Integer codiceProtTipoDocumento) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.fkidprottpdoc", codiceProtTipoDocumento, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("label", "metadato"));
	ft.addOrder(FilterUtils.orderAsc("id.fkidmetadatidizbase"));
	return prottipidocumentometadatiDAO.findByFilterTable(ft);
    }

    @Override
    public List<CodiceDescrizioneBean> findByTipoDocumentoCodice(String codiceTipoDocumento, String codiceComune, String software) {

	return prottipidocumentometadatiDAO.findByTipoDocumentoCodice(codiceTipoDocumento, codiceComune, software);
    }
}
