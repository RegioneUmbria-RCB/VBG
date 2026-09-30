package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.LinkPreferitiUtenteDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.LinkPreferitiUtente;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.LinkPreferitiUtenteService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author gianpaolot
 */
@Service
public class LinkPreferitiUtenteServiceImpl extends BaseServiceImpl<LinkPreferitiUtente, PkId> implements LinkPreferitiUtenteService {

    private LinkPreferitiUtenteDAO linkpreferitiutenteDAO;

    @Autowired
    public void setLinkPreferitiUtenteDAO(LinkPreferitiUtenteDAO linkpreferitiutenteDAO) {

	this.linkpreferitiutenteDAO = linkpreferitiutenteDAO;
    }

    @Override
    protected Class<LinkPreferitiUtente> getEntityClass() {

	return LinkPreferitiUtente.class;
    }

    @Override
    public List<LinkPreferitiUtente> findAll(Integer firstResult, Integer maxResult) {

	return linkpreferitiutenteDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(LinkPreferitiUtente entity) {

	dataIntegration(entity, true);
	if (validateEntity(entity)) {
	    linkpreferitiutenteDAO.insert(entity);
	}
    }

    @Override
    public LinkPreferitiUtente findById(PkId id) {

	return linkpreferitiutenteDAO.findById(id);
    }

    @Override
    public void update(LinkPreferitiUtente entity) {

	dataIntegration(entity, false);
	if (validateEntity(entity)) {
	    linkpreferitiutenteDAO.update(entity);
	}
    }

    @Override
    public void delete(LinkPreferitiUtente entity) {

	if (isDeleteAllowed(entity)) {
	    linkpreferitiutenteDAO.delete(entity);
	}
    }

    @Override
    public List<LinkPreferitiUtente> findLinkPreferitiUtente(Integer codiceResponsabile, Integer firstResult, Integer maxResult) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceResponsabile, "responsabili", Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("ordine"));
	return linkpreferitiutenteDAO.findByFilterTable(ft, firstResult, maxResult);
    }

    @Override
    public Integer findMaxOrdine() {

	return linkpreferitiutenteDAO.findMaxOrdine();
    }

    private void dataIntegration(LinkPreferitiUtente entity, boolean isInsert) {

	if (entity.getOrdine() == null) {
	    Integer maxOrdine = linkpreferitiutenteDAO.findMaxOrdine();
	    if (maxOrdine != null && maxOrdine.intValue() != 0) {
		maxOrdine += 1;
	    } else {
		maxOrdine = 1;
	    }
	    entity.setOrdine(maxOrdine);
	}
	if (entity.getIsEsternoTransient() != null && entity.getIsEsternoTransient().equals(true)) {
	    entity.setTarget("_blank");
	} else {
	    entity.setTarget(null);
	}
    }
    //    protected boolean isDeleteAllowed(LinkPreferitiUtente entity) {
    //
    //		boolean delete = true;
    //		List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
    //		TODO_validare_la_delete
    //		// esempio:
    //		// if (entity.getList().size() > 0) {
    //		//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
    //		// }
    //		if (!_ivs.isEmpty()) {
    //			this.throwValidationMessages(_ivs);
    //		}
    //		return delete;
    //    }
}
