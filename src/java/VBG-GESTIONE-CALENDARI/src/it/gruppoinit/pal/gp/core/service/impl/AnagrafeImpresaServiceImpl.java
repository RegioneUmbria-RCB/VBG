package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.AnagrafeImpresaDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.AnagrafeImpresa;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AnagrafeImpresaService;
import it.gruppoinit.pal.gp.gestionecalendari.web.command.AnagrafeImpresaFilter;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class AnagrafeImpresaServiceImpl extends BaseServiceImpl<AnagrafeImpresa, PkId> implements AnagrafeImpresaService {

    private AnagrafeImpresaDAO anagrafeimpresaDAO;

    @Autowired
    public void setAnagrafeImpresaDAO(AnagrafeImpresaDAO anagrafeimpresaDAO) {

	this.anagrafeimpresaDAO = anagrafeimpresaDAO;
    }

    @Override
    protected Class<AnagrafeImpresa> getEntityClass() {

	return AnagrafeImpresa.class;
    }

    @Override
    public void insert(AnagrafeImpresa entity) {

	if (validateEntity(entity)) {
	    anagrafeimpresaDAO.insert(entity);
	}
    }

    @Override
    public AnagrafeImpresa findById(PkId id) {

	return anagrafeimpresaDAO.findById(id);
    }

    @Override
    public void update(AnagrafeImpresa entity) {

	if (validateEntity(entity)) {
	    anagrafeimpresaDAO.update(entity);
	}
    }

    @Override
    public void delete(AnagrafeImpresa entity) {

	if (isDeleteAllowed(entity)) {
	    anagrafeimpresaDAO.delete(entity);
	}
    }

    @Override
    public List<AnagrafeImpresa> findByFilter(AnagrafeImpresaFilter filter) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	/**
	 * DATI DELLA PERSONA FISICA
	 */
	if (StringUtils.isNotBlank(filter.getCodicefiscale())) {
	    fr.addFilterField(FilterUtils.equals("codicefiscale", filter.getCodicefiscale(), String.class));
	}
	if (StringUtils.isNotBlank(filter.getCognome())) {
	    fr.addFilterField(FilterUtils.like("cognome", filter.getCognome()));
	}
	/**
	 * DATI DELL'AZIENDA
	 */
	if (StringUtils.isNotBlank(filter.getDenominazione())) {
	    fr.addFilterField(FilterUtils.like("denominazione", filter.getDenominazione()));
	}
	if (StringUtils.isNotBlank(filter.getCfPi())) {
	    fr.addFilterField(FilterUtils.equals("cfPi", filter.getCfPi(), String.class));
	}
	if (StringUtils.isNotBlank(filter.getNumeroRegImprese())) {
	    fr.addFilterField(FilterUtils.equals("numeroRegImprese", filter.getNumeroRegImprese(), String.class));
	}
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("denominazione"));
	ft.addOrder(FilterUtils.orderAsc("cognome"));
	ft.addOrder(FilterUtils.orderAsc("nome"));
	return anagrafeimpresaDAO.findByFilterTable(ft);
    }

    protected boolean isDeleteAllowed(AnagrafeImpresa entity) {

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
