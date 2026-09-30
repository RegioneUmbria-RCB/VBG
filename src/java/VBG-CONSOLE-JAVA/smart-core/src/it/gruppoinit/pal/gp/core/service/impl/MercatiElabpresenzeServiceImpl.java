package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.MercatiElabpresenzeDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiElabpresenze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.MercatiElabpresenzeService;
import it.gruppoinit.pal.gp.core.service.MercatiService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class MercatiElabpresenzeServiceImpl extends BaseServiceImpl<MercatiElabpresenze, PkId> implements MercatiElabpresenzeService {

    private MercatiElabpresenzeDAO mercatielabpresenzeDAO;
    private MercatiService mercatiService;

    @Autowired
    public void setMercatiService(MercatiService mercatiService) {

	this.mercatiService = mercatiService;
    }

    @Autowired
    public void setMercatiElabpresenzeDAO(MercatiElabpresenzeDAO mercatielabpresenzeDAO) {

	this.mercatielabpresenzeDAO = mercatielabpresenzeDAO;
    }

    @Override
    protected Class<MercatiElabpresenze> getEntityClass() {

	return MercatiElabpresenze.class;
    }

    @Override
    public List<MercatiElabpresenze> findAll(Integer firstResult, Integer maxResult) {

	return mercatielabpresenzeDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(MercatiElabpresenze entity) {

	if (validateEntity(entity)) {
	    mercatielabpresenzeDAO.insert(entity);
	}
    }

    @Override
    public MercatiElabpresenze findById(PkId id) {

	return mercatielabpresenzeDAO.findById(id);
    }

    @Override
    public void update(MercatiElabpresenze entity) {

	if (validateEntity(entity)) {
	    mercatielabpresenzeDAO.update(entity);
	}
    }

    @Override
    public void delete(MercatiElabpresenze entity) {

	if (isDeleteAllowed(entity)) {
	    mercatielabpresenzeDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(MercatiElabpresenze entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// TODO_validare_la_delete
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
    public void updateConsolidaAnnoMercato(Integer codiceMercato, Integer anno) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("mercatiId", codiceMercato, Integer.class));
	fr.addFilterField(FilterUtils.equals("anno", anno, Integer.class));
	ft.addRestriction(fr);
	List<MercatiElabpresenze> presenzes = mercatielabpresenzeDAO.findByFilterTable(ft);
	MercatiElabpresenze pres = null;
	if (presenzes.size() == 0) {
	    pres = new MercatiElabpresenze();
	    pres.setAnno(anno);
	    pres.setDataElaborazione(Calendar.getInstance().getTime());
	    Mercati mercati = mercatiService.findById(new PkId(codiceMercato));
	    pres.setMercati(mercati);
	    this.insert(pres);
	} else {
	    pres = presenzes.get(0);
	    pres.setDataElaborazione(Calendar.getInstance().getTime());
	    this.update(pres);
	}
    }

    @Override
    public List<MercatiElabpresenze> findByMercati(Integer codiceMercato) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("mercatiId", codiceMercato, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("anno"));
	return mercatielabpresenzeDAO.findByFilterTable(ft);
    }

    @Override
    public List<MercatiElabpresenze> findByMercatiAndAnno(Integer codiceMercato, Integer anno) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("mercatiId", codiceMercato, Integer.class));
	fr.addFilterField(FilterUtils.equals("anno", anno, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("anno"));
	return mercatielabpresenzeDAO.findByFilterTable(ft);
    }
}
