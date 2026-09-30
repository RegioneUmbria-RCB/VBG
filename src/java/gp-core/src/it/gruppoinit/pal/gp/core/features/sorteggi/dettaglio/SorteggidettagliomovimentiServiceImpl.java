package it.gruppoinit.pal.gp.core.features.sorteggi.dettaglio;

import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Sorteggidettaglio;
import it.gruppoinit.pal.gp.core.domain.Sorteggidettagliomovimenti;
import it.gruppoinit.pal.gp.core.domain.SorteggidettagliomovimentiId;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
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
public class SorteggidettagliomovimentiServiceImpl extends BaseServiceImpl<Sorteggidettagliomovimenti, SorteggidettagliomovimentiId> implements
	SorteggidettagliomovimentiService {

    private SorteggidettagliomovimentiDAO sorteggidettagliomovimentiDAO;
    private SorteggidettaglioService sorteggidettaglioService;
    private MovimentiService movimentiService;

    @Autowired
    public void setSorteggidettagliomovimentiDAO(SorteggidettagliomovimentiDAO sorteggidettagliomovimentiDAO) {

	this.sorteggidettagliomovimentiDAO = sorteggidettagliomovimentiDAO;
    }

    @Autowired
    public void setSorteggidettaglioService(SorteggidettaglioService sorteggidettaglioService) {

	this.sorteggidettaglioService = sorteggidettaglioService;
    }

    @Autowired
    public void setMovimentiService(MovimentiService movimentiService) {

	this.movimentiService = movimentiService;
    }

    @Override
    protected Class<Sorteggidettagliomovimenti> getEntityClass() {

	return Sorteggidettagliomovimenti.class;
    }

    @Override
    public List<Sorteggidettagliomovimenti> findAll(Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	return sorteggidettagliomovimentiDAO.findAll(firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void insert(Sorteggidettagliomovimenti entity) {

	// §§§BEGIN§§§
	dataIntegration(entity);
	if (validateEntity(entity)) {
	    sorteggidettagliomovimentiDAO.insert(entity);
	}
	// §§§END§§§
    }

    @Override
    public Sorteggidettagliomovimenti findById(SorteggidettagliomovimentiId id) {

	// §§§BEGIN§§§
	return sorteggidettagliomovimentiDAO.findById(id);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void update(Sorteggidettagliomovimenti entity) {

	// §§§BEGIN§§§
	dataIntegration(entity);
	if (validateEntity(entity)) {
	    sorteggidettagliomovimentiDAO.update(entity);
	}
	// §§§END§§§
    }

    @Override
    public void delete(Sorteggidettagliomovimenti entity) {

	// §§§BEGIN§§§
	if (isDeleteAllowed(entity)) {
	    sorteggidettagliomovimentiDAO.delete(entity);
	}
	// §§§END§§§
    }

    protected boolean isDeleteAllowed(Sorteggidettagliomovimenti entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// TODO_validare_la_delete
	// esempio:
	// if (entity.getList().size() > 0) {
	// _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	// }
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    private void dataIntegration(Sorteggidettagliomovimenti entity) {

	// §§§BEGIN§§§
	if (entity == null) {
	    throw new RuntimeException("Il parametro sorteggidettagliomovimenti è nullo");
	}
	fixMergeEntityProperties(entity);
	// §§§END§§§
    }

    protected void fixMergeEntityProperties(Sorteggidettagliomovimenti entity) {

	// §§§BEGIN§§§
	Sorteggidettaglio sorteggidettaglio = sorteggidettaglioService.bindDomainObject(entity.getSorteggidettaglio(), PkId.class, "id.codice");
	entity.setSorteggidettaglio(sorteggidettaglio);
	Movimenti movimento = movimentiService.bindDomainObject(entity.getMovimenti(), PkId.class, "id.codice");
	entity.setMovimenti(movimento);
	// §§§END§§§
    }
}
