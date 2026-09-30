package it.gruppoinit.pal.gp.core.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.IstanzeattivitaDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Attivita;
import it.gruppoinit.pal.gp.core.domain.AttivitaId;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeattivita;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Settori;
import it.gruppoinit.pal.gp.core.domain.Settoriavvisi;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.helper.SuperficiAttivitaHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AttivitaService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeattivitaService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

/**
 * 
 * @author
 */
@Service
public class IstanzeattivitaServiceImpl extends BaseServiceImpl<Istanzeattivita, PkId> implements IstanzeattivitaService {

    private AttivitaService attivitaService;
    private IstanzeattivitaDAO istanzeattivitaDAO;
    private IstanzeService istanzeService;
    private SoftwareService softwareService;

    @Autowired
    public void setAttivitaService(AttivitaService attivitaService) {

	this.attivitaService = attivitaService;
    }

    @Autowired
    public void setIstanzeattivitaDAO(IstanzeattivitaDAO istanzeattivitaDAO) {

	this.istanzeattivitaDAO = istanzeattivitaDAO;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Override
    protected Class<Istanzeattivita> getEntityClass() {

	return Istanzeattivita.class;
    }

    @Override
    public List<Istanzeattivita> findAll(Integer firstResult, Integer maxResult) {

	return istanzeattivitaDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Istanzeattivita entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    istanzeattivitaDAO.insert(entity);
	    aggiornaMetriQIstanza(entity.getIstanza(), entity.getAttivita().getSettori());
	}
    }

    private void dataIntegration(Istanzeattivita entity) {

	if (entity == null) {
	    throw new RuntimeException("Il parametro istanze attivita è nullo");
	}
	if (entity.getMetriq() == null) {
	    entity.setMetriq(BigDecimal.ZERO);
	}
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(Istanzeattivita entity) {

	Attivita attivita = attivitaService.bindDomainObject(entity.getAttivita(), AttivitaId.class, "id.codiceistat");
	entity.setAttivita(attivita);
	Istanze istanza = istanzeService.bindDomainObject(entity.getIstanza(), PkId.class, "id.codice");
	entity.setIstanza(istanza);
	Software software = softwareService.bindDomainObject(entity.getSoftware(), String.class, "codice");
	entity.setSoftware(software);
    }

    @Override
    public Istanzeattivita findById(PkId id) {

	return istanzeattivitaDAO.findById(id);
    }

    @Override
    public void update(Istanzeattivita entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    istanzeattivitaDAO.update(entity);
	    aggiornaMetriQIstanza(entity.getIstanza(), entity.getAttivita().getSettori());
	}
    }

    @Override
    public void delete(Istanzeattivita entity) {

	if (isDeleteAllowed(entity)) {
	    Istanze istanze = entity.getIstanza();
	    Settori settori = entity.getAttivita().getSettori();
	    istanzeattivitaDAO.delete(entity);
	    aggiornaMetriQIstanza(istanze, settori);
	}
    }

    protected boolean isDeleteAllowed(Istanzeattivita entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<Istanzeattivita> findByIstanza(Istanze istanza, Boolean orderByCodiceistat) {

	if (EntityUtils.getNestedProperty(istanza, "id.codice") == null) {
	    throw new IllegalArgumentException("il parametro istanza non può essere vuoto o nullo");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", istanza.getId().getCodice(), "istanza", Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("settore", "attivita.settori"));
	if (orderByCodiceistat) {
	    ft.addOrder(FilterUtils.orderAsc("id.codiceistat", "attivita"));
	    ft.addOrder(FilterUtils.orderAsc("istat", "attivita"));
	} else {
	    ft.addOrder(FilterUtils.orderAsc("istat", "attivita"));
	    ft.addOrder(FilterUtils.orderAsc("id.codiceistat", "attivita"));
	}
	List<Istanzeattivita> list = istanzeattivitaDAO.findByFilterTable(ft);
	return list;
    }

    @Override
    public List<Settoriavvisi> findAvvisiIstanza(Istanze istanza) {

	if (EntityUtils.getNestedProperty(istanza, "id.codice") == null) {
	    throw new IllegalArgumentException("il parametro istanza non può essere vuoto o nullo");
	}
	return istanzeattivitaDAO.findAvvisiIstanza(istanza);
    }

    @Override
    public void insertIstanzeattivitaSet(Set<Istanzeattivita> istanzeattivitas) {

	if (istanzeattivitas != null && !istanzeattivitas.isEmpty()) {
	    for (Istanzeattivita entity : istanzeattivitas) {
		this.insert(entity);
	    }
	}
    }

    @Override
    public List<Istanzeattivita> findByIstanzaAndSettore(Istanze istanza, Settori settore) {

	if (EntityUtils.getNestedProperty(istanza, "id.codice") == null) {
	    throw new IllegalArgumentException("il parametro istanza non può essere vuoto o nullo");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction istanzafr = new FilterRestriction();
	istanzafr.addFilterField(FilterUtils.equals("id.codice", istanza.getId().getCodice(), "istanza", Integer.class));
	ft.addRestriction(istanzafr);
	FilterRestriction settorefr = new FilterRestriction();
	settorefr.addFilterField(FilterUtils.equals("id.codicesettore", settore.getId().getCodicesettore(), "attivita.settori", Integer.class));
	ft.addRestriction(settorefr);
	List<Istanzeattivita> list = istanzeattivitaDAO.findByFilterTable(ft);
	return list;
    }

    protected void aggiornaMetriQIstanza(Istanze istanza, Settori settore) {

	if (settore.getFlagContamqattivita()) {
	    List<Istanzeattivita> istanzeattivitas = this.findByIstanzaAndSettore(istanza, settore);
	    // Calcola Totale
	    BigDecimal totale = new BigDecimal(0);
	    for (Istanzeattivita istanzeattivita : istanzeattivitas) {
		totale = totale.add(istanzeattivita.getMetriq());
	    }
	    istanza.setMetriquadrati(totale);
	    istanzeService.updateMqIstanza(istanza.getId().getCodice(), totale);
	}
    }

    @Override
    public void copiaIstanzeAttivita(Istanze istanzaSorgente, Istanze istanzaDestinatario) {

	// le istanze attività che devo copiare 
	Set<Istanzeattivita> listIstanzeattivitaSorgente = istanzaSorgente.getIstanzeattivitas();
	//
	//1-lista delle istanze attività che utilizzerò per i controllo per non creare doppioni
	Set<Istanzeattivita> listIstanzeattivitaDestinatare = istanzaDestinatario.getIstanzeattivitas();
	Istanzeattivita istanzeattivita = null;
	for (Istanzeattivita istanzeattivitaSorgenete : listIstanzeattivitaSorgente) {
	    if (!isIstanzeAttivitaExists(istanzeattivitaSorgenete, listIstanzeattivitaDestinatare)) {
		istanzeattivita = new Istanzeattivita();
		istanzeattivita.setIstanza(istanzaDestinatario);
		if (istanzeattivitaSorgenete.getAttivita() != null) {
		    istanzeattivita.setAttivita(istanzeattivitaSorgenete.getAttivita());
		}
		if (istanzeattivitaSorgenete.getMetriq() != null) {
		    istanzeattivita.setMetriq(istanzeattivitaSorgenete.getMetriq());
		}
		if (StringUtils.isNotBlank(istanzeattivitaSorgenete.getNote())) {
		    istanzeattivita.setNote(istanzeattivitaSorgenete.getNote());
		}
		if (istanzeattivitaSorgenete.getSoftware() != null) {
		    istanzeattivita.setSoftware(istanzeattivitaSorgenete.getSoftware());
		}
		this.insert(istanzeattivita);
	    }
	}
    }

    // Controlla per ogni oggetto della lista destinatario se ce ne è uno uguale a quello sorgente passato
    // quando ne trova uno esce e ritona true
    // altrimenti ritorna false.
    private boolean isIstanzeAttivitaExists(Istanzeattivita istanzeattivitaSorgente, Set<Istanzeattivita> listIstanzeattivitaDestinatarie) {

	boolean isEquals = false;
	for (Istanzeattivita istanzeattivitaDestinatario : listIstanzeattivitaDestinatarie) {
	    if ((istanzeattivitaSorgente.getAttivita() == null && istanzeattivitaDestinatario.getAttivita() == null)
		    || (EntityUtils.equals(istanzeattivitaSorgente.getAttivita(), istanzeattivitaDestinatario.getAttivita()))) {
		return true;
	    }
	}
	return isEquals;
    }

    @Override
    public List<SuperficiAttivitaHelper> getSommaSuperficiAttivitaPerSettore(Istanze istanza, String perSettore) {

	return istanzeattivitaDAO.getSommaSuperficiAttivitaPerSettore(istanza, perSettore);
    }

    @Override
    public List<SuperficiAttivitaHelper> getSommaSuperficiAttivitaPerAttivita(Istanze istanza, String perAttivita) {

	return istanzeattivitaDAO.getSommaSuperficiAttivitaPerAttivita(istanza, perAttivita);
    }
}
