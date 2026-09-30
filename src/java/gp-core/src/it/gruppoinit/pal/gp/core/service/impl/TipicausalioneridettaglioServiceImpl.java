package it.gruppoinit.pal.gp.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.TipicausalioneridettaglioDAO;
import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneridettaglio;
import it.gruppoinit.pal.gp.core.domain.TipicausalioneridettaglioId;
import it.gruppoinit.pal.gp.core.features.oneri.ContiService;
import it.gruppoinit.pal.gp.core.features.oneri.TipicausalioneriService;
import it.gruppoinit.pal.gp.core.service.TipicausalioneridettaglioService;

/**
 * 
 * @author
 */
@Service
public class TipicausalioneridettaglioServiceImpl extends BaseServiceImpl<Tipicausalioneridettaglio, TipicausalioneridettaglioId>
	implements TipicausalioneridettaglioService {

    private TipicausalioneridettaglioDAO tipicausalioneridettaglioDAO;
    private ContiService contiService;
    private TipicausalioneriService tipicausalioneriService;

    @Autowired
    public void setTipicausalioneridettaglioDAO(TipicausalioneridettaglioDAO tipicausalioneridettaglioDAO) {

	this.tipicausalioneridettaglioDAO = tipicausalioneridettaglioDAO;
    }

    @Autowired
    public void setContiService(ContiService contiService) {

	this.contiService = contiService;
    }

    @Autowired
    public void setTipicausalioneriService(TipicausalioneriService tipicausalioneriService) {

	this.tipicausalioneriService = tipicausalioneriService;
    }

    @Override
    protected Class<Tipicausalioneridettaglio> getEntityClass() {

	return Tipicausalioneridettaglio.class;
    }

    @Override
    public List<Tipicausalioneridettaglio> findAll(Integer firstResult, Integer maxResult) {

	return tipicausalioneridettaglioDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Tipicausalioneridettaglio entity) {

	if (validateEntity(entity)) {
	    tipicausalioneridettaglioDAO.insert(entity);
	}
    }

    @Override
    public Tipicausalioneridettaglio findById(TipicausalioneridettaglioId id) {

	return tipicausalioneridettaglioDAO.findById(id);
    }

    @Override
    public void update(Tipicausalioneridettaglio entity) {

	if (validateEntity(entity)) {
	    tipicausalioneridettaglioDAO.update(entity);
	}
    }

    @Override
    public void delete(Tipicausalioneridettaglio entity) {

	if (isDeleteAllowed(entity)) {
	    tipicausalioneridettaglioDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Tipicausalioneridettaglio entity) {

	boolean delete = true;
	//		List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	//		TODO_validare_la_delete
	//		// esempio:
	//		// if (entity.getList().size() > 0) {
	//		//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	//		// }
	//		if (!_ivs.isEmpty()) {
	//			this.throwValidationMessages(_ivs);
	//		}
	return delete;
    }

    @Override
    public void impostaContoAttivo(Integer idCausaleOnere, Integer idConto) {

	List<Tipicausalioneridettaglio> list = tipicausalioneridettaglioDAO.findByCausaleOnere(idCausaleOnere);
	for (Tipicausalioneridettaglio tipicausalioneridettaglio : list) {
	    tipicausalioneridettaglio.setFlagAttivo(Boolean.FALSE);
	    tipicausalioneridettaglioDAO.update(tipicausalioneridettaglio);
	}
	if (idConto != null) {
	    TipicausalioneridettaglioId id = new TipicausalioneridettaglioId(idConto, idCausaleOnere);
	    Tipicausalioneridettaglio entity = tipicausalioneridettaglioDAO.findById(id);
	    if (entity == null) {
		entity = new Tipicausalioneridettaglio();
		entity.setId(id);
		entity.setFlagAttivo(Boolean.TRUE);
		tipicausalioneridettaglioDAO.insert(entity);
	    } else {
		entity.setFlagAttivo(Boolean.TRUE);
		tipicausalioneridettaglioDAO.update(entity);
	    }
	}
    }

    @Override
    public Integer findIdContoAttivoByCausaleOneri(Integer codice) {

	return this.tipicausalioneridettaglioDAO.findIdContoAttivoByCausaleOneri(codice);
    }

    @Override
    public Conti findContoAttivoByCausaleOneri(Integer codice) {

	return this.tipicausalioneridettaglioDAO.findContoAttivoByCausaleOneri(codice);
    }

    @Override
    public void deleteByIdOnere(Integer codice) {

	this.tipicausalioneridettaglioDAO.deleteByIdOnere(codice);
    }
}
