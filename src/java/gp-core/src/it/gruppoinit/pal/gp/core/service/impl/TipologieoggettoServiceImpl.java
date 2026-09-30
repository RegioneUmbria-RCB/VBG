package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.TipologieoggettoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipologieoggetto;
import it.gruppoinit.pal.gp.core.service.TipologieoggettoService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipologieoggettoServiceImpl extends BaseServiceImpl<Tipologieoggetto, PkId> implements TipologieoggettoService {

    private TipologieoggettoDAO tipologieoggettoDAO;

    @Autowired
    public void setTipologieoggettoDAO(TipologieoggettoDAO tipologieoggettoDAO) {

	this.tipologieoggettoDAO = tipologieoggettoDAO;
    }

    @Override
    protected Class<Tipologieoggetto> getEntityClass() {

	return Tipologieoggetto.class;
    }

    @Override
    public void delete(Tipologieoggetto entity) {

	if (isDeleteAllowed(entity)) {
	    tipologieoggettoDAO.delete(entity);
	}
    }

    @Override
    public List<Tipologieoggetto> findAll(Integer firstResult, Integer maxResult) {

	return tipologieoggettoDAO.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "descrizionetipologia", DAOOrderTypeEnum.ASC);
    }

    @Override
    public Tipologieoggetto findById(PkId id) {

	return tipologieoggettoDAO.findById(id);
    }

    @Override
    public void insert(Tipologieoggetto entity) {

	if (validateEntity(entity)) {
	    tipologieoggettoDAO.insert(entity);
	}
    }

    @Override
    public void update(Tipologieoggetto entity) {

	if (validateEntity(entity)) {
	    tipologieoggettoDAO.update(entity);
	}
    }

    @Override
    protected boolean isDeleteAllowed(Tipologieoggetto entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (entity.getOggettiinfos().size() > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "OGGETTIINFO", null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }
}
