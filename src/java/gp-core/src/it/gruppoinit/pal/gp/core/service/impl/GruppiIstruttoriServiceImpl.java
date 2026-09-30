package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.GruppiIstruttoriDAO;
import it.gruppoinit.pal.gp.core.domain.GruppiIstruttori;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.GruppiIstruttoriService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class GruppiIstruttoriServiceImpl extends BaseServiceImpl<GruppiIstruttori, PkId> implements GruppiIstruttoriService {

    private GruppiIstruttoriDAO gruppiistruttoriDAO;

    @Autowired
    public void setGruppiIstruttoriDAO(GruppiIstruttoriDAO gruppiistruttoriDAO) {

	this.gruppiistruttoriDAO = gruppiistruttoriDAO;
    }

    @Override
    protected Class<GruppiIstruttori> getEntityClass() {

	return GruppiIstruttori.class;
    }

    @Override
    public List<GruppiIstruttori> findAll(Integer firstResult, Integer maxResult) {

	return gruppiistruttoriDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(GruppiIstruttori entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    gruppiistruttoriDAO.insert(entity);
	}
    }

    @Override
    public GruppiIstruttori findById(PkId id) {

	return gruppiistruttoriDAO.findById(id);
    }

    @Override
    public void update(GruppiIstruttori entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    gruppiistruttoriDAO.update(entity);
	}
    }

    @Override
    public void delete(GruppiIstruttori entity) {

	if (isDeleteAllowed(entity)) {
	    gruppiistruttoriDAO.delete(entity);
	}
    }

    @Override
    public List<GruppiIstruttori> findByDescrizione(String textToSearch) {

	return gruppiistruttoriDAO.findByDescrizione(textToSearch);
    }

    private void dataIntegration(GruppiIstruttori entity) {

	//fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(GruppiIstruttori entity) {

	// TODO Auto-generated method stub
	super.fixMergeEntityProperties(entity);
    }
    //    protected boolean isDeleteAllowed(GruppiIstruttori entity) {
    //
    //		boolean delete = true;
    //		List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
    //		TODO_validare_la_delete
    //		// esempio:
    //		// if (entity.getList().size() > 0) {
    //		//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
    //		// }
    //		if (!_ivs.isEmpty()) {
    //			this.throwValidationMessages(_ivs);
    //		}
    //		return delete;
    //    }
}
