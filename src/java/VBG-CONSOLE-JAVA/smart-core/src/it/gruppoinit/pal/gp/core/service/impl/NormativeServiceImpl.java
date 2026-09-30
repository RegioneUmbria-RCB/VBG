package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.NormativeDAO;
import it.gruppoinit.pal.gp.core.domain.Normative;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.NormativeService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author gianpaolot
 */
@Service
public class NormativeServiceImpl extends BaseServiceImpl<Normative, PkId> implements NormativeService {

    private NormativeDAO normativeDAO;

    @Autowired
    public void setNormativeDAO(NormativeDAO normativeDAO) {

	this.normativeDAO = normativeDAO;
    }

    @Override
    protected Class<Normative> getEntityClass() {

	return Normative.class;
    }

    @Override
    public List<Normative> findAll(Integer firstResult, Integer maxResult) {

	return normativeDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Normative entity) {

	if (validateEntity(entity)) {
	    normativeDAO.insert(entity);
	}
    }

    @Override
    public Normative findById(PkId id) {

	return normativeDAO.findById(id);
    }

    @Override
    public void update(Normative entity) {

	if (validateEntity(entity)) {
	    normativeDAO.update(entity);
	}
    }

    @Override
    public void delete(Normative entity) {

	if (isDeleteAllowed(entity)) {
	    normativeDAO.delete(entity);
	}
    }

    @Override
    public List<Normative> findByNormativa(String normativa) {

	return normativeDAO.findByNormativa(normativa);
    }
    // protected boolean isDeleteAllowed(Normative entity) {
    //
    // boolean delete = true;
    // List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
    // TODO_validare_la_delete
    // // esempio:
    // // if (entity.getList().size() > 0) {
    // // _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
    // // }
    // if (!_ivs.isEmpty()) {
    // this.throwValidationMessages(_ivs);
    // }
    // return delete;
    // }
}
