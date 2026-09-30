package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.TipiprocedureavvioDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedureavvio;
import it.gruppoinit.pal.gp.core.domain.TipiprocedureavvioId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.TipiprocedureavvioService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipiprocedureavvioServiceImpl extends BaseServiceImpl<Tipiprocedureavvio, TipiprocedureavvioId> implements TipiprocedureavvioService {

    private TipiprocedureavvioDAO tipiprocedureavvioDAO;

    @Autowired
    public void setTipiprocedureavvioDAO(TipiprocedureavvioDAO tipiprocedureavvioDAO) {

	this.tipiprocedureavvioDAO = tipiprocedureavvioDAO;
    }

    @Override
    protected Class<Tipiprocedureavvio> getEntityClass() {

	return Tipiprocedureavvio.class;
    }

    @Override
    public void delete(Tipiprocedureavvio entity) {

	if (isDeleteAllowed(entity)) {
	    tipiprocedureavvioDAO.delete(entity);
	}
    }

    @Override
    public List<Tipiprocedureavvio> findAll(Integer firstResult, Integer maxResult) {

	return tipiprocedureavvioDAO.findAll(null, null);
    }

    @Override
    public Tipiprocedureavvio findById(TipiprocedureavvioId id) {

	return tipiprocedureavvioDAO.findById(id);
    }

    @Override
    public void insert(Tipiprocedureavvio entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    // La combinazione dei controlli successivi permette di inserire un nuovo movimento di avvio di
	    // default e se ne esiste già uno toglierlo come di default
	    // controllo se la entity che sto inserendo la sto considerando come tipo procedura di avvio di default
	    if (entity.getDefaultsn() == true) {
		// cerca se esiste già un tipo procedura avvio configurata come di default
		Tipiprocedureavvio obj = this.findTipiprocedureavvioDeafult(entity);
		if (obj != null) {
		    obj.setDefaultsn(false);
		    this.update(obj);
		}
	    }
	    tipiprocedureavvioDAO.insert(entity);
	}
    }

    @Override
    public void update(Tipiprocedureavvio entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    // La combinazione dei controlli successivi permette di aggiornare un movimento di avvio in movimento di
	    // avvio di default
	    // ed eventualmente settare il vecchio movimento avvio di default (se esiste) in un movimento di avvio
	    // semplice
	    // controllo se la entity che sto inserendo la sto considerando come tipo procedura di avvio di default
	    if (entity.getDefaultsn() == true) {
		// cerca se esiste già un tipo procedura avvio configurata come di default
		Tipiprocedureavvio obj = this.findTipiprocedureavvioDeafult(entity);
		if (obj != null) {
		    obj.setDefaultsn(false);
		    this.update(obj);
		}
	    }
	    tipiprocedureavvioDAO.update(entity);
	}
    }

    @Override
    public Boolean isMovimentoAvvioDefault(Tipiprocedureavvio tipiprocedureavvio) {

	return tipiprocedureavvioDAO.isMovimentoAvvioDefault(tipiprocedureavvio);
    }

    @Override
    public Tipiprocedureavvio findTipiprocedureavvioDeafult(Tipiprocedureavvio tipiprocedureavvio) {

	return tipiprocedureavvioDAO.findTipiprocedureavvioDeafult(tipiprocedureavvio);
    }

    //    protected boolean isDeleteAllowed(Tipiprocedureavvio tipiprocedureavvio) {
    //
    //	boolean isDelete = true;
    //	Tipiprocedure tipiprocedure = tipiprocedureavvio.getTipoProcedura();
    //	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
    //	
    //	if (!tipiprocedure.getAlberoprocs().isEmpty() && tipiprocedureavvio.getDefaultsn() == true ) {
    //	    _ivs.add(new InvalidValue("tipiprocedure.service_error.movimento_default_non_cancellabile", null, null, null, null));
    //	}
    //	if (!_ivs.isEmpty()) {
    //	    this.throwValidationMessages(_ivs);
    //	}
    //	return isDelete;
    //    }
    private void dataIntegration(Tipiprocedureavvio entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("L'istanza passata è nulla");
	}
	if (entity.getDefaultsn() == null) {
	    entity.setDefaultsn(Boolean.FALSE);
	}
    }

    @Override
    public List<Tipiprocedureavvio> findTipimovimento(String tipomovimento, Integer firstResult, Integer maxResult) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.tipomovimento", tipomovimento, String.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("ordine", "tipoProcedura.software"));
	ft.addOrder(FilterUtils.orderAsc("descrizione", "tipoProcedura.software"));
	ft.addOrder(FilterUtils.orderAsc("procedura", "tipoProcedura"));
	return tipiprocedureavvioDAO.findByFilterTable(ft, firstResult, maxResult);
    }
}
