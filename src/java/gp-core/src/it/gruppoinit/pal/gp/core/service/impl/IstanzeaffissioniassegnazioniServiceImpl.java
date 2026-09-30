package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.IstanzeaffissioniassegnazioniDAO;
import it.gruppoinit.pal.gp.core.domain.Istanzeaffissioniassegnazioni;
import it.gruppoinit.pal.gp.core.domain.IstanzeaffissioniassegnazioniId;
import it.gruppoinit.pal.gp.core.service.IstanzeaffissioniassegnazioniService;

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
public class IstanzeaffissioniassegnazioniServiceImpl extends BaseServiceImpl<Istanzeaffissioniassegnazioni, IstanzeaffissioniassegnazioniId>
	implements IstanzeaffissioniassegnazioniService {

    private IstanzeaffissioniassegnazioniDAO istanzeaffissioniassegnazioniDAO;

    @Autowired
    public void setIstanzeaffissioniassegnazioniDAO(IstanzeaffissioniassegnazioniDAO istanzeaffissioniassegnazioniDAO) {

	this.istanzeaffissioniassegnazioniDAO = istanzeaffissioniassegnazioniDAO;
    }

    @Override
    protected Class<Istanzeaffissioniassegnazioni> getEntityClass() {

	return Istanzeaffissioniassegnazioni.class;
    }

    @Override
    public List<Istanzeaffissioniassegnazioni> findAll(Integer firstResult, Integer maxResult) {

	return istanzeaffissioniassegnazioniDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Istanzeaffissioniassegnazioni entity) {

	if (validateEntity(entity)) {
	    istanzeaffissioniassegnazioniDAO.insert(entity);
	}
    }

    @Override
    public Istanzeaffissioniassegnazioni findById(IstanzeaffissioniassegnazioniId id) {

	return istanzeaffissioniassegnazioniDAO.findById(id);
    }

    @Override
    public void update(Istanzeaffissioniassegnazioni entity) {

	if (validateEntity(entity)) {
	    istanzeaffissioniassegnazioniDAO.update(entity);
	}
    }

    @Override
    public void delete(Istanzeaffissioniassegnazioni entity) {

	if (isDeleteAllowed(entity)) {
	    istanzeaffissioniassegnazioniDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Istanzeaffissioniassegnazioni entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// TODO _validare_la_delete
	// esempio:
	// if (entity.getList().size() > 0) {
	// _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	// }
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }
}
