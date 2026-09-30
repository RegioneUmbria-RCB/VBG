package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.IstanzeaffissioniDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Istanzeaffissioni;
import it.gruppoinit.pal.gp.core.domain.IstanzeaffissioniId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.IstanzeaffissioniService;

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
public class IstanzeaffissioniServiceImpl extends BaseServiceImpl<Istanzeaffissioni, IstanzeaffissioniId> implements IstanzeaffissioniService {

    private IstanzeaffissioniDAO istanzeaffissioniDAO;

    @Autowired
    public void setIstanzeaffissioniDAO(IstanzeaffissioniDAO istanzeaffissioniDAO) {

	this.istanzeaffissioniDAO = istanzeaffissioniDAO;
    }

    @Override
    protected Class<Istanzeaffissioni> getEntityClass() {

	return Istanzeaffissioni.class;
    }

    @Override
    public List<Istanzeaffissioni> findAll(Integer firstResult, Integer maxResult) {

	return istanzeaffissioniDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Istanzeaffissioni entity) {

	if (validateEntity(entity)) {
	    istanzeaffissioniDAO.insert(entity);
	}
    }

    @Override
    public Istanzeaffissioni findById(IstanzeaffissioniId id) {

	return istanzeaffissioniDAO.findById(id);
    }

    @Override
    public void update(Istanzeaffissioni entity) {

	if (validateEntity(entity)) {
	    istanzeaffissioniDAO.update(entity);
	}
    }

    @Override
    public void delete(Istanzeaffissioni entity) {

	if (isDeleteAllowed(entity)) {
	    istanzeaffissioniDAO.delete(entity);
	}
    }
    

    @Override
    public List<Istanzeaffissioni> findByIstanze(Integer codice) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codice, "istanze", Integer.class));
	ft.addRestriction(fr);
	return istanzeaffissioniDAO.findByFilterTable(ft);
    }

    protected boolean isDeleteAllowed(Istanzeaffissioni entity) {

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
