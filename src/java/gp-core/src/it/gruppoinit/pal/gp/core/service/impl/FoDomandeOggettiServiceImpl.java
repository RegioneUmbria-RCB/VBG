package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.FoDomandeOggettiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.FoDomandeOggetti;
import it.gruppoinit.pal.gp.core.domain.FoDomandeOggettiId;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.FoDomandeOggettiService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Luca Proietti
 */
@Service
public class FoDomandeOggettiServiceImpl extends BaseServiceImpl<FoDomandeOggetti, FoDomandeOggettiId> implements FoDomandeOggettiService {

    private FoDomandeOggettiDAO fodomandeoggettiDAO;
    private OggettiService oggettiService;

    @Autowired
    public void setFoDomandeOggettiDAO(FoDomandeOggettiDAO fodomandeoggettiDAO) {

	this.fodomandeoggettiDAO = fodomandeoggettiDAO;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Override
    protected Class<FoDomandeOggetti> getEntityClass() {

	return FoDomandeOggetti.class;
    }

    @Override
    public List<FoDomandeOggetti> findAll(Integer firstResult, Integer maxResult) {

	return fodomandeoggettiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(FoDomandeOggetti entity) {

	if (validateEntity(entity)) {
	    fodomandeoggettiDAO.insert(entity);
	}
    }

    @Override
    public FoDomandeOggetti findById(FoDomandeOggettiId id) {

	return fodomandeoggettiDAO.findById(id);
    }

    @Override
    public void update(FoDomandeOggetti entity) {

	if (validateEntity(entity)) {
	    fodomandeoggettiDAO.update(entity);
	}
    }

    @Override
    public void delete(FoDomandeOggetti entity) {

	if (isDeleteAllowed(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetti", true, entity.getId());
	    fodomandeoggettiDAO.delete(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    protected boolean isDeleteAllowed(FoDomandeOggetti entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	//TODO_validare_la_delete
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
    public List<FoDomandeOggetti> findByIdDomandaFo(Integer idDomandaFo) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.iddomanda", idDomandaFo, Integer.class));
	ft.addRestriction(fr);
	return fodomandeoggettiDAO.findByFilterTable(ft);
    }
}
