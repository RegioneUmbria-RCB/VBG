package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.BattitoriCsiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.BattitoriCsi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.BattitoriCsiService;

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
public class BattitoriCsiServiceImpl extends BaseServiceImpl<BattitoriCsi, PkId> implements BattitoriCsiService {

    private BattitoriCsiDAO battitoricsiDAO;

    @Autowired
    public void setBattitoriCsiDAO(BattitoriCsiDAO battitoricsiDAO) {

	this.battitoricsiDAO = battitoricsiDAO;
    }

    @Override
    protected Class<BattitoriCsi> getEntityClass() {

	return BattitoriCsi.class;
    }

    @Override
    public List<BattitoriCsi> findAll(Integer firstResult, Integer maxResult) {

	return battitoricsiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(BattitoriCsi entity) {

	if (validateEntity(entity)) {
	    battitoricsiDAO.insert(entity);
	}
    }

    @Override
    public BattitoriCsi findById(PkId id) {

	return battitoricsiDAO.findById(id);
    }

    @Override
    public void update(BattitoriCsi entity) {

	if (validateEntity(entity)) {
	    battitoricsiDAO.update(entity);
	}
    }

    @Override
    public void delete(BattitoriCsi entity) {

	if (isDeleteAllowed(entity)) {
	    battitoricsiDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(BattitoriCsi entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// TODO _validare_la_delete
	// esempio:
	// if (entity.getList().size() > 0) {
	//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	// }
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<BattitoriCsi> findByAutorizzazioniAndGiorno(Integer idAut, Integer idGiornoSettimana, Integer firstResult, Integer maxResults) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("autorizzazioniId", idAut, Integer.class));
	fr.addFilterField(FilterUtils.equals("giornisettimana.id", idGiornoSettimana, Integer.class));
	ft.addRestriction(fr);
	return battitoricsiDAO.findByFilterTable(ft, firstResult, maxResults);
    }

    @Override
    public int countByAutorizzazioniAndGiorno(Integer idAut, Integer idGiornoSettimana) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("autorizzazioniId", idAut, Integer.class));
	fr.addFilterField(FilterUtils.equals("giornisettimana.id", idGiornoSettimana, Integer.class));
	ft.addRestriction(fr);
	return battitoricsiDAO.countRecord(ft);
    }
}
