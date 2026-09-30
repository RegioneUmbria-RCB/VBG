package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.TipimovimentoDisDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoDis;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;
import it.gruppoinit.pal.gp.core.service.TipimovimentoDisService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class TipimovimentoDisServiceImpl extends BaseServiceImpl<TipimovimentoDis, PkId> implements TipimovimentoDisService {

    private AmministrazioniService amministrazioniService;
    private InventarioprocedimentiService inventarioprocedimentiService;
    private IstanzeService istanzeService;
    private TipimovimentoDisDAO tipimovimentodisDAO;
    private TipiMovimentoService tipiMovimentoService;

    @Autowired
    public void setAmministrazioniService(AmministrazioniService amministrazioniService) {

	this.amministrazioniService = amministrazioniService;
    }

    @Autowired
    public void setInventarioprocedimentiService(InventarioprocedimentiService inventarioprocedimentiService) {

	this.inventarioprocedimentiService = inventarioprocedimentiService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setTipimovimentoDisDAO(TipimovimentoDisDAO tipimovimentodisDAO) {

	this.tipimovimentodisDAO = tipimovimentodisDAO;
    }

    @Autowired
    public void setTipiMovimentoService(TipiMovimentoService tipiMovimentoService) {

	this.tipiMovimentoService = tipiMovimentoService;
    }

    @Override
    protected Class<TipimovimentoDis> getEntityClass() {

	return TipimovimentoDis.class;
    }

    @SuppressWarnings("deprecation")
    @Override
    public List<TipimovimentoDis> findAll(Integer firstResult, Integer maxResult) {

	return tipimovimentodisDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(TipimovimentoDis entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    tipimovimentodisDAO.insert(entity);
	}
    }

    @Override
    public TipimovimentoDis findById(PkId id) {

	return tipimovimentodisDAO.findById(id);
    }

    @Override
    public void update(TipimovimentoDis entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    tipimovimentodisDAO.update(entity);
	}
    }

    private void dataIntegration(TipimovimentoDis entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il parametro TipimovimentoDis è nullo");
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(TipimovimentoDis entity) {

	Istanze istanza = istanzeService.bindDomainObject(entity.getIstanze(), PkId.class, "id.codice");
	entity.setIstanze(istanza);
	Inventarioprocedimenti endo = inventarioprocedimentiService.bindDomainObject(entity.getInventarioprocedimenti(), PkId.class, "id.codice");
	entity.setInventarioprocedimenti(endo);
	Amministrazioni amministrazione = amministrazioniService.bindDomainObject(entity.getAmministrazioni(), PkId.class, "id.codice");
	entity.setAmministrazioni(amministrazione);
	Tipimovimento tipimovimento = tipiMovimentoService.bindDomainObject(entity.getTipomovimento(), TipimovimentoId.class, "id.tipomovimento");
	entity.setTipomovimento(tipimovimento);
    }

    @Override
    public void delete(TipimovimentoDis entity) {

	if (isDeleteAllowed(entity)) {
	    tipimovimentodisDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(TipimovimentoDis entity) {

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

    @Override
    public List<TipimovimentoDis> findTipimovimentoDisabilitati(Integer codiceistanza, String tipomovimento, Integer codiceinventario,
	    Integer codiceamministrazione, Date datascadenza) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceistanza, "istanze", Integer.class));
	fr.addFilterField(FilterUtils.equals("id.tipomovimento", tipomovimento, "tipomovimento", String.class));
	if (codiceinventario == null) {
	    fr.addFilterField(FilterUtils.isNotNull("id.codice", "inventarioprocedimenti"));
	} else {
	    fr.addFilterField(FilterUtils.equals("id.codice", codiceinventario, "inventarioprocedimenti", Integer.class));
	}
	if (codiceamministrazione == null) {
	    fr.addFilterField(FilterUtils.isNotNull("id.codice", "amministrazioni"));
	} else {
	    fr.addFilterField(FilterUtils.equals("id.codice", codiceamministrazione, "amministrazioni", Integer.class));
	}
	if (datascadenza == null) {
	    fr.addFilterField(FilterUtils.isNotNull("datascad"));
	} else {
	    fr.addFilterField(FilterUtils.equals("datascad", datascadenza, Date.class));
	}
	ft.addRestriction(fr);
	return tipimovimentodisDAO.findByFilterTable(ft);
    }

    @Override
    public List<TipimovimentoDis> findByIstanza(Istanze istanza) {

	if (EntityUtils.getNestedProperty(istanza, "id.codice") == null) {
	    throw new IllegalArgumentException("Il parametro istanza non è valido");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("istanzeId", istanza.getId().getCodice(), Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("movimento", "tipomovimento"));
	ft.addOrder(FilterUtils.orderAsc("ordine", "inventarioprocedimenti"));
	ft.addOrder(FilterUtils.orderAsc("amministrazione", "amministrazioni"));
	ft.addOrder(FilterUtils.orderAsc("datascad"));
	return tipimovimentodisDAO.findByFilterTable(ft);
    }
}
