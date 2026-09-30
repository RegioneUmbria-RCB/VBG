package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.TipoComunicazioniTDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.TipoComunicazioniT;
import it.gruppoinit.pal.gp.core.service.TipoComunicazioniTService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class TipoComunicazioniTServiceImpl extends BaseServiceImpl<TipoComunicazioniT, String> implements TipoComunicazioniTService {

    private TipoComunicazioniTDAO tipocomunicazionitDAO;

    @Autowired
    public void setTipoComunicazioniTDAO(TipoComunicazioniTDAO tipocomunicazionitDAO) {

	this.tipocomunicazionitDAO = tipocomunicazionitDAO;
    }

    @Override
    protected Class<TipoComunicazioniT> getEntityClass() {

	return TipoComunicazioniT.class;
    }

    @Override
    public List<TipoComunicazioniT> findAll(Integer firstResult, Integer maxResult) {

	return tipocomunicazionitDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(TipoComunicazioniT entity) {

//	if (validateEntity(entity)) {
//	    tipocomunicazionitDAO.insert(entity);
//	}
	throw new NotImplementedException("Metodo non implementato");
    }

    @Override
    public TipoComunicazioniT findById(String id) {

	return tipocomunicazionitDAO.findById(id);
    }

    @Override
    public void update(TipoComunicazioniT entity) {

//	if (validateEntity(entity)) {
//	    tipocomunicazionitDAO.update(entity);
//	}
	throw new NotImplementedException("Metodo non implementato");
    }

    @Override
    public void delete(TipoComunicazioniT entity) {

//	if (isDeleteAllowed(entity)) {
//	    tipocomunicazionitDAO.delete(entity);
//	}
	throw new NotImplementedException("Metodo non implementato");
    }

    protected boolean isDeleteAllowed(TipoComunicazioniT entity) {

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
}
