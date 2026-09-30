package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.CategoriaaffissioniDAO;
import it.gruppoinit.pal.gp.core.domain.Categoriaaffissioni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.CategoriaaffissioniService;

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
public class CategoriaaffissioniServiceImpl extends BaseServiceImpl<Categoriaaffissioni, PkId> implements CategoriaaffissioniService {

    private CategoriaaffissioniDAO categoriaaffissioniDAO;

    @Autowired
    public void setCategoriaaffissioniDAO(CategoriaaffissioniDAO categoriaaffissioniDAO) {

	this.categoriaaffissioniDAO = categoriaaffissioniDAO;
    }

    @Override
    protected Class<Categoriaaffissioni> getEntityClass() {

	return Categoriaaffissioni.class;
    }

    @Override
    public List<Categoriaaffissioni> findAll(Integer firstResult, Integer maxResult) {

	return categoriaaffissioniDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Categoriaaffissioni entity) {

	if (validateEntity(entity)) {
	    categoriaaffissioniDAO.insert(entity);
	}
    }

    @Override
    public Categoriaaffissioni findById(PkId id) {

	return categoriaaffissioniDAO.findById(id);
    }

    @Override
    public void update(Categoriaaffissioni entity) {

	if (validateEntity(entity)) {
	    categoriaaffissioniDAO.update(entity);
	}
    }

    @Override
    public void delete(Categoriaaffissioni entity) {

	if (isDeleteAllowed(entity)) {
	    categoriaaffissioniDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Categoriaaffissioni entity) {

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
