package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.CittadinanzaDAO;
import it.gruppoinit.pal.gp.core.domain.Cittadinanza;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.service.CittadinanzaService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author gianpaolot
 */
@Service
public class CittadinanzaServiceImpl extends BaseServiceImpl<Cittadinanza, Integer> implements CittadinanzaService {

    private CittadinanzaDAO cittadinanzaDAO;

    @Autowired
    public void setCittadinanzaDAO(CittadinanzaDAO cittadinanzaDAO) {

	this.cittadinanzaDAO = cittadinanzaDAO;
    }

    @Override
    protected Class<Cittadinanza> getEntityClass() {

	return Cittadinanza.class;
    }

    @Override
    public List<Cittadinanza> findAll(Integer firstResult, Integer maxResult) {

	return cittadinanzaDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Cittadinanza entity) {

	if (validateEntity(entity)) {
	    cittadinanzaDAO.insert(entity);
	}
    }

    @Override
    public Cittadinanza findById(Integer id) {

	return cittadinanzaDAO.findById(id);
    }

    @Override
    public void update(Cittadinanza entity) {

	if (validateEntity(entity)) {
	    cittadinanzaDAO.update(entity);
	}
    }

    @Override
    public void delete(Cittadinanza entity) {

	if (isDeleteAllowed(entity)) {
	    cittadinanzaDAO.delete(entity);
	}
    }

    @Override
    public List<Cittadinanza> findByFilterTable(FilterTable filterTable) {

	return cittadinanzaDAO.findByFilterTable(filterTable);
    }
    // protected boolean isDeleteAllowed(Cittadinanza entity) {
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

    @Override
    public List<Cittadinanza> findByDescrizione(String term) {

	return cittadinanzaDAO.findByDescrizione(term);
    }
}
