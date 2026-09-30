package it.gruppoinit.pal.gp.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.ComuniassociatiesclusioniDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.ComuniAssociatiEsclusioni;
import it.gruppoinit.pal.gp.core.domain.ComuniAssociatiEsclusioniId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiesclusioniService;

/**
 * 
 * @author
 */
@Service
public class ComuniassociatiesclusioniServiceImpl extends BaseServiceImpl<ComuniAssociatiEsclusioni, ComuniAssociatiEsclusioniId>
	implements ComuniassociatiesclusioniService {

    private ComuniassociatiesclusioniDAO comuniassociatiesclusioniDAO;

    @Autowired
    public void setComuniassociatiesclusioniDAO(ComuniassociatiesclusioniDAO comuniassociatiesclusioniDAO) {

	this.comuniassociatiesclusioniDAO = comuniassociatiesclusioniDAO;
    }

    @Override
    protected Class<ComuniAssociatiEsclusioni> getEntityClass() {

	return ComuniAssociatiEsclusioni.class;
    }

    @Override
    public List<ComuniAssociatiEsclusioni> findAll(Integer firstResult, Integer maxResult) {

	return comuniassociatiesclusioniDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(ComuniAssociatiEsclusioni entity) {

	if (validateEntity(entity)) {
	    comuniassociatiesclusioniDAO.insert(entity);
	}
    }

    @Override
    public void update(ComuniAssociatiEsclusioni entity) {

	if (validateEntity(entity)) {
	    comuniassociatiesclusioniDAO.update(entity);
	}
    }

    @Override
    public void delete(ComuniAssociatiEsclusioni entity) {

	if (isDeleteAllowed(entity)) {
	    comuniassociatiesclusioniDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(ComuniAssociatiEsclusioni entity) {

	boolean delete = true;
	//		List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	//		TODO_validare_la_delete
	//		// esempio:
	//		// if (entity.getList().size() > 0) {
	//		//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	//		// }
	//		if (!_ivs.isEmpty()) {
	//			this.throwValidationMessages(_ivs);
	//		}
	return delete;
    }

    @Override
    public List<ComuniAssociatiEsclusioni> findByIdComuneandSoftware(String idcomune, String software) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.idcomune", idcomune, String.class));
	fr.addFilterField(FilterUtils.equals("id.software", software, String.class));
	ft.addRestriction(fr);
	return comuniassociatiesclusioniDAO.findByFilterTable(ft);
    }

    @Override
    public ComuniAssociatiEsclusioni findById(ComuniAssociatiEsclusioniId id) {

	return comuniassociatiesclusioniDAO.findById(id);
    }
}
