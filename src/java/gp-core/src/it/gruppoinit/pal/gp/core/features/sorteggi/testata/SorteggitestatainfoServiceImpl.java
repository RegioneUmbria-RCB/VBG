package it.gruppoinit.pal.gp.core.features.sorteggi.testata;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Sorteggitestatainfo;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;

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
public class SorteggitestatainfoServiceImpl extends BaseServiceImpl<Sorteggitestatainfo, PkId> implements SorteggitestatainfoService {

    private SorteggitestatainfoDAO sorteggitestatainfoDAO;

    @Autowired
    public void setSorteggitestatainfoDAO(SorteggitestatainfoDAO sorteggitestatainfoDAO) {

	this.sorteggitestatainfoDAO = sorteggitestatainfoDAO;
    }

    @Override
    protected Class<Sorteggitestatainfo> getEntityClass() {

	return Sorteggitestatainfo.class;
    }

    @Override
    public List<Sorteggitestatainfo> findAll(Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	return sorteggitestatainfoDAO.findAll(firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void insert(Sorteggitestatainfo entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    sorteggitestatainfoDAO.insert(entity);
	}
	// §§§END§§§
    }

    @Override
    public Sorteggitestatainfo findById(PkId id) {

	// §§§BEGIN§§§
	return sorteggitestatainfoDAO.findById(id);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void update(Sorteggitestatainfo entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    sorteggitestatainfoDAO.update(entity);
	}
	// §§§END§§§
    }

    @Override
    public void delete(Sorteggitestatainfo entity) {

	// §§§BEGIN§§§
	if (isDeleteAllowed(entity)) {
	    sorteggitestatainfoDAO.delete(entity);
	}
	// §§§END§§§
    }

    protected boolean isDeleteAllowed(Sorteggitestatainfo entity) {

	boolean delete = true;
	// §§§BEGIN§§§
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	//TODO_validare_la_delete
	// esempio:
	// if (entity.getList().size() > 0) {
	//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	// }
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	// §§§END§§§
	return delete;
    }
}
